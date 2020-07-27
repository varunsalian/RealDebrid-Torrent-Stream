RealDebrid-Torrent-Stream

General Overview: 

This is a simple console based application written in Java, to stream movies by searching from the torrents (currently supports YIFY) and play it in VLC media player via real-debrid.

NOTE: This requires a real-debrid account to work.

Technical Overview

1. Search query is taken from System.in.
2. Using yify api, deatils of all the movies matching the query is fetched. 
3. An api request is sent to Real-debrid to check if the available(cached) torrent hashes match the hashes of the searched torrents.
4. All the non cached torrents are removed and the user is shown the available torrents.
5. Suitable Subtitle is downloaded using yifysubtitles.
5. Once user selects the required option, the streamble link is fetched and a VLC process is started with the streamable URL.

Direct Download JAR and EXE

(Will be added soon)

Usage Instructions

(Will be added soon)

TODO List

1. Add subtitle support (maybe using opensubtitle)
2. Add more torrent sources and make it more generic(not specific to movies)
3. Refactor the code to be more generic
4. Handle API token of real-debrid.


