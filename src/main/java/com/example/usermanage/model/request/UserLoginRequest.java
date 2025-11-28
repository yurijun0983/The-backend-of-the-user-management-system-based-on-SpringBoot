package com.example.usermanage.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginRequest {



    private String userAccount;
    private String userPassword;
    
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
}