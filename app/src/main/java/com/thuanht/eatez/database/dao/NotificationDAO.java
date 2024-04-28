package com.thuanht.eatez.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.thuanht.eatez.database.entity.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {
    @Query("SELECT * FROM Notification ORDER BY date DESC")
    List<Notification> selectAll();
    @Insert
    long insert(Notification notification);
    @Delete
    void delete(Notification notification);

    @Query("DELETE FROM Notification")
    void deleteAll();
}
