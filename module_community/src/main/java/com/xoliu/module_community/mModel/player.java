package com.xoliu.module_community.mModel;

import com.google.gson.annotations.SerializedName;

public class player {
    @SerializedName(value = "username", alternate = {"Nickname"})
    String PName;

    @SerializedName(value = "content", alternate = {"Poem"})
    String signer;

    @SerializedName("title")
    String title;

    @SerializedName("avatar_url")
    String avatarUrl;

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

    @Override
    public String toString() {
        return "player{" +
                "PName='" + PName + '\'' +
                ", signer='" + signer + '\'' +
                ", title='" + title + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
