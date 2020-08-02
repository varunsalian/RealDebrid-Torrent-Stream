package com.stream.fetcher;

import com.stream.exceptions.BadTypeException;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SourceUtils {

    public static List<Map<String, String>> getDataFromSource(SourceDTO torrentSourceDTO, String searchQuery) throws BadTypeException, IOException {
        Document document = FetcherUtils.getScrapDataFromUrl(torrentSourceDTO.getBaseUrl()+searchQuery);
        return  FetcherUtils.fetchTableFromDocument(torrentSourceDTO, document);
    }
}
