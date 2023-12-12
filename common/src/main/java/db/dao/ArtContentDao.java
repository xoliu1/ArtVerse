package db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import db.bean.ArtContent;

/***
 * 存艺术画廊的Dao
 * @author xoliu
 * @create 23-12-12
 **/

@Dao
public interface ArtContentDao {

    @Insert
    void insert(ArtContent artContent);

    @Update
    void update(ArtContent artContent);

    @Delete
    void delete(ArtContent artContent);

    @Query("SELECT * FROM artcontent")
    List<ArtContent> getAllArtContents();

    @Query("SELECT * FROM artcontent WHERE name = :name")
    List<ArtContent> getArtContentByName(String name);

    @Query("SELECT COUNT(*) FROM artcontent WHERE name = :name")
    int checkNameExists(String name);

    // 删除某名字对应的所有画
    @Query("DELETE FROM artcontent WHERE name = :name")
    void deletePaintingsByArtistName(String name);
}
