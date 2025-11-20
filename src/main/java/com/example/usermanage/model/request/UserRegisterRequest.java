package com.example.usermanage.model.request;

import java.io.Serializable;

public class UserRegisterRequest implements Serializable {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    
    public String getUserAccount() {
        return userAccount;
    }
    
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    
    public String getUserPassword() {
        return userPassword;
    }
    
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    
    public String getCheckPassword() {
        return checkPassword;
    }
    
    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}