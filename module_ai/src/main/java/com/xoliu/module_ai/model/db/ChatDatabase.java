package com.xoliu.module_ai.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.xoliu.module_ai.model.bean.ChatMessageEntity;
import com.xoliu.module_ai.model.dao.ChatMessageDao;

/**
 * Room Database - 聊天数据库
 */
@Database(entities = {ChatMessageEntity.class}, version = 2, exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {

    public abstract ChatMessageDao chatMessageDao();

    private static volatile ChatDatabase INSTANCE;

    public static ChatDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ChatDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ChatDatabase.class,
                            "chat_database"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
