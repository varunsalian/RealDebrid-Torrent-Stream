package com.stream.realdebrid.dtos;

public class UnrestrictDTO {

    private String id;
    private String filename;
    private String mimetype;
    private long filesize;
    private String link;
    private String host;
    private int chunks;
    private int crc;
    private String download;
    private int streamable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public int getCrc() {
        return crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public int getStreamable() {
        return streamable;
    }

    public void setStreamable(int streamable) {
        this.streamable = streamable;
    }

    @Override
    public String toString() {
        return "UnrestrictDTO{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", mimetype='" + mimetype + '\'' +
                ", filesize=" + filesize +
                ", link='" + link + '\'' +
                ", host='" + host + '\'' +
                ", chunks=" + chunks +
                ", crc=" + crc +
                ", downloads='" + download + '\'' +
                ", streamable=" + streamable +
                '}';
    }
}
