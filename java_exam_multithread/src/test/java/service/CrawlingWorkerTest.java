package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static service.CrawlingWorker.PREFIX_FOR_RUN;

public class CrawlingWorkerTest {

    private Set<String> generalUniqueUrls;
    private Set<String> uniqueUrlsPerLevel;
    private static final String PREFIX = "src/results/" + PREFIX_FOR_RUN +"/";

    @BeforeEach
    public void setUp() {
        generalUniqueUrls = new ConcurrentSkipListSet<>();
        uniqueUrlsPerLevel = new ConcurrentSkipListSet<>();
    }

    @AfterEach
    public void cleanup() throws IOException {
        // Delete the temporary directory and its contents
        Path path = Paths.get(PREFIX);
        Files.walk(path)
                    .map(Path::toFile)
                    .forEach(File::delete);
            Files.deleteIfExists(path);
    }

    @Test
    public void testExecute() {
        String url = "https://example.com";
        int maxUrls = 10;
        int depth = 1;
        boolean crossLevelUniqueness = true;

        CrawlingWorker worker = new CrawlingWorker(maxUrls, url, depth, crossLevelUniqueness);

        // Execute the worker
        worker.execute(generalUniqueUrls, uniqueUrlsPerLevel);

        // Verify that the URLs are added to the sets
        assertTrue(generalUniqueUrls.isEmpty());
        assertTrue(uniqueUrlsPerLevel.isEmpty());
    }

    @Test
    public void testSavePage() throws IOException {
        String url = "https://example.com";
        int depth = 1;
        String html = "<html><body>This is a test HTML page.</body></html>";

        try {
            CrawlingWorker worker = new CrawlingWorker(10, url, depth, true);

            worker.savePage(url, depth, html);

            // Verify that the file was created
            Path filePath = Paths.get(PREFIX + "/1/example_com.html");
            assertTrue(Files.exists(filePath));


            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                String fileContent = reader.readLine();
                assertEquals(html, fileContent);
            }
        } finally {
            // Clean up the temporary directory after the test
            cleanup();
        }
    }
}
