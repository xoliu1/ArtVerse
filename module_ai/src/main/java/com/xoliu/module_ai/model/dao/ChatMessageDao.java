package com.xoliu.module_ai.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.xoliu.module_ai.model.bean.ChatMessageEntity;

import java.util.List;

/**
 * Room DAO - 聊天消息数据访问接口
 */
@Dao
public interface ChatMessageDao {

    @Insert
    void insert(ChatMessageEntity message);

    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    List<ChatMessageEntity> getAllMessages();

    @Query("SELECT * FROM chat_messages WHERE poetName = :poetName ORDER BY timestamp ASC")
    List<ChatMessageEntity> getMessagesByPoet(String poetName);

    @Query("DELETE FROM chat_messages")
    void deleteAll();

    @Query("DELETE FROM chat_messages WHERE poetName = :poetName")
    void deleteByPoet(String poetName);

    @Query("SELECT COUNT(*) FROM chat_messages")
    int getMessageCount();

    @Query("SELECT COUNT(*) FROM chat_messages WHERE poetName = :poetName")
    int getMessageCountByPoet(String poetName);
}
