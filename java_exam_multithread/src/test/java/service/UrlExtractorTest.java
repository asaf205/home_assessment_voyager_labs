package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

class UrlExtractorTest {

    private static final String DUMMY_HTML = "<a href=\"https://www.example.com\">Example</a>"
            + "<a href=\"https://www.example.com\">Example</a>"
            + "<a href=\"https://www.example2.com\">Example 2</a>";

    private UrlExtractor extractor;

    private UrlFetcherUtils validator;

    @BeforeEach
    void setup() {
        Document document = Jsoup.parse(DUMMY_HTML);
        validator = Mockito.mock(UrlFetcherUtils.class);
        extractor = new UrlExtractor(document, 5);
        extractor.setValidator(validator);
    }

    @Test
    void testExtract_should_return_unis_urls() {
        Set<String> generalUniqueUrls = new HashSet<>();
        Set<String> currentUniqueUrlsPeLevel = new HashSet<>();


        generalUniqueUrls.add("https://www.example3.com");
        generalUniqueUrls.add("https://www.example4.com");
        generalUniqueUrls.add("https://www.example5.com");
        generalUniqueUrls.add("https://www.example6.com");


        currentUniqueUrlsPeLevel.add("https://www.example2.com");
        currentUniqueUrlsPeLevel.add("https://www.example6.com");
        Mockito.when(validator.isValidUrl(Mockito.anyString())).thenReturn(true);

        extractor.extract(generalUniqueUrls, currentUniqueUrlsPeLevel, true);

        Assertions.assertEquals(3, currentUniqueUrlsPeLevel.size());
        Assertions.assertEquals(6, generalUniqueUrls.size());
        Assertions.assertTrue(currentUniqueUrlsPeLevel.contains("https://www.example.com"));
        Assertions.assertTrue(currentUniqueUrlsPeLevel.contains("https://www.example2.com"));
        Assertions.assertTrue(currentUniqueUrlsPeLevel.contains("https://www.example6.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example2.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example3.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example4.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example5.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example6.com"));
    }

    @Test
    void testExtract_should_return_uniques_urls() {
        Set<String> generalUniqueUrls = new HashSet<>();
        Set<String> currentUniqueUrlsPeLevel = new HashSet<>();

        generalUniqueUrls.add("https://www.example3.com");
        generalUniqueUrls.add("https://www.example4.com");
        generalUniqueUrls.add("https://www.example5.com");
        generalUniqueUrls.add("https://www.example6.com");
        generalUniqueUrls.add("https://www.example2.com");

        currentUniqueUrlsPeLevel.add("https://www.example6.com");
        Mockito.when(validator.isValidUrl(Mockito.anyString())).thenReturn(true);

        extractor.extract(generalUniqueUrls, currentUniqueUrlsPeLevel, false);

        Assertions.assertEquals(3, currentUniqueUrlsPeLevel.size());
        Assertions.assertEquals(6, generalUniqueUrls.size());
        Assertions.assertTrue(currentUniqueUrlsPeLevel.contains("https://www.example.com"));
        Assertions.assertTrue(currentUniqueUrlsPeLevel.contains("https://www.example2.com"));
        Assertions.assertTrue(currentUniqueUrlsPeLevel.contains("https://www.example6.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example2.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example3.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example4.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example5.com"));
        Assertions.assertTrue(generalUniqueUrls.contains("https://www.example6.com"));
    }
}
