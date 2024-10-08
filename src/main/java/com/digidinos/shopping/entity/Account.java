package com.digidinos.shopping.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ACCOUNTS")
public class Account implements Serializable {


    private static final long serialVersionUID = -2054386655979281969L;


    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    public static final String ROLE_USER = "USER";

    @Id
    @Column(name = "USER_NAME", length = 20, nullable = false)
    private String userName;


    @Column(name = "ENCRYTED_PASSWORD", length = 128, nullable = false)
    private String encrytedPassword;


    @Column(name = "ACTIVE", length = 1, nullable = false)
    private boolean active;


    @Column(name = "USER_ROLE", length = 20, nullable = false)
    private String userRole;
    
    @Column(name = "GMAIL", length = 128, nullable = false)
    private String gmail;

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
    
    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getEncrytedPassword() {
        return encrytedPassword;
    }


    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    public String getUserRole() {
        return userRole;
    }


    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }


    @Override
    public String toString() {
        return "[" + this.userName + "," + this.encrytedPassword + "," + this.userRole + "]";
    }


}