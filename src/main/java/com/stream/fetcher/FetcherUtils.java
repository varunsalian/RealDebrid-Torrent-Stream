package com.stream.fetcher;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.exceptions.BadTypeException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FetcherUtils {
    public static Document getScrapDataFromUrl(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                .referrer("http://www.google.com")
                .timeout(60000)
                .followRedirects(true)
                .execute();
        Document document = response.parse();
        return document;
    }

    private static void parser(String path, Class dtoClass, Consumer<Object> consumer) throws IOException {
        File file = new File(path);
        File[] files = file.listFiles();
        ObjectMapper objectMapper = new ObjectMapper();
        if (files != null) {
            for (File value : files) {
                if (value.getName().endsWith(".json")) {
                    Object obj = objectMapper.readValue(value, dtoClass);
                    consumer.accept((obj));
                }
            }
        }
    }

    private static String getMapKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> s : map.entrySet()) {
            if (s.getValue().equals(value))
                return s.getKey();
        }
        return null;
    }

    public static List<Map<String, String>> fetchTableFromDocument(SourceDTO torrentSourceDTO, Document document) throws BadTypeException {
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

    public static Map<String, SourceDTO> loadSourceFromJson() throws IOException {
        Map<String, SourceDTO> torrentSourceDTOS = new HashMap<>();
        FetcherUtils.parser("data/", SourceDTO.class, (dtoClass)-> {
            SourceDTO torrentSourceDTO = (SourceDTO) dtoClass;
            boolean validationSuccess = validateTorrentSource(torrentSourceDTO);
            if(validationSuccess)
                torrentSourceDTOS.put(torrentSourceDTO.getSourceName(), torrentSourceDTO);
        });
        return torrentSourceDTOS;
    }

    private static boolean validateTorrentSource(SourceDTO torrentSourceDTO) {
        return true;
    }
}
