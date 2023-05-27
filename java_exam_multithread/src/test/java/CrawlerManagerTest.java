import org.junit.jupiter.api.Test;

class CrawlerManagerTest {

    @Test
    void testProcessUrl_withZeroMaxUrls() {
        CrawlerManager crawlerManager = new CrawlerManager();
        int maxUrls = 0;
        int maxDepth = 3;
        String url = "https://example.com";
        boolean crossLevelUniqueness = true;

        crawlerManager.processUrl(maxUrls, maxDepth, url, crossLevelUniqueness);

    }

    @Test
    void testProcessUrl_withNegativeMaxDepth_shuold() {
        CrawlerManager crawlerManager = new CrawlerManager();
        int maxUrls = 10;
        int maxDepth = -1;
        String url = "https://example.com";
        boolean crossLevelUniqueness = true;

        crawlerManager.processUrl(maxUrls, maxDepth, url, crossLevelUniqueness);

    }

    @Test
    void testProcessUrl_withNullUrl() {
        CrawlerManager crawlerManager = new CrawlerManager();
        int maxUrls = 10;
        int maxDepth = 3;
        String url = null; // Null URL
        boolean crossLevelUniqueness = true;

        crawlerManager.processUrl(maxUrls, maxDepth, url, crossLevelUniqueness);

    }

}
