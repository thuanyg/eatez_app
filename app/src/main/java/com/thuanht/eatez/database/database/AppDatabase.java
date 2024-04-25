package com.thuanht.eatez.database.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thuanht.eatez.database.dao.PostDAO;
import com.thuanht.eatez.model.Post;

@Database(entities = {Post.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "EATEZ_Database")
//                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract PostDAO postDAO();
//    public abstract DishDAO dishDAO();
//    public abstract RestaurantDAO restaurantDAO();
}
