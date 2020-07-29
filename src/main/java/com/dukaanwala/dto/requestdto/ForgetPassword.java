package com.dukaanwala.dto.requestdto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * ForgetPassword
 */
public class ForgetPassword {

    @NotNull
    @Email
    private String email;
    @NotNull
    private String code;
    @NotNull
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    

    
}