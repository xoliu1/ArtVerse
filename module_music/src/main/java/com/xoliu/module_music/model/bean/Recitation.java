package com.xoliu.module_music.model.bean;

public class Recitation {

    private String Name;

    private int bgImgId;

    private int audioResId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getBgImgId() {
        return bgImgId;
    }

    public void setBgImgId(int bgImgId) {
        this.bgImgId = bgImgId;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public void setAudioResId(int audioResId) {
        this.audioResId = audioResId;
    }

    public Recitation(String name, int bgImgId) {
        Name = name;
        this.bgImgId = bgImgId;
        this.audioResId = 0;
    }

    public Recitation(String name, int bgImgId, int audioResId) {
        Name = name;
        this.bgImgId = bgImgId;
        this.audioResId = audioResId;
    }
}
