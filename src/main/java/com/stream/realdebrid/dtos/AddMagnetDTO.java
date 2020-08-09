package com.stream.realdebrid.dtos;

import java.io.Serializable;

public class AddMagnetDTO implements Serializable {


    private String id;
    private String uri;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AddMagnetDTO{" +
                "id='" + id + '\'' +
                ", url='" + uri + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public AddMagnetDTO(){

    }
}
