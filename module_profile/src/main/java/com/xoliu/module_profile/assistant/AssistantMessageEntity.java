package com.xoliu.module_profile.assistant;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room 实体类 - AI助手聊天消息持久化存储
 */
@Entity(tableName = "assistant_messages")
public class AssistantMessageEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String role;      // "user" 或 "assistant"
    private String content;   // 消息内容
    private long timestamp;   // 发送时间戳

    public AssistantMessageEntity(String role, String content, long timestamp) {
        this.role = role;
        this.content = content;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
