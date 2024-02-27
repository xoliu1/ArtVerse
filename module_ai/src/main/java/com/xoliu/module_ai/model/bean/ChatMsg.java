package com.xoliu.module_ai.model.bean;

public class ChatMsg {
    private String role;

    private String content;

    public ChatMsg(int times, String content) {
        if (times % 2 == 1){
            this.role = "user";
        }else{
            this.role = "assistant";
        }

        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
