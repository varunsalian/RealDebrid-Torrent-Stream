package com.stream.fetcher;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public class SourceUtils {

    public static List<Map<String, String>> getDataFromSource(String sourceName, Map<String, SourceDTO> torrentSourceDTOS, String searchQuery) throws Exception {
        Document document = FetcherUtils.getScrapDataFromUrl(torrentSourceDTOS.get(sourceName).getBaseUrl()+searchQuery);
        return  FetcherUtils.fetchTableFromDocument(torrentSourceDTOS.get(sourceName), document);
    }
}
