package com.example.spotifyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifyapp.Data.Token;
import com.example.spotifyapp.Data.Track;
import com.example.spotifyapp.Service.JsonService;
import com.example.spotifyapp.Service.MusicDataService;
import com.example.spotifyapp.db.AppDatabase;
import com.example.spotifyapp.db.TrackFavourite;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {
    private ArrayList<String> favoriteString=new ArrayList<>();
    private Token tokenObj = new Token();
    private JsonService jsonService = new JsonService();
    private MusicDataService mService = new MusicDataService();
    private ArrayList<Track> tracks=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private String oldTrackName="";
    List<TrackFavourite> trackFavourites=new ArrayList<>();
    FavouriteAdapter favouriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        initView();


    }


    void initView(){
        loadTrackFavourite();
        TextView status=findViewById(R.id.status);
        if(favoriteString.size()>0){
            status.setText("");
            String params=getParamsFavourite(favoriteString);
            getFavourite(params);
            mRecyclerView = findViewById(R.id.favoriteListRecycleView);
            mRecyclerView.setHasFixedSize(true);
            favouriteAdapter = new FavouriteAdapter(this, tracks);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(favouriteAdapter);
            favouriteAdapter.setOnItemClickListener(new FavouriteAdapter.OnItemClickListener(){
                @Override
                public void onItemClicked(Track track) {

                }

                @Override
                public void playButtonClicked(Track track) {
                    if(!mediaPlayer.isPlaying()){
                        prepareMedia(track);
                        mediaPlayer.start();
                    }
                    Toast.makeText(FavoriteListActivity.this, track.getTrackName()+" is playing", Toast.LENGTH_LONG).show();
                    oldTrackName = track.getTrackName();
                }

                @Override
                public void stopButtonClicked(Track track) {
                    if(mediaPlayer.isPlaying()){
                        Toast.makeText(FavoriteListActivity.this, oldTrackName+" is stopped", Toast.LENGTH_LONG).show();
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                }

                @Override
                public void addFavoriteClicked(Track track) {
                    AppDatabase db = AppDatabase.getDbInstance(FavoriteListActivity.this.getApplicationContext());
                    int index = -1;
                    for(int i=0;i<trackFavourites.size();i++){
                        if(track.getId().contains(trackFavourites.get(i).trackId)){
                            index = i;
                            break;
                        }
                    }
                    db.trackDao().delete(trackFavourites.get(index));
                    Toast.makeText(FavoriteListActivity.this, track.getTrackName()+" Deleted from playlist", Toast.LENGTH_LONG).show();
                    initView();
                }
            });
        }else{
            status.setText("Empty List");
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

    private String getParamsFavourite(ArrayList<String> arrayList){
        String params="";
        for(int i=0;i<arrayList.size();i++){
            params+=arrayList.get(i)+",";
        }
        params=params.substring(0, params.length()-1);
        return params;
    }

    private void loadTrackFavourite(){
        trackFavourites.clear();
        favoriteString.clear();
        AppDatabase db = AppDatabase.getDbInstance(FavoriteListActivity.this.getApplicationContext());
        trackFavourites = db.trackDao().getAllTrackIds();
        for(int i=0;i<trackFavourites.size();i++){
            favoriteString.add(trackFavourites.get(i).trackId);
        }
    }

    public void getFavourite(String param){
        checkExpireToken();
        mService.getFavourite(param, tokenObj.getToken());
        tracks = jsonService.extractFavourite(mService.getResponseString());
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
}