package com.xoliu.module_poem.comment;

/***
 * 评论的Bean类
 * @author xoliu
 * @create 23-11-21
 **/

public class commentItem {
    public String userIcon;
    String userName;
    String commentTime;
    String commentContent;

    public commentItem(String userIcon, String userName, String commentTime, String commentContent) {
        this.userIcon = userIcon;
        this.userName = userName;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
