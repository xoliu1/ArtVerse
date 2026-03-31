package com.xoliu.module_profile.assistant;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Room Database - AI助手聊天数据库
 */
@Database(entities = {AssistantMessageEntity.class}, version = 1, exportSchema = false)
public abstract class AssistantDatabase extends RoomDatabase {

    public abstract AssistantMessageDao assistantMessageDao();

    private static volatile AssistantDatabase INSTANCE;

    public static AssistantDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AssistantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AssistantDatabase.class,
                            "assistant_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
