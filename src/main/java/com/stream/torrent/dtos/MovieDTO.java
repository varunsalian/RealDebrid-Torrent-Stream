package com.stream.torrent.dtos;

import java.util.List;
import java.util.Objects;

public class MovieDTO  {

    private Integer id;
    private String url;
    private String imdbCode;
    private String title;
    private String titleEnglish;
    private String titleLong;
    private String slug;
    private Integer year;
    private Float rating;
    private Integer runtime;
    private List<String> genres;
    private String summary;
    private String descriptionFull;
    private String synopsis;
    private String ytTrailerCode;
    private String language;
    private String mpaRating;
    private List<TorrentDTO> torrents;
    private String backgroundImage;
    private String backgroundImageOriginal;
    private String smallCoverImage;
    private String mediumCoverImage;
    private String largeCoverImage;
    private String state;
    private String dateUploaded;

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", imdbCode='" + imdbCode + '\'' +
                ", title='" + title + '\'' +
                ", titleEnglish='" + titleEnglish + '\'' +
                ", titleLong='" + titleLong + '\'' +
                ", slug='" + slug + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", runtime=" + runtime +
                ", generes=" + genres+
                ", summary='" + summary + '\'' +
                ", descriptionFull='" + descriptionFull + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", ytTrailerCode='" + ytTrailerCode + '\'' +
                ", language='" + language + '\'' +
                ", mpaRating='" + mpaRating + '\'' +
                ", torrent=" + torrents +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", backgroundImageOriginal='" + backgroundImageOriginal + '\'' +
                ", smallCoverImage='" + smallCoverImage + '\'' +
                ", mediumCoverImage='" + mediumCoverImage + '\'' +
                ", largeCoverImage='" + largeCoverImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(id, movieDTO.id) &&
                Objects.equals(url, movieDTO.url) &&
                Objects.equals(imdbCode, movieDTO.imdbCode) &&
                Objects.equals(title, movieDTO.title) &&
                Objects.equals(titleEnglish, movieDTO.titleEnglish) &&
                Objects.equals(titleLong, movieDTO.titleLong) &&
                Objects.equals(slug, movieDTO.slug) &&
                Objects.equals(year, movieDTO.year) &&
                Objects.equals(rating, movieDTO.rating) &&
                Objects.equals(runtime, movieDTO.runtime) &&
                Objects.equals(genres, movieDTO.genres) &&
                Objects.equals(summary, movieDTO.summary) &&
                Objects.equals(descriptionFull, movieDTO.descriptionFull) &&
                Objects.equals(synopsis, movieDTO.synopsis) &&
                Objects.equals(ytTrailerCode, movieDTO.ytTrailerCode) &&
                Objects.equals(language, movieDTO.language) &&
                Objects.equals(mpaRating, movieDTO.mpaRating) &&
                Objects.equals(torrents, movieDTO.torrents) &&
                Objects.equals(backgroundImage, movieDTO.backgroundImage) &&
                Objects.equals(backgroundImageOriginal, movieDTO.backgroundImageOriginal) &&
                Objects.equals(smallCoverImage, movieDTO.smallCoverImage) &&
                Objects.equals(mediumCoverImage, movieDTO.mediumCoverImage) &&
                Objects.equals(largeCoverImage, movieDTO.largeCoverImage) &&
                Objects.equals(state, movieDTO.state) &&
                Objects.equals(dateUploaded, movieDTO.dateUploaded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, imdbCode, title, titleEnglish, titleLong, slug, year, rating, runtime, genres, summary, descriptionFull, synopsis, ytTrailerCode, language, mpaRating, torrents, backgroundImage, backgroundImageOriginal, smallCoverImage, mediumCoverImage, largeCoverImage, state, dateUploaded);
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundImageOriginal() {
        return backgroundImageOriginal;
    }

    public void setBackgroundImageOriginal(String backgroundImageOriginal) {
        this.backgroundImageOriginal = backgroundImageOriginal;
    }

    public String getSmallCoverImage() {
        return smallCoverImage;
    }

    public void setSmallCoverImage(String smallCoverImage) {
        this.smallCoverImage = smallCoverImage;
    }

    public String getMediumCoverImage() {
        return mediumCoverImage;
    }

    public void setMediumCoverImage(String mediumCoverImage) {
        this.mediumCoverImage = mediumCoverImage;
    }

    public String getLargeCoverImage() {
        return largeCoverImage;
    }

    public void setLargeCoverImage(String largeCoverImage) {
        this.largeCoverImage = largeCoverImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImdbCode() {
        return imdbCode;
    }

    public void setImdbCode(String imdbCode) {
        this.imdbCode = imdbCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleLong() {
        return titleLong;
    }

    public void setTitleLong(String titleLong) {
        this.titleLong = titleLong;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescriptionFull() {
        return descriptionFull;
    }

    public void setDescriptionFull(String descriptionFull) {
        this.descriptionFull = descriptionFull;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getYtTrailerCode() {
        return ytTrailerCode;
    }

    public void setYtTrailerCode(String ytTrailerCode) {
        this.ytTrailerCode = ytTrailerCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMpaRating() {
        return mpaRating;
    }

    public void setMpaRating(String mpaRating) {
        this.mpaRating = mpaRating;
    }

    public List<TorrentDTO> getTorrents() {
        return torrents;
    }

    public void setTorrents(List<TorrentDTO> torrents) {
        this.torrents = torrents;
    }


}
