package com.stream.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.exceptions.LinkUnavailableException;
import com.stream.realdebrid.DebridUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    public static String readJSON(InputStream inputStream) throws IOException {
        return  IOUtils.toString(inputStream, StandardCharsets.UTF_8);
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
        System.console().writer().println(CommonConstants.USER_INPUT_MOVIE);
        String searchQuery = System.console().readLine();
        searchQuery = searchQuery.replace(CommonConstants.STRING_SPACE, CommonConstants.STRING_UNDERSCORE);
        return searchQuery;
    }

    private static Object getDataFromPropertiesFile(String key) throws IOException {
        Object data = null;
        ObjectMapper objectMapper = new ObjectMapper();
        File propertiesFile = new File(CommonConstants.SUPPORT_DIRECTORY + CommonConstants.FORWARD_SLASH + CommonConstants.APP_PROPERTIES + CommonConstants.JSON_EXTENSION);
        if(propertiesFile.exists()) {
            Map<String, Object> properties =  objectMapper.readValue(propertiesFile, new TypeReference<HashMap<String, Object>>() {
            });
            data = properties.getOrDefault(key, null);
        }
        return data;
    }

    private static Map<String, Object> getPropertiesFileAsMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File propertiesFile = new File(CommonConstants.SUPPORT_DIRECTORY + CommonConstants.FORWARD_SLASH + CommonConstants.APP_PROPERTIES + CommonConstants.JSON_EXTENSION);
        Map<String, Object> properties = null;
        if(propertiesFile.exists()) {
            properties = objectMapper.readValue(propertiesFile, new TypeReference<HashMap<String, Object>>() {
            });
        }
        return properties;
    }

    public static void startVlcProcess(String videoLink, String subs) throws IOException, LinkUnavailableException {
        Object filePath = getDataFromPropertiesFile(CommonConstants.VLC_JSON_KEY);
        filePath = filePath==null? CommonConstants.VLC_FILEPATH: filePath;
        if(videoLink==null)
            throw new LinkUnavailableException("Streaming Link For the movie is unavailable");
        List<String> arguments = new ArrayList<>();
        arguments.add((String) filePath);
        arguments.add(videoLink);
        processVlcArgumentsFromJson(arguments);
        if(subs!=null)
            arguments.add(subs);
        ProcessBuilder pb = new ProcessBuilder(arguments);
        pb.start();
    }

    private static void processVlcArgumentsFromJson(List<String> arguments) throws IOException {
        Map<String, Object> properties = getPropertiesFileAsMap();
        String temp;
        if(properties!=null) {
            if (properties.containsKey(CommonConstants.VLC_ASPECT_RATIO_KEY) && (temp = (String) properties.get(CommonConstants.VLC_ASPECT_RATIO_KEY)) != null && !temp.equals(CommonConstants.EMPTY_STRING))
                arguments.add(CommonConstants.VLC_ASPECT_RATIO_VALUE + CommonConstants.SYMBOL_EQUALS + temp);
            if(properties.containsKey(CommonConstants.VLC_FULLSCREEN_KEY) && (temp = (String) properties.get(CommonConstants.VLC_FULLSCREEN_KEY)) != null && temp.equalsIgnoreCase(CommonConstants.BOOLEAN_TRUE))
                arguments.add(CommonConstants.VLC_FULLSCREEN_VALUE);
        }
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

    public static void createDirectoryIfNotExists(String directory) {
        File file = new File(directory);
        if(!file.exists())
            file.mkdirs();
    }

}
