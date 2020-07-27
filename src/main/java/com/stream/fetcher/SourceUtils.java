package com.stream.fetcher;

import com.stream.exceptions.BadTypeException;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SourceUtils {

    public static List<Map<String, String>> getDataFromSource(String sourceName, Map<String, SourceDTO> torrentSourceDTOS, String searchQuery) throws BadTypeException, IOException {
        Document document = FetcherUtils.getScrapDataFromUrl(torrentSourceDTOS.get(sourceName).getBaseUrl()+searchQuery);
        return  FetcherUtils.fetchTableFromDocument(torrentSourceDTOS.get(sourceName), document);
    }
}
