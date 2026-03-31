package com.xoliu.module_profile.assistant;

/**
 * 助手聊天消息 Bean - 用于 UI 展示
 */
public class AssistantChatMsg {
    private String role;
    private String content;

    public AssistantChatMsg(String role, String content) {
        this.role = role;
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
