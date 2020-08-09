package com.stream.realdebrid.dtos;

import java.util.List;

public class AllTorrentsInfoDTO {

    private  String id;
    private  String filename;
    private  String hash;
    private long bytes;
    private  String host;
    private  int split;
    private  int progress;
    private String status;
    private String added;
    private List<String> links;
    private String ended;

    @Override
    public String toString() {
        return "AllTorrentsInfoDTO{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", hash='" + hash + '\'' +
                ", bytes=" + bytes +
                ", host='" + host + '\'' +
                ", split=" + split +
                ", progress=" + progress +
                ", status='" + status + '\'' +
                ", added='" + added + '\'' +
                ", links=" + links +
                ", ended='" + ended + '\'' +
                '}';
    }

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getSplit() {
        return split;
    }

    public void setSplit(int split) {
        this.split = split;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public AllTorrentsInfoDTO(){

    }
}
