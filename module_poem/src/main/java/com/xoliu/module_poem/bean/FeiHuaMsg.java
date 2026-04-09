package com.xoliu.module_poem.bean;

/**
 * 飞花令消息 Bean
 * type: 0=AI, 1=玩家, 2=系统消息
 */
public class FeiHuaMsg {
    public static final int TYPE_AI = 0;
    public static final int TYPE_PLAYER = 1;
    public static final int TYPE_SYSTEM = 2;

    private int type;
    private String content;
    private int round;

    public FeiHuaMsg(int type, String content, int round) {
        this.type = type;
        this.content = content;
        this.round = round;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
