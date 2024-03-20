package db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import db.bean.ArtContent;
import db.bean.PoemCard;
import db.dao.ArtContentDao;
import db.dao.PoemCardDao;

@Database(entities = {PoemCard.class, ArtContent.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PoemCardDao poemCardDao();
    public abstract ArtContentDao artContentDao();

}
