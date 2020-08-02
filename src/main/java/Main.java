import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.common.CommonConstants;
import com.stream.common.CommonUtils;
import com.stream.common.CredentialsDTO;
import com.stream.exceptions.*;
import com.stream.realdebrid.DebridUtils;
import com.stream.realdebrid.dtos.*;
import com.stream.subtitles.YtsSubtitleUtils;
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
    private static ObjectMapper objectMapper = DebridUtils.getObjectMapper();

    public static void main(String[] args) {
        try {
            DebridUtils.manageRealDebridAuthentication();
            String searchQuery = CommonUtils.askUserSearchQuery();
            ytsMovieStream(searchQuery);
        }
        catch (ItemNotFoundException | ConnectionException | IOException | LinkUnavailableException | RealDebridException | BadTypeException e) {
            logger.warning(e.getMessage());
        } catch (InterruptedException e) {
            logger.warning(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static void ytsMovieStream(String searchQuery) throws IOException, ItemNotFoundException, ConnectionException, RealDebridException, BadTypeException, InterruptedException, LinkUnavailableException {
            //Get Real Debrid access token
            //TODO: check if token has expired, (currently new access token is fetched everytime)
            CredentialsDTO credentialsDTO = CredentialsDTO.getInstance();
            TokenDTO tokenDTO = credentialsDTO.getTokenDTO();

            //Search for a movie and select the quality
            MovieDTO selectedMovie = TorrentUtils.searchAndSelectMovie(searchQuery);
            TorrentDTO selectedTorrent = TorrentUtils.selectQuality(selectedMovie);

            //Get magnet URI of the torrent, add it to real debrid
            LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
            String magnet = TorrentUtils.magnetGenerator(selectedTorrent.getHash(), selectedMovie.getSlug());
            hashMap.put(CommonConstants.MAGNET, magnet);
            String a = DebridUtils.postAndGetData(CommonConstants.DEBRID_API_URL + CommonConstants.ADD_MAGNET_PATH, tokenDTO.getAccessToken(), hashMap);
            AddMagnetDTO magnetDTO = objectMapper.readValue(a, AddMagnetDTO.class);

            //Check if the torrent is added to rd and fetch the details
            HttpURLConnection httpURLConnection = CommonUtils.getHttpUrlConnection(new URL(magnetDTO.getUri()), tokenDTO.getAccessToken());
            if (httpURLConnection.getResponseCode() != 200)
                throw new ConnectionException(CommonConstants.UNABLE_TO_CONNECT + httpURLConnection.getResponseCode());
            String json = CommonUtils.readJSON(httpURLConnection.getInputStream());
            TorrentInfoDTO torrentInfoDTO = objectMapper.readValue(json, TorrentInfoDTO.class);

            //Select the file ID of the file with highest size and add it to download
            int selectedFileId = DebridUtils.selectCorrectFileId(torrentInfoDTO);
            LinkedHashMap<String, String> hashMap1 = new LinkedHashMap<>();
            hashMap1.put(CommonConstants.FILES, String.valueOf(selectedFileId));
            DebridUtils.postAndGetData(CommonConstants.DEBRID_API_URL + CommonConstants.SELECT_FILES_PATH + magnetDTO.getId(), tokenDTO.getAccessToken(), hashMap1);

            //Get the list of all downloaded items, pick the selected one.
            httpURLConnection = CommonUtils.getHttpUrlConnection(new URL(CommonConstants.DEBRID_API_URL + CommonConstants.TORRENT_INFO_PATH), tokenDTO.getAccessToken());
            if (httpURLConnection.getResponseCode() != 200)
                throw new ConnectionException(CommonConstants.UNABLE_TO_CONNECT + httpURLConnection.getResponseCode());
            json = CommonUtils.readJSON(httpURLConnection.getInputStream());
            List<AllTorrentsInfoDTO> allinfo = objectMapper.readValue(json, new TypeReference<List<AllTorrentsInfoDTO>>() {
            });
            String selectedLink = DebridUtils.getLinkOfSelectedTorrentFromTorrentInfo(torrentInfoDTO, allinfo);

            //Unrestrict the link
            LinkedHashMap<String, String> hashMap11 = new LinkedHashMap<>();
            hashMap11.put(CommonConstants.LINK, selectedLink);
            String s = DebridUtils.postAndGetData(CommonConstants.DEBRID_API_URL + CommonConstants.UNRESTRICT_LINK_PATH, tokenDTO.getAccessToken(), hashMap11);
            UnrestrictDTO unrestrictDTO = objectMapper.readValue(s, UnrestrictDTO.class);

            //play the video via VLC
            YtsSubtitleUtils.addSubtitleFromImdbId(selectedMovie.getImdbCode());
            String subs = CommonUtils.getSubtitleCmdString(selectedMovie.getImdbCode());
            CommonUtils.startVlcProcess(unrestrictDTO.getDownload(), subs);
    }
}
