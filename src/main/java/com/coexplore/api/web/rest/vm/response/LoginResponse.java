package com.coexplore.api.web.rest.vm.response;

import java.util.Date;

public class LoginResponse {

    private String accessToken;
    private Date expiredIn;
    //    private String userName;
    //    private String email;
    private String authorities;

    public LoginResponse() {
        super();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(Date expiredIn) {
        this.expiredIn = expiredIn;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

}