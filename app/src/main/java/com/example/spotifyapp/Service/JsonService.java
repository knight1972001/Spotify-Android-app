package com.example.spotifyapp.Service;

import com.example.spotifyapp.Data.Album;
import com.example.spotifyapp.Data.Token;
import com.example.spotifyapp.Data.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class JsonService {
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Track> tracks = new ArrayList<>();
    private MusicDataService service = new MusicDataService();
    private Token tokenObj;

    public Token extractToken(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            String token = jsonObject.getString("access_token");
            long expires_in = jsonObject.getLong("expires_in");
            long now = System.currentTimeMillis();
            tokenObj= new Token(token,expires_in,now);
            return tokenObj;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Album> extractNewRelease(String json) {
        try {
            albums.clear();
            JSONObject jsonAlbumObject = new JSONObject(json);
            JSONObject jsonAlbum = jsonAlbumObject.getJSONObject("albums");
            JSONArray albumArray = jsonAlbum.getJSONArray("items");
            for (int i = 0; i < albumArray.length(); i++) {
                String albumName = albumArray.getJSONObject(i).getString("name");
                String imageUrl = albumArray.getJSONObject(i).getJSONArray("images").getJSONObject(0).getString("url");
                String releaseDate = albumArray.getJSONObject(i).getString("release_date");
                int totalTrack = albumArray.getJSONObject(i).getInt("total_tracks");
                JSONArray artistArray = albumArray.getJSONObject(i).getJSONArray("artists");
                ArrayList<String> artists = new ArrayList<>();
                for (int y = 0; y < artistArray.length(); y++) {
                    String artistName = artistArray.getJSONObject(y).getString("name");
                    artists.add(artistName);
                }
                String albumId = albumArray.getJSONObject(i).getString("id");
                Album album = new Album(albumName, imageUrl, releaseDate, totalTrack, albumId, artists);
                albums.add(album);
            }
            return albums;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Album extractAlbumById(String json) {
        try {
            tracks.clear();
            JSONObject albumById = new JSONObject(json);
            String albumName = albumById.getString("name");
            String imageUrl = albumById.getJSONArray("images").getJSONObject(0).getString("url");
            String releaseDate = albumById.getString("release_date");
            String albumId = albumById.getString("id");
            int totalTrack = albumById.getInt("total_tracks");
            JSONArray artistArray = albumById.getJSONArray("artists");
            ArrayList<String> artists = new ArrayList<>();
            for (int i = 0; i < artistArray.length(); i++) {
                String artistName = artistArray.getJSONObject(i).getString("name");
                artists.add(artistName);
            }
            JSONArray tracksArray = albumById.getJSONObject("tracks").getJSONArray("items");

            for (int i = 0; i < tracksArray.length(); i++) {
                String trackId = tracksArray.getJSONObject(i).getString("id");
                int trackNumber = tracksArray.getJSONObject(i).getInt("track_number");
                String trackName = tracksArray.getJSONObject(i).getString("name");
                long duringMilisecond = tracksArray.getJSONObject(i).getLong("duration_ms");
                String previewUrl = tracksArray.getJSONObject(i).getString("preview_url");
                Track track = new Track(trackId, trackNumber, trackName, duringMilisecond, previewUrl);
                tracks.add(track);
            }
            Album album = new Album(albumName, imageUrl, releaseDate, totalTrack, albumId, artists, tracks);
            return album;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Track> extractFavourite(String json){
        ArrayList<Track> favouriteTracks = new ArrayList<>();
        try{

            JSONObject favourite = new JSONObject(json);
            JSONArray tracksArray = favourite.getJSONArray("tracks");

            for (int i = 0; i < tracksArray.length(); i++) {
                String trackId = tracksArray.getJSONObject(i).getString("id");
                int trackNumber = tracksArray.getJSONObject(i).getInt("track_number");
                String trackName = tracksArray.getJSONObject(i).getString("name");
                long duringMilisecond = tracksArray.getJSONObject(i).getLong("duration_ms");
                String previewUrl = tracksArray.getJSONObject(i).getString("preview_url");
                Track track = new Track(trackId, trackNumber, trackName, duringMilisecond, previewUrl);
                favouriteTracks.add(track);
            }
            return favouriteTracks;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
