package com.xoliu.module_community.mModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class customer {
    @SerializedName("data")
    List<player> playerList;

    public List<player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public String toString() {
        return "customer{" +
                "playerList=" + playerList +
                '}';
    }
}
