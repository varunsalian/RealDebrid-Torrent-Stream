package com.stream.fetcher;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.exceptions.BadTypeException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetcherUtils {
    static Document getScrapDataFromUrl(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                .referrer("http://www.google.com")
                .timeout(60000)
                .followRedirects(true)
                .execute();
        return response.parse();
    }

    private static Object parser(String path, Class dtoClass) throws IOException {
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        ObjectMapper objectMapper = new ObjectMapper();
        Object obj = objectMapper.readValue(file, dtoClass);
        return obj;
    }

    private static String getMapKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> s : map.entrySet()) {
            if (s.getValue().equals(value))
                return s.getKey();
        }
        return null;
    }

    static List<Map<String, String>> fetchTableFromDocument(SourceDTO torrentSourceDTO, Document document) throws BadTypeException {
        int tableIndex = torrentSourceDTO.getTableIndex() == null ? 0 : torrentSourceDTO.getTableIndex();
        List<String> tableContent = torrentSourceDTO.getTableContent();
        Map<String, String> additionalContentRelation = torrentSourceDTO.getAdditionalContentRelation();
        Element table = document.select("table").get(tableIndex);
        Elements rows = table.select("tr");
        Map<String, Map<String, String>> contentSpecialCase = torrentSourceDTO.getContentSpecialCase();

        List<String> headers = new ArrayList<>(tableContent);
        headers.addAll(torrentSourceDTO.getAdditionalContent());

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int row = 1; row < rows.size(); row++) {
            Elements colVals = rows.get(row).select("th,td");
            int colCount = 0;
            Map<String, String> tuple = new HashMap<String, String>();
            for (Element colVal : colVals) {
                String currentHeader = headers.get(colCount);
                if (additionalContentRelation.containsValue(currentHeader)) {
                    String tupleData = contentSpecialCase.containsKey(currentHeader) ? selectStringFromCss(contentSpecialCase.get(currentHeader), colVal) : colVal.text();
                    tuple.put(headers.get(colCount), tupleData);
                    String additionalHeader = getMapKeyByValue(additionalContentRelation, currentHeader);
                    tuple.put(additionalHeader, selectStringFromCss(torrentSourceDTO.getAdditionalContentReference().get(additionalHeader), colVal));
                } else if (contentSpecialCase.containsKey(currentHeader)) {
                    String tupleData = selectStringFromCss(contentSpecialCase.get(currentHeader), colVal);
                    tuple.put(headers.get(colCount), tupleData);
                } else {
                    tuple.put(headers.get(colCount), colVal.text());
                }
                colCount++;
                if(colCount>=headers.size())
                    break;
            }
            listMap.add(tuple);
        }
        return listMap;
    }

    private static String selectStringFromCss(Map<String, String> data, Element element) throws BadTypeException {
        Object elementData = element;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            elementData = CssSelector.getData(entry.getKey(), entry.getValue(), elementData);
        }
        if (elementData instanceof String)
            return (String) elementData;
        return null;
    }

    public static SourceDTO getNextPageSource(Map<String, SourceDTO> torrentSourceDTOS, String page) {
        return torrentSourceDTOS.get(page);
    }

    public static SourceDTO loadSourceFromJson(String sourceName) throws IOException {
        return ((SourceDTO)parser(sourceName, SourceDTO.class));
    }


    private FetcherUtils(){

    }
}
