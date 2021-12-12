package com.example.spotifyapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrackDao {
    @Query("SELECT * FROM TrackFavourite")
    List<TrackFavourite> getAllTrackIds();

    @Insert
    void insertTrack(TrackFavourite... trackFavourites);

    @Delete
    void delete(TrackFavourite trackFavourite);
}
