package com.stream.common;

import java.util.List;

public class CommonConstants {

    //HTTP REQUEST CONSTANTS
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String USER_AGENT ="User-Agent";
    public static final String USER_AGENT_DETAILS = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String URL_ENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String CHARSET = "application/x-www-form-urlencoded";
    public static final String UTF_8 = "utf-8";
    public static final String CONTENT_LENGTH = "Content-Length";

   //YTS SPECIFIC CONSTANTS
    public static final String LIST_MOVIRES_YTS_URL ="https://yts.mx/api/v2/list_movies.json";
    public static final String SEARCH_QUERY_YTS ="?query_term=";
    public static final String DATA = "data";
    public static final String MOVIES ="movies";

    //MAGNET SPECIFIC CONSTANTS
    public static final String MAGNET_URL = "magnet:?xt=urn:btih:";
    public static final String MAGNET_DOWNLOAD = "&dn=";
    public static final String MAGNET_TRACKER = "&tr=http://track.one:1234/announce&tr=udp://track.two:80";

    //USER INPUT CONSTANTS
    public static final String USER_INPUT_MOVIE = "Type the movie name";
    public static final String USER_INPUT_SELECT_OPTION = "Select the option number";
    public static final String USER_INPUT_GOTO = "Goto ";
    public static final String USER_INPUT_ENTER_CODE = " and enter this code  ";

    //STRING CONSTANTS
    public static final String STRING_SPACE = " ";
    public static final String EMPTY_STRING ="";
    public static final String STRING_UNDERSCORE = "_";
    public static final String DOT_AND_SPACE = ". ";
    public static final String FORWARD_SLASH = "/";
    public static final String SYMBOL_EQUALS = "=";
    public static final String SYMBOL_AND = "&";

    //REAL DEBRID SPECIFIC CONSTANTS
    public static final String DEBRID_OAUTH_URL= "https://api.real-debrid.com/oauth/v2";
    public static final String DEBRID_SECRET_ID_PATH = "/device/credentials?client_id=X245A4XAIBGVM&code=";
    public static final String DEBRID_TOKEN_PATH = "/token";
    public static final String DEBRID_AUTHENTICATION_PATH = "/device/code?client_id=X245A4XAIBGVM&new_credentials=yes";
    public static final String DEBRID_API_URL = "https://api.real-debrid.com/rest/1.0";
    public static final String DEBRID_TORRENT_INSTANT_AVAILABILITY_PATH = "/torrents/instantAvailability/";
    public static final String GRANT_TYPE_URL = "http://oauth.net/grant_type/device/1.0";


    public static final String MAGNET = "magnet";
    public static final String FILES = "files";
    public static final String LINK = "link";

    //ERROR CONSTANTS
    public static final String URL_INVALID = "INVALID URL";
    public static final String ERROR = "ERROR";
    public static final String ERROR_SESSION_TIMED_OUT = "SESSION TIMED OUT";
    public static final String ERROR_AUTHENTICATION_CONNECTION_FAILED = "Failed to connect to Authentication Server";

    //CLIENT POST
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String DEVICE_CODE = "code";
    public static final String GRANT_TYPE = "grant_type";

    // DESERIALIZATION CONSTANTS
    public static final String AUTHENTICATION_TXT = "authentication.txt";
    public static final String CREDENTIALS_TXT = "credentials.txt";
    public static final String ADD_MAGNET_PATH = "/torrents/addMagnet";
    public static final String SELECT_FILES_PATH = "/torrents/selectFiles/";
    public static final String TORRENT_INFO_PATH = "/torrents";
    public static final String UNRESTRICT_LINK_PATH = "/unrestrict/link";

    //SYSTEM SPECIFIC CONSTANTS
    public static final String VLC_FILEPATH = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";


    private CommonConstants(){

    }
}
