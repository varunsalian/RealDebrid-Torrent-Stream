package com.stream.ytstorrent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.stream.common.CommonConstants;
import com.stream.common.CommonUtils;
import com.stream.exceptions.ConnectionException;
import com.stream.exceptions.ItemNotFoundException;
import com.stream.realdebrid.DebridUtils;
import com.stream.ytstorrent.dtos.YtsMovieDTO;
import com.stream.ytstorrent.dtos.YtsTorrentDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class YtsTorrentUtils {
    private static Logger logger = Logger.getLogger(YtsTorrentUtils.class.getName());

    public static String magnetGenerator(String torrentHash, String urlEncodedMovieName) {
        return CommonConstants.MAGNET_URL + torrentHash + CommonConstants.MAGNET_DOWNLOAD + urlEncodedMovieName + CommonConstants.MAGNET_TRACKER;
    }

    private static List<YtsMovieDTO> getMovieDataDtoFromJson(String jsonString) throws ItemNotFoundException {
        JSONObject data = (JSONObject) new JSONObject(jsonString).get(CommonConstants.DATA);
        if(!data.has(CommonConstants.MOVIES)) {
            throw new ItemNotFoundException("NO RESULT FOUND");
        }
        JSONArray arr = data.getJSONArray(CommonConstants.MOVIES);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<YtsMovieDTO> dtos = null;

        try {
            dtos = objectMapper.readValue(arr.toString(), new TypeReference<List<YtsMovieDTO>>() {
            });
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        return dtos;
    }

    private static Integer askUserOption() {
        System.console().writer().println(CommonConstants.USER_INPUT_SELECT_OPTION);
        return Integer.parseInt(System.console().readLine());
    }

    private static List<YtsMovieDTO> ytsTorrentSearch(String searchQuery) throws ItemNotFoundException {
        String jsonString;
        List<YtsMovieDTO> dtos = null;

        try {
            URL url = new URL(CommonConstants.LIST_MOVIRES_YTS_URL + CommonConstants.SEARCH_QUERY_YTS + searchQuery);
            HttpURLConnection conn = CommonUtils.getHttpUrlConnection(url, null);
            if (conn.getResponseCode() == 200) {
                jsonString = CommonUtils.readJSON(conn.getInputStream());
                dtos = YtsTorrentUtils.getMovieDataDtoFromJson(jsonString);
            } else {
                throw new ConnectionException("HttpResponseCode: " + conn.getResponseCode());
            }
        } catch (IOException | ConnectionException e) {
            logger.warning(e.getMessage());
        }
        return dtos;
    }

    public static YtsMovieDTO searchAndSelectMovie(String searchQuery) throws IOException, ItemNotFoundException, ConnectionException {
        YtsMovieDTO selectedMovie = null;
        if (searchQuery != null) {
            List<YtsMovieDTO> movieDTOS = YtsTorrentUtils.ytsTorrentSearch(searchQuery);
            DebridUtils.removeNonInstantlyAvailableTorrents(movieDTOS);
            if (movieDTOS != null) {
                selectedMovie = selectMovie(movieDTOS);
            }
        }
        return selectedMovie;
    }

    private static YtsMovieDTO selectMovie(List<YtsMovieDTO> movieDTOS) {
        YtsMovieDTO selectedMovie = null;
        AtomicInteger integer = new AtomicInteger(1);
        final Map<Integer, YtsMovieDTO> mapOfDtos = movieDTOS.stream().collect(Collectors.toMap(c -> integer.getAndIncrement(), c -> c));
        mapOfDtos.keySet().forEach(c -> System.console().writer().println(c + CommonConstants.DOT_AND_SPACE + mapOfDtos.get(c).getTitleLong()));
        boolean isWrongSelection = true;
        while (isWrongSelection) {
            Integer selection = YtsTorrentUtils.askUserOption();
            if (selection <= mapOfDtos.size()) {
                selectedMovie = mapOfDtos.get(selection);
                isWrongSelection = false;
            }
        }
        return selectedMovie;
    }

    public static YtsTorrentDTO selectQuality(YtsMovieDTO selectedMovie) {
        YtsTorrentDTO selectedTorrents = null;
        AtomicInteger integer = new AtomicInteger(1);
        final Map<Integer, YtsTorrentDTO> mapOfDtos = selectedMovie.getTorrents().stream().collect(Collectors.toMap(c -> integer.getAndIncrement(), c -> c));
        mapOfDtos.keySet().forEach(c -> System.console().writer().println(c + CommonConstants.DOT_AND_SPACE + mapOfDtos.get(c).getQuality()));
        boolean isWrongSelection = true;
        while (isWrongSelection) {
            Integer selection = YtsTorrentUtils.askUserOption();
            if (selection <= mapOfDtos.size()) {
                selectedTorrents = mapOfDtos.get(selection);
                isWrongSelection = false;
            }
        }
        return selectedTorrents;
    }

    private YtsTorrentUtils(){
    }
}
