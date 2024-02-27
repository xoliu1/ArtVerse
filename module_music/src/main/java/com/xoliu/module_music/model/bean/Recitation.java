package com.xoliu.module_music.model.bean;

public class Recitation {

    private String Name;

    private int bgImgId;

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

    public Recitation(String name, int bgImgId) {
        Name = name;
        this.bgImgId = bgImgId;
    }
}
