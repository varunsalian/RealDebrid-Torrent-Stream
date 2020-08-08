package com.stream.fetcher;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SourceDTO {
 private String sourceName;
 private String baseUrl;
 private Boolean table;
 private Integer tableIndex;
 private List<String> tableContent;
 private List<String> additionalContent;
 private Map<String, String> additionalContentRelation;
 private Map<String, Map<String, String>> additionalContentReference;
 private Map<String, Map<String, String>> headerSpecialCase;
 private Map<String, Map<String, String>> contentSpecialCase;
 private Map<String, String> nextPage;


    public Integer getTableIndex() {
        return tableIndex;
    }

    @Override
    public String toString() {
        return "com.torrent.fetcher.TorrentSourceDTO{" +
                "sourceName='" + sourceName + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", table=" + table +
                ", tableIndex=" + tableIndex +
                ", tableContent=" + tableContent +
                ", additionalContent=" + additionalContent +
                ", additionalContentRelation=" + additionalContentRelation +
                ", additionalContentReference=" + additionalContentReference +
                ", headerSpecialCase=" + headerSpecialCase +
                ", contentSpecialCase=" + contentSpecialCase +
                ", nextPage=" + nextPage +
                '}';
    }

    public void setTableIndex(Integer tableIndex) {
        this.tableIndex = tableIndex;
    }

    public Boolean getTable() {
        return table;
    }

    public void setTable(Boolean table) {
        this.table = table;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public List<String> getTableContent() {
        return tableContent;
    }

    public void setTableContent(List<String> tableContent) {
        this.tableContent = tableContent;
    }

    public List<String> getAdditionalContent() {
        return additionalContent;
    }

    public void setAdditionalContent(List<String> additionalContent) {
        this.additionalContent = additionalContent;
    }

    public Map<String, String> getAdditionalContentRelation() {
        return additionalContentRelation;
    }

    public void setAdditionalContentRelation(Map<String, String> additionalContentRelation) {
        this.additionalContentRelation = additionalContentRelation;
    }

    public Map<String, Map<String, String>> getAdditionalContentReference() {
        return additionalContentReference;
    }

    public void setAdditionalContentReference(Map<String, Map<String, String>> additionalContentReference) {
        this.additionalContentReference = additionalContentReference;
    }

    public Map<String, Map<String, String>> getHeaderSpecialCase() {
        return headerSpecialCase;
    }

    public void setHeaderSpecialCase(Map<String, Map<String, String>> headerSpecialCase) {
        this.headerSpecialCase = headerSpecialCase;
    }

    public Map<String, Map<String, String>> getContentSpecialCase() {
        return contentSpecialCase;
    }

    public void setContentSpecialCase(Map<String, Map<String, String>> contentSpecialCase) {
        this.contentSpecialCase = contentSpecialCase;
    }

    public Map<String, String> getNextPage() {
        return nextPage;
    }

    public void setNextPage(Map<String, String> nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }
        SourceDTO dummyDTO = (SourceDTO) object;
        return table == dummyDTO.table &&
                Objects.equals(sourceName, dummyDTO.sourceName) &&
                Objects.equals(baseUrl, dummyDTO.baseUrl) &&
                Objects.equals(tableContent, dummyDTO.tableContent) &&
                Objects.equals(additionalContent, dummyDTO.additionalContent) &&
                Objects.equals(additionalContentRelation, dummyDTO.additionalContentRelation) &&
                Objects.equals(additionalContentReference, dummyDTO.additionalContentReference) &&
                Objects.equals(headerSpecialCase, dummyDTO.headerSpecialCase) &&
                Objects.equals(contentSpecialCase, dummyDTO.contentSpecialCase) &&
                Objects.equals(nextPage, dummyDTO.nextPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceName, baseUrl, table, tableContent, additionalContent, additionalContentRelation, additionalContentReference, headerSpecialCase, contentSpecialCase, nextPage);
    }

}