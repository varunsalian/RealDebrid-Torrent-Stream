package com.stream.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.exceptions.LinkUnavailableException;
import com.stream.realdebrid.DebridUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class CommonUtils {

    private static Logger logger = Logger.getLogger(DebridUtils.class.getName());

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

    private static Object getDataFromPropertiesFile(String key) throws IOException {
        Object filePath = null;
        ObjectMapper objectMapper = new ObjectMapper();
        File propertiesFile = new File(CommonConstants.SUPPORT_DIRECTORY + CommonConstants.FORWARD_SLASH + CommonConstants.APP_PROPERTIES + CommonConstants.JSON_EXTENSION);
        if(propertiesFile.exists()) {
            Map<String, Object> properties =  objectMapper.readValue(propertiesFile, new TypeReference<HashMap<String, Object>>() {
            });
            filePath = properties.getOrDefault(key, null);
        }
        return filePath;
    }

    public static void startVlcProcess(String videoLink, String subs) throws IOException, LinkUnavailableException {
        Object filePath = getDataFromPropertiesFile(CommonConstants.VLC_JSON_KEY);
        filePath = filePath==null? CommonConstants.VLC_FILEPATH: filePath;
        if(videoLink==null)
            throw new LinkUnavailableException("Streaming Link For the movie is unavailable");
        ProcessBuilder pb;
        if(subs!=null)
            pb = new ProcessBuilder((String) filePath, videoLink, subs);
        else
            pb = new ProcessBuilder((String) filePath, videoLink);
        pb.start();
    }

    public static void serializeAnObject(String fileName, Object object) {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    public static String convertHashesToRequestUrl(List<String> hashes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hashes.size(); i++) {
            if (i != hashes.size() - 1)
                stringBuilder.append(hashes.get(i)).append(CommonConstants.FORWARD_SLASH);
            else {
                stringBuilder.append(hashes.get(i));
            }
        }
        return stringBuilder.toString();
    }
}
