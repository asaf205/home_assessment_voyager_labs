import service.ParameterValidator;

public class WebCrawler {

    public static void main(String[] args) {

        ParameterValidator.validateParameters(args);
        String startUrl = args[0];
        int maxUrls = Integer.parseInt(args[1]);
        int maxDepth = Integer.parseInt(args[2]);
        boolean crossLevelUniqueness = Boolean.parseBoolean(args[3]);
        CrawlerManager crawlerManager = new CrawlerManager();
        crawlerManager.processUrl(maxUrls, maxDepth , startUrl, crossLevelUniqueness);
    }

}
