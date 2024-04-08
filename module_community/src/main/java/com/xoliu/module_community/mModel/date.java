package com.xoliu.module_community.mModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class date {
    @SerializedName("data")
    List<base> base;

    public List<com.xoliu.module_community.mModel.base> getBase() {
        return base;
    }

    public void setBase(List<com.xoliu.module_community.mModel.base> base) {
        this.base = base;
    }

    @Override
    public String toString() {
        return "date{" +
                "base=" + base +
                '}';
    }
}
