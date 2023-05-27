package service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Data
public class CrawlingWorker {
    private static final String PREFIX = "src/results/";
    protected static final String PREFIX_FOR_RUN = UUID.randomUUID().toString();
    private static final UrlFetcherUtils validator = new UrlFetcherUtils();

    private UrlExtractor extractor;
    private int depth;
    private boolean crossLevelUniqueness;

    private String url;

    public CrawlingWorker(int maxUrls, String url, int depth, boolean crossLevelUniqueness) {
        this.extractor = new UrlExtractor(validator.fetchUrlDocument(url), maxUrls);
        this.depth = depth;
        this.crossLevelUniqueness = crossLevelUniqueness;
        this.url = url;
    }

    public void execute(Set<String> sharedGeneralUniqueUrls, Set<String> sharedUrlsFoundInLevel) {
        extractor.extract(sharedGeneralUniqueUrls, sharedUrlsFoundInLevel, crossLevelUniqueness);
        savePage(url,depth, extractor.getDocument().html());
        log.info("Executed CrawlingWorker for URL: {}, Depth: {}, Thread: {}", url, depth, Thread.currentThread().getName());
    }

    public void savePage(String url, int depth, String html) {
        String fileName = url.replaceFirst("^(http[s]?://)", "")
                .replaceAll("[^a-zA-Z0-9-_\\.]", "_") + ".html";
        String folderName = String.valueOf(depth);

        Path path = Paths.get(PREFIX + PREFIX_FOR_RUN +"/"+ folderName, fileName);
        try {
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()))) {
                writer.write(html);
                writer.flush();
            }
            log.info("Saved HTML page for URL: {}, Depth: {}, Path: {}, Thread: {}", url, depth, path, Thread.currentThread().getName());
        } catch (IOException e) {
            log.error("Failed to save HTML page for URL: {}, Depth: {}, Thread: {}", url, depth, Thread.currentThread().getName(), e);
        }
    }

}

