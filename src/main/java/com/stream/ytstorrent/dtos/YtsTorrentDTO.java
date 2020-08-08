package com.stream.ytstorrent.dtos;

import java.util.Objects;

public class YtsTorrentDTO {

    private String url;
    private String hash;
    private String quality;
    private String type;
    private Integer seeds;
    private Integer peers;
    private String size;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSeeds() {
        return seeds;
    }

    public void setSeeds(Integer seeds) {
        this.seeds = seeds;
    }

    public Integer getPeers() {
        return peers;
    }

    public void setPeers(Integer peers) {
        this.peers = peers;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        YtsTorrentDTO that = (YtsTorrentDTO) object;
        return Objects.equals(url, that.url) &&
                Objects.equals(hash, that.hash) &&
                Objects.equals(quality, that.quality) &&
                Objects.equals(type, that.type) &&
                Objects.equals(seeds, that.seeds) &&
                Objects.equals(peers, that.peers) &&
                Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, hash, quality, type, seeds, peers, size);
    }

    @Override
    public String toString() {
        return "com.varub.torrent.TorrentDTO{" +
                "url='" + url + '\'' +
                ", hash='" + hash + '\'' +
                ", quality='" + quality + '\'' +
                ", type='" + type + '\'' +
                ", seeds='" + seeds + '\'' +
                ", peers='" + peers + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    public static final class TorrentDTOBuilder {
        private String url;
        private String hash;
        private String quality;
        private String type;
        private Integer seeds;
        private Integer peers;
        private String size;

        private TorrentDTOBuilder() {
        }

        public static TorrentDTOBuilder aTorrentDTO() {
            return new TorrentDTOBuilder();
        }

        public TorrentDTOBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public TorrentDTOBuilder withHash(String hash) {
            this.hash = hash;
            return this;
        }

        public TorrentDTOBuilder withQuality(String quality) {
            this.quality = quality;
            return this;
        }

        public TorrentDTOBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public TorrentDTOBuilder withSeeds(Integer seeds) {
            this.seeds = seeds;
            return this;
        }

        public TorrentDTOBuilder withPeers(Integer peers) {
            this.peers = peers;
            return this;
        }

        public TorrentDTOBuilder withSize(String size) {
            this.size = size;
            return this;
        }

        public YtsTorrentDTO build() {
            YtsTorrentDTO torrentDTO = new YtsTorrentDTO();
            torrentDTO.setUrl(url);
            torrentDTO.setHash(hash);
            torrentDTO.setQuality(quality);
            torrentDTO.setType(type);
            torrentDTO.setSeeds(seeds);
            torrentDTO.setPeers(peers);
            torrentDTO.setSize(size);
            return torrentDTO;
        }
    }
}
