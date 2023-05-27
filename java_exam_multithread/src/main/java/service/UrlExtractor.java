package service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
public class UrlExtractor {

    private UrlFetcherUtils validator = new UrlFetcherUtils();

    private Document document;

    private int maxUrls;

    public UrlExtractor(Document document, int maxUrls) {
        this.document = document;
        this.maxUrls = maxUrls;
    }

    //extract urls from the document
    public void extract(Set<String> sharedGeneralUniqueUrls, Set<String> sharedUrlsFoundInLevel, boolean crossLevelUniqueness) {
        if (document == null){
            return;
        }
        Set<String> hrefList = Set.copyOf(document.select("a[href]").eachAttr("abs:href"));
        int sumOfTheNewAddedUrls = 0;
        log.info("Number of links found: {}", hrefList.size());
        for (String link : hrefList) {
            if (maxUrls <= sumOfTheNewAddedUrls) {
                break;
            }
            if (!validator.isValidUrl(link)) {
                log.info("The URL {} is not valid", link);
                continue;
            }
            if (crossLevelUniqueness) {
                if (!sharedGeneralUniqueUrls.contains(link)) {
                    sharedGeneralUniqueUrls.add(link);
                    sharedUrlsFoundInLevel.add(link);
                    sumOfTheNewAddedUrls++;
                    log.debug("Added URL: {} (Cross-level uniqueness)", link);
                }
            } else {
                sharedUrlsFoundInLevel.add(link);
                sumOfTheNewAddedUrls++;
                log.debug("Added URL: {}", link);
            }

        }

    }
}
