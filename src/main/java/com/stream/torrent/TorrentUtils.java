package com.stream.torrent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.stream.common.CommonConstants;
import com.stream.common.CommonUtils;
import com.stream.exceptions.ItemNotFoundException;
import com.stream.realdebrid.DebridUtils;
import com.stream.torrent.dtos.MovieDTO;
import com.stream.torrent.dtos.TorrentDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TorrentUtils {
    private static Logger logger = Logger.getLogger(TorrentUtils.class.getName());

    public static String magnetGenerator(String torrentHash, String urlEncodedMovieName) {
        return CommonConstants.MAGNET_URL + torrentHash + CommonConstants.MAGNET_DOWNLOAD + urlEncodedMovieName + CommonConstants.MAGNET_TRACKER;
    }

    private static List<MovieDTO> getMovieDataDtoFromJson(String jsonString) throws ItemNotFoundException {
        JSONObject data = (JSONObject) new JSONObject(jsonString).get(CommonConstants.DATA);
        JSONArray arr = null;
        if(!data.has(CommonConstants.MOVIES))
            throw new ItemNotFoundException("NO RESULT FOUND");
        arr = data.getJSONArray(CommonConstants.MOVIES);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<MovieDTO> dtos = null;

        try {
            dtos = objectMapper.readValue(arr.toString(), new TypeReference<List<MovieDTO>>() {
            });
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        return dtos;
    }

    public static String askUserSearchQuery() {
        System.out.println(CommonConstants.USER_INPUT_MOVIE);
        Scanner scanner = new Scanner(System.in);
        String searchQuery = scanner.nextLine();
        searchQuery = searchQuery.replace(CommonConstants.STRING_SPACE, CommonConstants.STRING_UNDERSCORE);
        return searchQuery;
    }

    private static Integer askUserOption() {
        System.out.println(CommonConstants.USER_INPUT_SELECT_OPTION);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static List<MovieDTO> ytsTorrentSearch(String searchQuery) {
        String jsonString;
        List<MovieDTO> dtos = null;

        try {
            URL url = new URL(CommonConstants.LIST_MOVIRES_YTS_URL + CommonConstants.SEARCH_QUERY_YTS + searchQuery);
            HttpURLConnection conn = CommonUtils.getHttpUrlConnection(url, null);
            if (conn.getResponseCode() == 200) {
                jsonString = CommonUtils.readJSON(conn.getInputStream());
                dtos = TorrentUtils.getMovieDataDtoFromJson(jsonString);
            } else {
                throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
            }
        } catch (IOException | ItemNotFoundException e) {
            logger.warning(e.getMessage());
        }
        return dtos;
    }

    public static MovieDTO searchAndSelectMovie(String searchQuery) throws IOException {
        MovieDTO selectedMovie = null;
        if (searchQuery != null) {
            List<MovieDTO> movieDTOS = TorrentUtils.ytsTorrentSearch(searchQuery);
            DebridUtils.removeNonInstantlyAvailableTorrents(movieDTOS);
            if (movieDTOS != null) {
                selectedMovie = selectMovie(movieDTOS);
            }
        }
        return selectedMovie;
    }

    private static MovieDTO selectMovie(List<MovieDTO> movieDTOS) {
        MovieDTO selectedMovie = null;
        AtomicInteger i = new AtomicInteger(1);
        final Map<Integer, MovieDTO> mapOfDtos = movieDTOS.stream().collect(Collectors.toMap(c -> i.getAndIncrement(), c -> c));
        mapOfDtos.keySet().forEach(c -> System.out.println(c + CommonConstants.DOT_AND_SPACE + mapOfDtos.get(c).getTitleLong()));
        boolean isWrongSelection = true;
        while (isWrongSelection) {
            Integer selection = TorrentUtils.askUserOption();
            if (selection <= mapOfDtos.size()) {
                selectedMovie = mapOfDtos.get(selection);
                isWrongSelection = false;
            }
        }
        return selectedMovie;
    }

    public static TorrentDTO selectQuality(MovieDTO selectedMovie) {
        TorrentDTO selectedTorrents = null;
        AtomicInteger i = new AtomicInteger(1);
        final Map<Integer, TorrentDTO> mapOfDtos = selectedMovie.getTorrents().stream().collect(Collectors.toMap(c -> i.getAndIncrement(), c -> c));
        mapOfDtos.keySet().forEach(c -> System.out.println(c + CommonConstants.DOT_AND_SPACE + mapOfDtos.get(c).getQuality()));
        boolean isWrongSelection = true;
        while (isWrongSelection) {
            Integer selection = TorrentUtils.askUserOption();
            if (selection <= mapOfDtos.size()) {
                selectedTorrents = mapOfDtos.get(selection);
                isWrongSelection = false;
            }
        }
        return selectedTorrents;
    }

    private TorrentUtils(){
    }
}
