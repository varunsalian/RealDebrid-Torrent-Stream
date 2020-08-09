package com.stream.realdebrid.dtos;

public class TorrentFilesDTO {

    private int id;
    private String path;
    private long bytes;
    private int selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "TorrentFilesDTO{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", bytes=" + bytes +
                ", selected=" + selected +
                '}';
    }

    public TorrentFilesDTO(){

    }
}
