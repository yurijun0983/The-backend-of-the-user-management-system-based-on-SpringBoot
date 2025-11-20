package com.example.usermanage.model;

import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String username;
    
    @TableField("avatarUrl")
    private String avatarUrl;
    
    private Integer gender;
    
    private String password;
    
    private String email;
    
    private Integer isVaild;
    
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;
    
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    
    private Integer userStatus;

    private Integer userRole;
    @TableLogic
    @TableField("isDelete")
    private Integer isDelete;

    public User() {}

    public User(Integer id, String username, String avatarUrl, Integer gender, 
                String password, String email, Integer isVaild, Date createdTime, 
                Date updateTime, Integer userStatus, Integer isDelete) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.isVaild = isVaild;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.userStatus = userStatus;
        this.isDelete = isDelete;
    }
    
    // Constructor without id (for new users)
    public User(String username, String avatarUrl, Integer gender, 
                String password, String email, Integer isVaild, 
                Integer userStatus) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.isVaild = isVaild;
        this.userStatus = userStatus;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Integer getIsVaild() {
        return isVaild;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public Integer getIsDelete() {
        return isDelete;
    }
    public Integer getUserRole() {
        return userRole;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsVaild(Integer isVaild) {
        this.isVaild = isVaild;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    // toString method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isVaild=" + isVaild +
                ", createdTime=" + createdTime +
                ", updateTime=" + updateTime +
                ", userStatus=" + userStatus +
                ", isDelete=" + isDelete +
                '}';
    }
}