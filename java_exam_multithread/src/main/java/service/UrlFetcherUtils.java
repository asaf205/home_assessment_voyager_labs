package service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class UrlFetcherUtils{

    public Document fetchUrlDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error("Failed to fetch URL document: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            log.warn("Invalid URL: {}", e.getMessage());
        }
        return document;
    }

    public boolean isValidUrl(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            log.warn("Failed to check URL validity: {}", e.getMessage());
            return false;
        }
    }
}
