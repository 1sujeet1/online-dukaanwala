package com.dukaanwala.dto.requestdto;

import javax.validation.constraints.NotNull;

/**
 * LoginRequestDto
 */
public class LoginRequestDto {

    @NotNull(message = "email can't be null")
    private String email;
    
    private String password;

    @NotNull(message = "device token can't be null")
    private String deviceToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
    
}