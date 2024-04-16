package db.dao;


import android.media.Image;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import db.bean.PoemCard;

/***
 * 诗歌的卡片Dao类
 * @return
 * @author xoliu
 * @create 23-12-6
 **/

@Dao
public interface PoemCardDao {
    @Insert
    void insert(PoemCard poemCard);

    @Update
    void update(PoemCard poemCard);

    @Delete
    void delete(PoemCard poemCard);

    @Query("DELETE FROM PoemCard WHERE poemContext = :poemContext")
    int deletePoemCardByContext(String poemContext);

    @Query("SELECT * FROM PoemCard")
    List<PoemCard> getAllPoemCards();

    @Query("SELECT * FROM PoemCard WHERE id = :id")
    PoemCard getPoemCardById(int id);
}

