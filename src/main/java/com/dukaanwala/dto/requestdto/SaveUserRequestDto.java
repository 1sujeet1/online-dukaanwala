package com.dukaanwala.dto.requestdto;


import javax.validation.constraints.NotNull;

import com.dukaanwala.enums.Role;

/**
 * SaveUserRequestDto
 */
public class SaveUserRequestDto {

    @NotNull(message = "email should be not null")
    private String email;

    
    private String password;    
    
    @NotNull(message = "firstName can't be null")
    private String firstName;

    private String lastName;

    private String photoUrl;

    @NotNull(message = "device token can't be null")
    private String deviceToken;
    
    private Role role;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    

}
