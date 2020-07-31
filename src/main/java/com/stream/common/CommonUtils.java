package com.stream.common;

import com.stream.exceptions.LinkUnavailableException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CommonUtils {


    public static HttpURLConnection getHttpUrlConnection(URL url, String token) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(CommonConstants.HTTP_GET);
        conn.setRequestProperty(CommonConstants.USER_AGENT, CommonConstants.USER_AGENT_DETAILS);
        if (token != null) {
            String bearer = CommonConstants.BEARER + token;
            conn.setRequestProperty(CommonConstants.AUTHORIZATION, bearer);
        }
        conn.connect();
        return conn;
    }

    public static String readJSON(InputStream inputStream){
        Scanner sc = new Scanner(inputStream);
        StringBuilder jsonString = new StringBuilder(CommonConstants.EMPTY_STRING);
        while(sc.hasNext())
            jsonString.append(sc.nextLine());
 //        sc.close();
        return jsonString.toString();
    }

    private CommonUtils(){

    }

    public static String getSubtitleCmdString(String imdbCode) {
        File file = new File(CommonConstants.SUBS_UNCOMPRESSED_FOLDER+imdbCode);
        File[] files = file.listFiles();
        String result = null;
        if(files!=null && files.length>0)
            result= "--sub-file="+files[0].getAbsolutePath();
        return result;
    }

    public static String askUserSearchQuery() {
        System.out.println(CommonConstants.USER_INPUT_MOVIE);
        Scanner scanner = new Scanner(System.in);
        String searchQuery = scanner.nextLine();
        searchQuery = searchQuery.replace(CommonConstants.STRING_SPACE, CommonConstants.STRING_UNDERSCORE);
        return searchQuery;
    }

    public static void startVlcProcess(String videoLink, String subs) throws IOException, LinkUnavailableException {
        if(videoLink==null)
            throw new LinkUnavailableException("Streaming Link For the movie is unavailable");
        ProcessBuilder pb;
        if(subs!=null)
            pb = new ProcessBuilder(CommonConstants.VLC_FILEPATH, videoLink, subs);
        else
            pb = new ProcessBuilder(CommonConstants.VLC_FILEPATH, videoLink);
        pb.start();
    }
}
