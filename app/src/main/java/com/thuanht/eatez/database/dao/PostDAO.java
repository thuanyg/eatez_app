package com.thuanht.eatez.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thuanht.eatez.model.Post;

import java.util.List;

@Dao
public interface PostDAO {
    @Query("SELECT * FROM Post ORDER BY date DESC")
    List<Post> selectAll();
    @Query("SELECT * FROM Post WHERE post_id IN (:postid)")
    List<Post> getPostById(int postid);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Post post);
    @Delete
    void delete(Post post);
}
