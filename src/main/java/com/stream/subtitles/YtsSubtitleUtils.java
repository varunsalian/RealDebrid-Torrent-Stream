package com.stream.subtitles;

import com.stream.common.CommonConstants;
import com.stream.common.CommonUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class YtsSubtitleUtils {

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

    public static List<Map<String, String>> parseTable(Document doc, int tableOrder) {
        Element table = doc.select("table").get(tableOrder);
        Elements rows = table.select("tr");
        Elements first = rows.get(0).select("th,td");
        List<String> headers = new ArrayList<String>();
        for (int i = 0; i < first.size(); i++) {
            headers.add(first.get(i).text());
        }
        headers.add("link");
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int row = 1; row < rows.size(); row++) {
            Elements colVals = rows.get(row).select("th,td");
            int colCount = 0;
            Map<String, String> tuple = new HashMap<String, String>();
            for (Element colVal : colVals) {
                if (headers.get(colCount).equals("release")) {
                    tuple.put(headers.get(colCount), colVal.select("a").text());
                    tuple.put("link", colVal.select("a").first().absUrl("href"));
                } else {
                    tuple.put(headers.get(colCount), colVal.text());
                }
                colCount++;
            }
            listMap.add(tuple);
        }
        return listMap;
    }

    public static List<Map<String, String>> filterEnglishSubs(List<Map<String, String>> dd) {
        return dd.stream().filter(a -> a.get("language").equals("English")).collect(Collectors.toList());
    }

    public static void addSubtitleFromImdbId(String imdbId){
        try {
            Document document = YtsSubtitleUtils.getScrapDataFromUrl("https://yifysubtitles.org/movie-imdb/" + imdbId);
            List<Map<String, String>> orginalList = YtsSubtitleUtils.parseTable(document,0);
            List<Map<String, String>> filterredList = YtsSubtitleUtils.filterEnglishSubs(orginalList);
            ExecutorService pool = Executors.newFixedThreadPool(10);
            FileUtils.deleteDirectory(new File("subtitle"));
            new File("subtitle/compressed/" + imdbId).mkdirs();
            for (int i=0;i<filterredList.size();i++) {
                pool.submit(new DownloadTask(filterredList.get(i).get("link"), imdbId + "-"+ i, imdbId));
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
