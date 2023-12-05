package db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import db.bean.PoemCard;
import db.dao.PoemCardDao;

@Database(entities = {PoemCard.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PoemCardDao poemCardDao();

}
