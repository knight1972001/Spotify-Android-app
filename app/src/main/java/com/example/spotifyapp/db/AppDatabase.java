package com.example.spotifyapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TrackFavourite.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TrackDao trackDao();

    private static AppDatabase INSTANCE;
    public static AppDatabase getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_NAME").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
