package com.stream.subtitles;
import com.stream.common.CommonConstants;
import com.stream.exceptions.BadTypeException;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class YtsSubtitleUtils {

    private static Logger logger = Logger.getLogger(YtsSubtitleUtils.class.getName());

    public static Document getScrapDataFromUrl(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent(CommonConstants.USER_AGENT_DETAILS)
                .referrer(CommonConstants.HTTP_REFERRER)
                .timeout(60_000)
                .followRedirects(true)
                .execute();
        return response.parse();
    }

    private static List<Map<String, String>> filterSubs(List<Map<String, String>> subs, String language) {
        return subs.stream().filter(a -> a.get(CommonConstants.SUBS_LANGUAGE).equals(language)).collect(Collectors.toList());
    }

    public static void addSubtitleFromImdbId(String imdbId) throws BadTypeException, InterruptedException {
        try {
            SourceDTO torrentSourceDTO = FetcherUtils.loadSourceFromJson(CommonConstants.YTS_SUBS_SOURCE);
            List<Map<String, String>> searchResult = SourceUtils.getDataFromSource(torrentSourceDTO, imdbId);
            List<Map<String, String>> filterredList = YtsSubtitleUtils.filterSubs(searchResult, CommonConstants.SUBS_ENGLISH);
            ExecutorService pool = Executors.newFixedThreadPool(10);
            FileUtils.deleteDirectory(new File(CommonConstants.SUBS_ROOT_FOLDER));
            new File(CommonConstants.SUBS_COMPRESSED_FOLDER + imdbId).mkdirs();
            for (int i = 0; i< (Math.min(filterredList.size(), 5)); i++) {
                pool.submit(new DownloadTask(filterredList.get(i).get(CommonConstants.SUBS_DOWNLOAD_LINK), imdbId + CommonConstants.SYMBOL_HYPHEN+ i, imdbId));
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private YtsSubtitleUtils(){

    }
}
