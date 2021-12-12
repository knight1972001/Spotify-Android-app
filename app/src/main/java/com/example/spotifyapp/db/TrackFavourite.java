package com.example.spotifyapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TrackFavourite {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "track_id")
    public String trackId;
}
