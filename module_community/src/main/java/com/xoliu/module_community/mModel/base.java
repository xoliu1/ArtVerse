package com.xoliu.module_community.mModel;

import com.google.gson.annotations.SerializedName;

public class base {
    @SerializedName("Nickname")
    String name;
    @SerializedName("Poem")
    String poem;

    public base(String name, String poem) {
        this.name = name;
        this.poem = poem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoem() {
        return poem;
    }

    public void setPoem(String poem) {
        this.poem = poem;
    }

    @Override
    public String toString() {
        return "base{" +
                "name='" + name + '\'' +
                ", poem='" + poem + '\'' +
                '}';
    }
}
