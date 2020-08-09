package com.stream.realdebrid.dtos;

import java.io.Serializable;

public class AuthenticationDTO implements Serializable {

    private String deviceCode;
    private String userCode;
    private Integer interval;
    private Integer expiresIn;
    private String verificationUrl;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public String toString() {
        return "com.varun.realdebrid.AuthenticationDTO{" +
                "deviceCode='" + deviceCode + '\'' +
                ", userCode='" + userCode + '\'' +
                ", interval=" + interval +
                ", expiresIn=" + expiresIn +
                ", verificationUrl='" + verificationUrl + '\'' +
                '}';
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getVerificationUrl() {
        return verificationUrl;
    }

    public void setVerificationUrl(String verificationUrl) {
        this.verificationUrl = verificationUrl;
    }

    public void setAll(AuthenticationDTO authenticationDTO){
        this.deviceCode = authenticationDTO.deviceCode;
        this.userCode = authenticationDTO.userCode;
        this.interval = authenticationDTO.interval;
        this.expiresIn = authenticationDTO.expiresIn;
        this.verificationUrl = authenticationDTO.verificationUrl;
    }

    public AuthenticationDTO(){

    }
}
