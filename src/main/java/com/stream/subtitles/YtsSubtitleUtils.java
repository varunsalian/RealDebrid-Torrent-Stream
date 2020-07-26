package com.stream.subtitles;
import com.stream.fetcher.FetcherUtils;
import com.stream.fetcher.SourceDTO;
import com.stream.fetcher.SourceUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class YtsSubtitleUtils {

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

    private static List<Map<String, String>> filterSubs(List<Map<String, String>> subs, String language) {
        return subs.stream().filter(a -> a.get("language").equals(language)).collect(Collectors.toList());
    }

    public static void addSubtitleFromImdbId(String imdbId) throws Exception {
        try {
            Map<String, SourceDTO> torrentSourceDTOS = FetcherUtils.loadSourceFromJson();
            List<Map<String, String>> searchResult = SourceUtils.getDataFromSource("ytssubs", torrentSourceDTOS, imdbId);
            List<Map<String, String>> filterredList = YtsSubtitleUtils.filterSubs(searchResult, "English");
            ExecutorService pool = Executors.newFixedThreadPool(10);
            FileUtils.deleteDirectory(new File("subtitle"));
            new File("subtitle/compressed/" + imdbId).mkdirs();
            for (int i = 0; i< (Math.min(filterredList.size(), 5)); i++) {
                pool.submit(new DownloadTask(filterredList.get(i).get("link"), imdbId + "-"+ i, imdbId));
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
