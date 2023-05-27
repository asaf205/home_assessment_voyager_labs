package service;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlFetcherUtilsTest {

    @Test
    void testFetchUrlDocument() {
        UrlFetcherUtils urlFetcherUtils = new UrlFetcherUtils();
        String validUrl = "https://example.com";
        String invalidUrl = "https://invalidurl.com";

        // Test valid URL
        Document validDocument = urlFetcherUtils.fetchUrlDocument(validUrl);
        Assertions.assertNotNull(validDocument, "Valid document should not be null");

        // Test invalid URL
        Document invalidDocument = urlFetcherUtils.fetchUrlDocument(invalidUrl);
        Assertions.assertNull(invalidDocument, "Invalid document should be null");
    }

    @Test
    void testIsValidUrl() {
        UrlFetcherUtils urlFetcherUtils = new UrlFetcherUtils();
        String validUrl = "https://example.com";
        String invalidUrl = "https://invalidurl.com";

        // Test valid URL
        boolean isValidValidUrl = urlFetcherUtils.isValidUrl(validUrl);
        Assertions.assertTrue(isValidValidUrl, "Valid URL should be considered valid");

        // Test invalid URL
        boolean isValidInvalidUrl = urlFetcherUtils.isValidUrl(invalidUrl);
        Assertions.assertFalse(isValidInvalidUrl, "Invalid URL should be considered invalid");
    }
}
