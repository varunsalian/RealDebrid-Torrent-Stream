package com.stream.realdebrid.dtos;

import java.util.List;

public class TorrentInfoDTO {
    private  String id;
    private  String filename;
    private  String originalFilename;
    private  String hash;
    private long bytes;
    private long originalBytes;
    private  String host;
    private  int split;
    private  int progress;
    private String status;
    private String added;
    private List<TorrentFilesDTO> files;
    private List<String> links;
    private String ended;
    private int speed;
    private int seeders;

    @Override
    public String toString() {
        return "TorrentInfoDTO{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", original_filename='" + originalFilename + '\'' +
                ", hash='" + hash + '\'' +
                ", bytes=" + bytes +
                ", original_bytes=" + originalBytes +
                ", host='" + host + '\'' +
                ", split=" + split +
                ", progress=" + progress +
                ", status='" + status + '\'' +
                ", added='" + added + '\'' +
                ", files=" + files +
                ", links=" + links +
                ", ended='" + ended + '\'' +
                ", speed=" + speed +
                ", seeders=" + seeders +
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

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
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

    public long getOriginalBytes() {
        return originalBytes;
    }

    public void setOriginalBytes(long originalBytes) {
        this.originalBytes = originalBytes;
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

    public List<TorrentFilesDTO> getFiles() {
        return files;
    }

    public void setFiles(List<TorrentFilesDTO> files) {
        this.files = files;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSeeders() {
        return seeders;
    }

    public void setSeeders(int seeders) {
        this.seeders = seeders;
    }
}
