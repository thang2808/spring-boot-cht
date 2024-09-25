package com.digidinos.shopping.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AccountForm {

    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username must be less than 20 characters")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(max = 128, message = "Password must be less than 128 characters")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword; 

    private boolean active = true; 

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
