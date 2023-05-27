import service.ParameterValidator;

public class WebCrawler {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java WebCrawler <startUrl> <maxUrls> <maxDepth> <crossLevelUniqueness>");
            return;
        }

        String startUrl = args[0];
        int maxUrls = Integer.parseInt(args[1]);
        int maxDepth = Integer.parseInt(args[2]);
        boolean crossLevelUniqueness = Boolean.parseBoolean(args[3]);
        ParameterValidator.validateParameters(args);
        CrawlerManager crawlerManager = new CrawlerManager();
        crawlerManager.processUrl(maxUrls, maxDepth , startUrl, crossLevelUniqueness);
    }

}
