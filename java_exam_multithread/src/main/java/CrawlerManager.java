import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import service.CrawlingWorker;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Data
public class CrawlerManager {
    private static final int THREAD_POOL_SIZE = 10;
    private static final int WAITING_TIME = 10;
    private static final int INITIALIZE_DEPTH = 0;
    private static Set<String> sharedGeneralUniqueUrls = new ConcurrentSkipListSet<>();

    public void processUrl(int maxUrls, int maxDepth, String url, boolean crossLevelUniqueness) {

        AtomicInteger depth = new AtomicInteger(0);
        Set<String> urlsForIterationInLevel = new ConcurrentSkipListSet<>();
        if (url == null){
            throw new IllegalArgumentException("startUrl cannot be null or empty.");
        }
        urlsForIterationInLevel.add(url);
        sharedGeneralUniqueUrls.add(url);

        while (depth.get() <= maxDepth) {
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            final Set<String> sharedUrlsFoundInLevel = new ConcurrentSkipListSet<>();

            for (String extractedUrl : urlsForIterationInLevel) {
                CrawlingWorker finalWorker1 = new CrawlingWorker(maxUrls, extractedUrl, depth.get(), crossLevelUniqueness);
                executorService.submit(() -> finalWorker1.execute(sharedGeneralUniqueUrls, sharedUrlsFoundInLevel));
            }
            executorService.shutdown();
            //waits for all the threads finished they task or until all the threads of the-sam level finished the tasks
            try {
                log.info("Waiting for crawling threads for level {} to finish...", depth);
                executorService.awaitTermination(WAITING_TIME, TimeUnit.SECONDS);
                log.info("Crawling threads finished.");
            } catch (InterruptedException e) {
                log.error("Thread execution was interrupted: {}", e.getMessage());
            }

            //To sum up the quinine urls for each level and the reset the list
            urlsForIterationInLevel = sharedUrlsFoundInLevel;
            depth.set(depth.get() + 1);
        }

    }

    public Set<String> getAllUrls(){
        return new HashSet<>(sharedGeneralUniqueUrls);
    }
}
