package com.stream.realdebrid.dtos;

import java.io.Serializable;

public class ClientDTO implements Serializable {

    private String clientID;
    private String clientSecret;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setAll(ClientDTO clientDTO){
        this.clientID = clientDTO.getClientID();
        this.clientSecret = clientDTO.getClientSecret();
    }

    @Override
    public String toString() {
        return "AccessTokenDTO{" +
                "clientID='" + clientID + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
