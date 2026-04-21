package com.xoliu.module_profile;

/**
 * 粉丝/关注用户的数据模型
 */
public class FanItem {
    private int userId;
    private String username;
    private String avatarUrl;
    private boolean isFollowed; // 当前登录用户是否已关注此人

    public FanItem(int userId, String username, String avatarUrl, boolean isFollowed) {
        this.userId = userId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.isFollowed = isFollowed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }
}
