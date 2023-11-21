package com.xoliu.module_poem.viewpager;

import com.google.gson.annotations.SerializedName;

public class Poem {
    @SerializedName("id")
    private Integer id;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("hitokoto")
    private String hitokoto;
    @SerializedName("type")
    private String type;
    @SerializedName("from")
    private String from;
    @SerializedName("from_who")
    private String fromWho;
    @SerializedName("creator")
    private String creator;
    @SerializedName("creator_uid")
    private Integer creatorUid;
    @SerializedName("reviewer")
    private Integer reviewer;
    @SerializedName("commit_from")
    private String commitFrom;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("length")
    private Integer length;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(Integer creatorUid) {
        this.creatorUid = creatorUid;
    }

    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    public String getCommitFrom() {
        return commitFrom;
    }

    public void setCommitFrom(String commitFrom) {
        this.commitFrom = commitFrom;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
