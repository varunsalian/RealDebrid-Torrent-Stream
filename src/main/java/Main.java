import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.common.CommonConstants;
import com.stream.common.CommonUtils;
import com.stream.common.CredentialsDTO;
import com.stream.realdebrid.DebridUtils;
import com.stream.realdebrid.dtos.*;
import com.stream.torrent.TorrentUtils;
import com.stream.torrent.dtos.MovieDTO;
import com.stream.torrent.dtos.TorrentDTO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)  {

        ObjectMapper objectMapper = DebridUtils.getObjectMapper();

        try {
            CredentialsDTO credentialsDTO = CredentialsDTO.getInstance();
            TokenDTO tokenDTO = credentialsDTO.getTokenDTO();


            String searchQuery = TorrentUtils.askUserSearchQuery();
            MovieDTO selectedMovie = TorrentUtils.searchAndSelectMovie(searchQuery);
            TorrentDTO selectedTorrent = TorrentUtils.selectQuality(selectedMovie);

            LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
            String magnet = TorrentUtils.magnetGenerator(selectedTorrent.getHash(), selectedMovie.getSlug());
            hashMap.put(CommonConstants.MAGNET, magnet);
            String a = DebridUtils.postAndGetData(CommonConstants.DEBRID_API_URL + CommonConstants.ADD_MAGNET_PATH, tokenDTO.getAccessToken(), hashMap);
            AddMagnetDTO magnetDTO = objectMapper.readValue(a, AddMagnetDTO.class);
            HttpURLConnection httpURLConnection = CommonUtils.getHttpUrlConnection(new URL(magnetDTO.getUri()), tokenDTO.getAccessToken());
            if (httpURLConnection.getResponseCode()==200){
                String json = CommonUtils.readJSON(httpURLConnection.getInputStream());
                TorrentInfoDTO torrentInfoDTO = objectMapper.readValue(json, TorrentInfoDTO.class);
                int selectedFileId = DebridUtils.selectCorrectFileId(torrentInfoDTO);
                LinkedHashMap<String, String> hashMap1 = new LinkedHashMap<>();
                hashMap1.put(CommonConstants.FILES,String.valueOf(selectedFileId));
                 DebridUtils.postAndGetData(CommonConstants.DEBRID_API_URL + CommonConstants.SELECT_FILES_PATH + magnetDTO.getId(), tokenDTO.getAccessToken(), hashMap1);

                //
                httpURLConnection = CommonUtils.getHttpUrlConnection(new URL(CommonConstants.DEBRID_API_URL + CommonConstants.TORRENT_INFO_PATH), tokenDTO.getAccessToken());
                List<AllTorrentsInfoDTO> allinfo=null;
                if (httpURLConnection.getResponseCode()==200){
                    json = CommonUtils.readJSON(httpURLConnection.getInputStream());
                    allinfo = objectMapper.readValue(json, new TypeReference<List<AllTorrentsInfoDTO>>(){});
                }

                String selectedLink = DebridUtils.getLinkOfSelectedTorrentFromTorrentInfo(torrentInfoDTO, allinfo);
                LinkedHashMap<String, String> hashMap11 = new LinkedHashMap<>();
                hashMap11.put(CommonConstants.LINK, selectedLink);
                String s = DebridUtils.postAndGetData(CommonConstants.DEBRID_API_URL + CommonConstants.UNRESTRICT_LINK_PATH, tokenDTO.getAccessToken(), hashMap11);
                UnrestrictDTO unrestrictDTO = objectMapper.readValue(s, UnrestrictDTO.class);
                ProcessBuilder pb = new ProcessBuilder(CommonConstants.VLC_FILEPATH, unrestrictDTO.getDownload());  //"--sub-file=D:\\b.srt"
                pb.start();
            }
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    static {
        try {
            ObjectMapper objectMapper = DebridUtils.getObjectMapper();
            AuthenticationDTO authenticationDTO =  new AuthenticationDTO();
            ClientDTO clientDTO = new ClientDTO();
            if (!(DebridUtils.checkIfFileExists(CommonConstants.AUTHENTICATION_TXT) && DebridUtils.checkIfFileExists(CommonConstants.CREDENTIALS_TXT))) {
                DebridUtils.dooer();
            }
            DebridUtils.deserializeAuthenticationAndClientDTO(authenticationDTO, clientDTO);
            String tokenJson = DebridUtils.postAndGetAccessToken(clientDTO.getClientID(), clientDTO.getClientSecret(), authenticationDTO.getDeviceCode(), CommonConstants.GRANT_TYPE_URL);
            if (tokenJson == null) {
                DebridUtils.dooer();
                DebridUtils.deserializeAuthenticationAndClientDTO(authenticationDTO, clientDTO);
                tokenJson = DebridUtils.postAndGetAccessToken(clientDTO.getClientID(), clientDTO.getClientSecret(), authenticationDTO.getDeviceCode(), CommonConstants.GRANT_TYPE_URL);
            }
            TokenDTO tokenDTO = objectMapper.readValue(tokenJson, TokenDTO.class);
            CredentialsDTO credentialsDTO = CredentialsDTO.getInstance();

            credentialsDTO.setAuthenticationDTO(authenticationDTO);
            credentialsDTO.setClientDTO(clientDTO);
            credentialsDTO.setTokenDTO(tokenDTO);
//            DebridUtils.accessToken = tokenDTO.getAccessToken();
        } catch (InterruptedException | IOException | ClassNotFoundException e) {
            logger.warning(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
}
