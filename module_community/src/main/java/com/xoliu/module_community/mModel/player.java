package com.xoliu.module_community.mModel;

import com.google.gson.annotations.SerializedName;

public class player {
    @SerializedName("user_id")
    int userId;

    @SerializedName(value = "username", alternate = {"Nickname"})
    String PName;

    @SerializedName(value = "content", alternate = {"Poem"})
    String signer;

    @SerializedName("title")
    String title;

    @SerializedName("avatar_url")
    String avatarUrl;

    // 前端本地维护的关注状态（不来自后端JSON）
    boolean isFollowed = false;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    @Override
    public String toString() {
        return "player{" +
                "userId=" + userId +
                ", PName='" + PName + '\'' +
                ", signer='" + signer + '\'' +
                ", title='" + title + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", isFollowed=" + isFollowed +
                '}';
    }
}
