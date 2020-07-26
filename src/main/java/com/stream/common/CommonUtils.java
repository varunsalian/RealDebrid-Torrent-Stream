package com.stream.common;

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
        String result= "--sub-file="+files[0].getAbsolutePath();
        return result;
    }
}
