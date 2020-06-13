package com.stream.common;

import com.stream.realdebrid.dtos.AuthenticationDTO;
import com.stream.realdebrid.dtos.ClientDTO;
import com.stream.realdebrid.dtos.TokenDTO;

import java.util.Objects;

public class CredentialsDTO {

    private AuthenticationDTO authenticationDTO;
    private ClientDTO clientDTO;
    private TokenDTO tokenDTO;
    private static CredentialsDTO credentialsDTO;

    public AuthenticationDTO getAuthenticationDTO() {
        return authenticationDTO;
    }

    public void setAuthenticationDTO(AuthenticationDTO authenticationDTO) {
        this.authenticationDTO = authenticationDTO;
    }

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    public TokenDTO getTokenDTO() {
        return tokenDTO;
    }

    public void setTokenDTO(TokenDTO tokenDTO) {
        this.tokenDTO = tokenDTO;
    }

    private CredentialsDTO(){

    }

    public static CredentialsDTO getInstance(){
        if (credentialsDTO==null)
            credentialsDTO =  new CredentialsDTO();
        return credentialsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialsDTO that = (CredentialsDTO) o;
        return Objects.equals(authenticationDTO, that.authenticationDTO) &&
                Objects.equals(clientDTO, that.clientDTO) &&
                Objects.equals(tokenDTO, that.tokenDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authenticationDTO, clientDTO, tokenDTO);
    }

    @Override
    public String toString() {
        return "CredentialsDTO{" +
                "authenticationDTO=" + authenticationDTO +
                ", clientDTO=" + clientDTO +
                ", tokenDTO=" + tokenDTO +
                '}';
    }
}
