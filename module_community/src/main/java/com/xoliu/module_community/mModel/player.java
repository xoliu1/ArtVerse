package com.xoliu.module_community.mModel;

import com.google.gson.annotations.SerializedName;

public class player {
    @SerializedName("Nickname")
    String PName;

    @SerializedName("Poem")
    String signer;

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

    @Override
    public String toString() {
        return "player{" +
                "PName='" + PName + '\'' +
                ", signer='" + signer + '\'' +
                '}';
    }
}
