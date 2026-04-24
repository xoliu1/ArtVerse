package com.xoliu.module_music.model.bean;

public class Song {

    private String Name;
    private String From;
    private int audioResId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public void setAudioResId(int audioResId) {
        this.audioResId = audioResId;
    }

    public Song(String name, String from) {
        Name = name;
        From = from;
        this.audioResId = 0;
    }

    public Song(String name, String from, int audioResId) {
        Name = name;
        From = from;
        this.audioResId = audioResId;
    }
}
