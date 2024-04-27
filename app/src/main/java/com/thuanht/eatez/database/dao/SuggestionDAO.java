package com.thuanht.eatez.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thuanht.eatez.database.entity.Suggestion;

import java.util.List;

@Dao
public interface SuggestionDAO {
    @Query("SELECT * FROM suggestion")
    List<Suggestion> selectAll();
    @Insert
    long insert(Suggestion suggestion);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Suggestion> suggestions);
}
