package com.xoliu.module_profile.assistant;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Room DAO - AI助手聊天消息数据访问接口
 */
@Dao
public interface AssistantMessageDao {

    @Insert
    void insert(AssistantMessageEntity message);

    @Query("SELECT * FROM assistant_messages ORDER BY timestamp ASC")
    List<AssistantMessageEntity> getAllMessages();

    @Query("DELETE FROM assistant_messages")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM assistant_messages")
    int getMessageCount();
}
