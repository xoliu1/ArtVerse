package com.xoliu.module_login.model;

import com.google.gson.annotations.SerializedName;

public class reDate {
    @SerializedName("Code")
    int code;
    @SerializedName("Message")
    String message;
    @SerializedName("user_id")
    int userId;
    @SerializedName("user_email")
    String userEmail;
    @SerializedName("username")
    String username;
    @SerializedName("avatar_url")
    String avatarUrl;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "reDate{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
