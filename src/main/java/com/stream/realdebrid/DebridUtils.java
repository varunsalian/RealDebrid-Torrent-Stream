package com.stream.realdebrid;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.stream.common.CommonUtils;
import com.stream.common.CredentialsDTO;
import com.stream.realdebrid.dtos.*;
import com.stream.torrent.dtos.MovieDTO;
import com.stream.common.CommonConstants;
import com.stream.torrent.dtos.TorrentDTO;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class DebridUtils {

    private static Logger logger = Logger.getLogger(DebridUtils.class.getName());

    private static  String repeatedCallToGetSecretId(AuthenticationDTO authenticationDTO) throws IOException, InterruptedException {
        URL url1 = new URL(CommonConstants.DEBRID_OAUTH_URL + CommonConstants.DEBRID_SECRET_ID_PATH+ authenticationDTO.getDeviceCode());
        HttpURLConnection httpURLConnection;
        for(int i=0;i<120;i++) {
            httpURLConnection = CommonUtils.getHttpUrlConnection(url1, null);
            if(httpURLConnection.getResponseCode()==200)
                return CommonUtils.readJSON(httpURLConnection.getInputStream());
            else if(httpURLConnection.getResponseCode()==400)
                throw new RuntimeException(CommonConstants.URL_INVALID);
            else {
                System.out.println(httpURLConnection.getResponseCode() + "   " + httpURLConnection.getResponseMessage());
                System.out.println(CommonConstants.ERROR);
            }
            Thread.sleep(5000);
        }
        return null;
    }

    private static void setUrlPropertiesForPost(HttpURLConnection urlConnection, String accessToken) throws ProtocolException {
        urlConnection.setRequestMethod(CommonConstants.HTTP_POST);
        urlConnection.setDoOutput(true);
        urlConnection.setInstanceFollowRedirects(false);
        urlConnection.setRequestProperty(CommonConstants.CONTENT_TYPE, CommonConstants.URL_ENCODED_CONTENT_TYPE);
        urlConnection.setRequestProperty(CommonConstants.CHARSET, CommonConstants.UTF_8);
        if(accessToken!=null) {
            String bearer = CommonConstants.BEARER + accessToken;
            urlConnection.setRequestProperty(CommonConstants.AUTHORIZATION, bearer);
        }
        urlConnection.setRequestProperty(CommonConstants.USER_AGENT, CommonConstants.USER_AGENT_DETAILS);
    }

    private static String generateUrlEncodedAttributesForPost(Map<String, String> hashMap){
        final String[] data = {CommonConstants.EMPTY_STRING};
        AtomicInteger i= new AtomicInteger();
        hashMap.forEach((a, b)-> {
            if(i.get() ==0){
                data[0] += a + CommonConstants.SYMBOL_EQUALS + b;
                i.getAndIncrement();
            }
            else {
                data[0] += CommonConstants.SYMBOL_AND + a + CommonConstants.SYMBOL_EQUALS + b;
            }
        });
        return data[0];
    }

    public static String postAndGetAccessToken(String clientId, String clientSecret, String deviceCode, String grantType) throws IOException {
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(CommonConstants.DEBRID_OAUTH_URL + CommonConstants.DEBRID_TOKEN_PATH);
            urlConnection = (HttpURLConnection) url.openConnection();
            setUrlPropertiesForPost(urlConnection, null);
            LinkedHashMap<String, String> hashMap= new LinkedHashMap<>();
            hashMap.put(CommonConstants.CLIENT_ID, clientId);
            hashMap.put(CommonConstants.CLIENT_SECRET, clientSecret);
            hashMap.put(CommonConstants.DEVICE_CODE, deviceCode);
            hashMap.put(CommonConstants.GRANT_TYPE, grantType);
            String data =  generateUrlEncodedAttributesForPost(hashMap);

            byte[] postData = data.getBytes( StandardCharsets.UTF_8 );
            int postDataLength = postData.length;
            urlConnection.setRequestProperty(CommonConstants.CONTENT_LENGTH, Integer.toString(postDataLength ));
            urlConnection.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.write(postData);
            dataOutputStream.flush();
            if (urlConnection.getResponseCode()==200)
                return  CommonUtils.readJSON(urlConnection.getInputStream());
            if (urlConnection.getResponseCode()==401)
                return null;
       } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    public static String postAndGetData(String urlString, String accessTokens, Map<String, String> hashMap) throws IOException {
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            setUrlPropertiesForPost(urlConnection, accessTokens);
            String data =  generateUrlEncodedAttributesForPost(hashMap);

            byte[] postData = data.getBytes( StandardCharsets.UTF_8 );
            int postDataLength = postData.length;
            urlConnection.setRequestProperty(CommonConstants.CONTENT_LENGTH, Integer.toString(postDataLength ));
            urlConnection.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.write(postData);
            dataOutputStream.flush();
            if (urlConnection.getResponseCode()==200 || urlConnection.getResponseCode()==201)
                return  CommonUtils.readJSON(urlConnection.getInputStream());
            if (urlConnection.getResponseCode()==401)
                return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


    private static AuthenticationDTO getAuthenticationDTO() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        URL url = new URL(CommonConstants.DEBRID_OAUTH_URL + CommonConstants.DEBRID_AUTHENTICATION_PATH);
        HttpURLConnection httpURLConnection = CommonUtils.getHttpUrlConnection(url, null);
        if(httpURLConnection.getResponseCode()==200) {
            String jsonString = CommonUtils.readJSON(httpURLConnection.getInputStream());
            AuthenticationDTO authenticationDTO = objectMapper.readValue(jsonString, AuthenticationDTO.class);
            System.out.println(CommonConstants.USER_INPUT_GOTO+ authenticationDTO.getVerificationUrl() + CommonConstants.USER_INPUT_ENTER_CODE + authenticationDTO.getUserCode());
            return authenticationDTO;
        }
        return null;
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private static boolean serializeAnObject(String fileName, Object object) {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        return true;
    }

    public static void dooer() throws IOException, InterruptedException {
        ObjectMapper objectMapper = getObjectMapper();
        String jsonString;

        AuthenticationDTO authenticationDTO = getAuthenticationDTO();
        if (authenticationDTO==null)
            throw new RuntimeException(CommonConstants.ERROR_AUTHENTICATION_CONNECTION_FAILED);
        serializeAnObject(CommonConstants.AUTHENTICATION_TXT, authenticationDTO);

        jsonString = repeatedCallToGetSecretId(authenticationDTO);
        if (jsonString==null)
            throw new RuntimeException(CommonConstants.ERROR_AUTHENTICATION_CONNECTION_FAILED);
        ClientDTO clientDTO = objectMapper.readValue(jsonString,ClientDTO.class);
        System.out.println(clientDTO.toString());
        serializeAnObject(CommonConstants.CREDENTIALS_TXT, clientDTO);


        jsonString = postAndGetAccessToken(clientDTO.getClientID(), clientDTO.getClientSecret(), authenticationDTO.getDeviceCode(), CommonConstants.GRANT_TYPE_URL);
        if(jsonString==null){
            throw new RuntimeException(CommonConstants.ERROR_SESSION_TIMED_OUT);
        }
        TokenDTO tokenDTO = objectMapper.readValue(jsonString, TokenDTO.class);
        System.out.println(tokenDTO);
    }

    public static boolean checkIfFileExists(String fileName){
        File file = new File(fileName);
        return file.exists();
    }

    public static void deserializeAuthenticationAndClientDTO(AuthenticationDTO authenticationDTO, ClientDTO credentialsDTO) throws IOException, ClassNotFoundException {
        authenticationDTO.setAll((AuthenticationDTO) new ObjectInputStream(new FileInputStream(CommonConstants.AUTHENTICATION_TXT)).readObject());
        credentialsDTO.setAll((ClientDTO) new ObjectInputStream(new FileInputStream(CommonConstants.CREDENTIALS_TXT)).readObject());
    }

    public static int selectCorrectFileId(TorrentInfoDTO torrentInfoDTO) {
        List<TorrentFilesDTO> files = torrentInfoDTO.getFiles();
        int id=1;
        long highestSize = 0;
        for(int i=0; i <files.size();i++){
            if(files.get(i).getBytes()>highestSize)    //WAS 0
            {
                id = files.get(i).getId();     //WAS 0
                highestSize = files.get(i).getBytes();      // WAS )
            }
        }
        return id;
    }

    public static String getLinkOfSelectedTorrentFromTorrentInfo(TorrentInfoDTO selectedTorrent, List<AllTorrentsInfoDTO> allinfo) {
        for(AllTorrentsInfoDTO allTorrentsInfoDTO: allinfo){
            if(selectedTorrent.getHash().equals(allTorrentsInfoDTO.getHash()))
                return allTorrentsInfoDTO.getLinks().get(0);
        }
        return null;
    }

    public static void removeNonInstantlyAvailableTorrents(List<MovieDTO> movieDTOS) throws IOException {
        List<String> hashes = getHashesOfTorrentsFromMovieDTO(movieDTOS);
        String hashesString = convertHashesToRequestUrl(hashes);
        String json = getRequestToCheckInstantAvailability(hashesString);
        JSONObject jsonObject = new JSONObject(json);
        Map instantAvailabilityMap = jsonObject.toMap();
        for (int i = 0; i < movieDTOS.size(); i++) {
            MovieDTO movieDTO = movieDTOS.get(i);
            for (int j = 0; j < movieDTO.getTorrents().size(); j++) {
                TorrentDTO torrentDTO = movieDTO.getTorrents().get(j);
                if (!(instantAvailabilityMap.get(torrentDTO.getHash().toLowerCase()) instanceof Map)) {
                    movieDTO.getTorrents().remove(j--);
                }
            }
            if (movieDTO.getTorrents().isEmpty())
                movieDTOS.remove(i--);
        }
    }

    private static String getRequestToCheckInstantAvailability(String hashesString) throws IOException {
        String accessToken = CredentialsDTO.getInstance().getTokenDTO().getAccessToken();
        if(accessToken==null)
            throw new RuntimeException("Credentials Unavailable");
        HttpURLConnection checkedConnection = CommonUtils.getHttpUrlConnection(new URL(CommonConstants.DEBRID_API_URL + CommonConstants.DEBRID_TORRENT_INSTANT_AVAILABILITY_PATH + hashesString), accessToken);
        return CommonUtils.readJSON(checkedConnection.getInputStream());
    }

    private static String convertHashesToRequestUrl(List<String> hashes) {
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

    private static List<String> getHashesOfTorrentsFromMovieDTO(List<MovieDTO> movieDTOS) {
        List<String> hashes = new ArrayList<>();
        movieDTOS.forEach(movieDTO -> movieDTO.getTorrents().forEach(torrentDTO -> hashes.add(torrentDTO.getHash())));
        return hashes;
    }

    private DebridUtils(){

    }
}
