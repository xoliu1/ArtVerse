package com.xoliu.module_music.model.bean;

public class Song {

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Song(String name, String from) {
        Name = name;
        From = from;
    }

    private String From;

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }
}
