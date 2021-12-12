package com.example.spotifyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifyapp.Data.Album;
import com.example.spotifyapp.Data.Token;
import com.example.spotifyapp.Data.Track;
import com.example.spotifyapp.Service.JsonService;
import com.example.spotifyapp.Service.MusicDataService;
import com.example.spotifyapp.db.AppDatabase;
import com.example.spotifyapp.db.TrackFavourite;

import java.io.IOException;
import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {
    private String albumId="";
    private Token tokenObj = new Token();
    private JsonService jsonService = new JsonService();
    private MusicDataService mService = new MusicDataService();
    private Album album = new Album();
    private RecyclerView mRecyclerView;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private String oldTrackName="";
    private TextView albumName, releaseDate, trackCount;
    private ImageView imageAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        if(this.getIntent().getExtras()!=null){
            albumId=this.getIntent().getExtras().getString("albumId");
        }
        getAlbumById(albumId);
        setAlbumAttribute(album);

        mRecyclerView = findViewById(R.id.trackRecycleView);
        mRecyclerView.setHasFixedSize(true);
        AlbumAdapter albumAdapter = new AlbumAdapter(this, album.getTracks());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(albumAdapter);

        albumAdapter.setOnItemClickListener(new AlbumAdapter.OnItemClickListener(){
            @Override
            public void onItemClicked(Track track) {

            }

            @Override
            public void playButtonClicked(Track track) {
                if(!mediaPlayer.isPlaying()){
                    prepareMedia(track);
                    mediaPlayer.start();
                }
                Toast.makeText(AlbumActivity.this, track.getTrackName()+" is playing", Toast.LENGTH_LONG).show();
                oldTrackName = track.getTrackName();
            }

            @Override
            public void stopButtonClicked(Track track) {
                if(mediaPlayer.isPlaying()){
                    Toast.makeText(AlbumActivity.this, oldTrackName+" is stopped", Toast.LENGTH_LONG).show();
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();
                }
            }

            @Override
            public void addFavoriteClicked(Track track) {
                AppDatabase db = AppDatabase.getDbInstance(AlbumActivity.this.getApplicationContext());
                TrackFavourite trackFavourite = new TrackFavourite();
                trackFavourite.trackId = track.getId();
                db.trackDao().insertTrack(trackFavourite);
                Toast.makeText(AlbumActivity.this, track.getTrackName()+" added to playlist", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setAlbumAttribute(Album album){
        albumName = findViewById(R.id.albumName);
        releaseDate = findViewById(R.id.releaseDate);
        trackCount = findViewById(R.id.trackCount);
        imageAlbum = findViewById(R.id.albumImage);
        albumName.setText(album.getAlbumName());
        releaseDate.setText(album.getReleaseDate());
        trackCount.setText(String.valueOf(album.getTotalTrack()));
        imageAlbum.setImageBitmap(album.getImageBitmap());
    }

    public void getAlbumById(String albumId){
        checkExpireToken();
        mService.getAlbumById(albumId, tokenObj.getToken());
        album = jsonService.extractAlbumById(mService.getResponseString());
    }


    public void getNewToken(){
        mService.getToken();
        tokenObj=jsonService.extractToken(mService.getResponseString());
    }

    public void checkExpireToken(){
        long now = System.currentTimeMillis();
        if ((now - tokenObj.getTimeGetToken()) > tokenObj.getExpireIn() || tokenObj.isEmpty()) {
            getNewToken();
        }
    }

    public void prepareMedia(Track track){
        try{
            mediaPlayer.setDataSource(track.getTrackPreviewUrl());
            mediaPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}