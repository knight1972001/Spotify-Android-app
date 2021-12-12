package com.example.spotifyapp.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Album {
    private String albumName;
    private String imageUrl;
    private String releaseDate;
    private int totalTrack;
    private String albumId;
    private ArrayList<String> artistNames=new ArrayList<>();
    private ArrayList<Track> tracks=new ArrayList<>();
    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    private Bitmap bitmapImage=null;

    public Album() {
        this.albumName = "";
        this.imageUrl = "";
        this.releaseDate = "";
        this.totalTrack = 0;
        this.albumId = "";
        this.artistNames=new ArrayList<>();
    }

    public Album(String albumName, String imageUrl, String releaseDate, int totalTrack, String albumId, ArrayList<String> artistNames) {
        this.albumName = albumName;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.totalTrack = totalTrack;
        this.albumId = albumId;
        this.artistNames = artistNames;
    }

    public String getAlbumName() {
        return albumName;
    }

    public Bitmap getImageBitmap() {
        convertImagetoBitmap();
        while(!networkExecutorService.isTerminated()){

        }
        System.out.println("Fetching Finished");
        networkExecutorService=Executors.newFixedThreadPool(4);
        return this.bitmapImage;
    }

    public void convertImagetoBitmap(){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageUrl);
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    bitmapImage=image;
                } catch(IOException e) {
                    System.out.println(e);
                }finally {
                    networkExecutorService.shutdown();
                }
            }
        });
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getTotalTrack() {
        return totalTrack;
    }

    public String getAlbumId() {
        return albumId;
    }

    public ArrayList<String> getArtistNames() {
        return artistNames;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public Album(String albumName, String imageUrl, String releaseDate, int totalTrack, String albumId, ArrayList<String> artistNames, ArrayList<Track> tracks) {
        this.albumName = albumName;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.totalTrack = totalTrack;
        this.albumId = albumId;
        this.artistNames = artistNames;
        this.tracks = tracks;
    }
}
