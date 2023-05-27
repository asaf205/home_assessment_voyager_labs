import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import service.CrawlingWorker;
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
    private static Set<String> sharedUniqueUrlsPerLevel = new ConcurrentSkipListSet<>();


    public void processUrl(int maxUrls, int maxDepth, String url, boolean crossLevelUniqueness) {

        AtomicInteger depth = new AtomicInteger(1);
        CrawlingWorker worker = new CrawlingWorker(maxUrls, url, INITIALIZE_DEPTH, crossLevelUniqueness);
        worker.execute(sharedGeneralUniqueUrls, sharedUniqueUrlsPerLevel);

        while (depth.get() <= maxDepth) {
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            Set<String> finalUniqueUrlsPerLevel = new ConcurrentSkipListSet<>(); //to sum up the quinine urls for each level and the reset the list
            finalUniqueUrlsPerLevel.addAll(sharedUniqueUrlsPerLevel);
            sharedUniqueUrlsPerLevel = new ConcurrentSkipListSet<>();
            
            for (String extractedUrl : finalUniqueUrlsPerLevel) {
                CrawlingWorker finalWorker1 = new CrawlingWorker(maxUrls, extractedUrl, depth.get(), crossLevelUniqueness);
                executorService.submit(() -> finalWorker1.execute(sharedGeneralUniqueUrls, sharedUniqueUrlsPerLevel));
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
            depth.set(depth.get() + 1);

        }

    }
}
