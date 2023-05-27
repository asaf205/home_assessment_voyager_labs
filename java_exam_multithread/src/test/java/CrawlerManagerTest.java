import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.ParameterValidator;

class CrawlerManagerTest {

    @Test
    void testProcessUrl_withZeroMaxUrls_should_return_empty_set() {
        CrawlerManager crawlerManager = new CrawlerManager();
        int maxUrls = 0;
        int maxDepth = 3;
        String url = "https://example.com";
        boolean crossLevelUniqueness = true;

        crawlerManager.processUrl(maxUrls, maxDepth, url, crossLevelUniqueness);
        Assertions.assertTrue(crawlerManager.getAllUrls().isEmpty());

    }

    @Test
    void testProcessUrl_withNegativeMaxDepth_should_return_empty_set() {
        CrawlerManager crawlerManager = new CrawlerManager();
        int maxUrls = 10;
        int maxDepth = -1;
        String url = "https://example.com";
        boolean crossLevelUniqueness = true;

        crawlerManager.processUrl(maxUrls, maxDepth, url, crossLevelUniqueness);
        Assertions.assertTrue(crawlerManager.getAllUrls().isEmpty());
    }

    @Test
    void testProcessUrl_withNullUrl() {
        CrawlerManager crawlerManager = new CrawlerManager();
        int maxUrls = 10;
        int maxDepth = 3;
        String url = null; // Null URL
        boolean crossLevelUniqueness = true;

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> crawlerManager.processUrl(maxUrls, maxDepth, url, crossLevelUniqueness));
        Assertions.assertEquals("startUrl cannot be null or empty.", exception.getMessage());
    }

}
