package com.example.spotifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.spotifyapp.Data.Album;
import com.example.spotifyapp.Data.Token;
import com.example.spotifyapp.Service.JsonService;
import com.example.spotifyapp.Service.MusicDataService;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Album> albums = new ArrayList<>();
    private JsonService jsonService = new JsonService();
    private MusicDataService mService = new MusicDataService();
    private Token tokenObj = new Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getNewRelease();
        mRecyclerView = findViewById(R.id.recycleView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        MainAdapter mainAdapter = new MainAdapter(this, albums, new MainAdapter.OnItemClickListener(){
            @Override
            public void onItemClicked(Album album) {
                navigateToAlbum(album);
            }
        });
        mRecyclerView.setAdapter(mainAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()!=0){
            navigateToFavorite();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void navigateToFavorite(){
        Intent myIntent = new Intent(this, FavoriteListActivity.class);
        Bundle bundle = new Bundle();
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }

    public void navigateToAlbum(Album album){
        Intent myIntent = new Intent(this, AlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("albumId", album.getAlbumId());
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }

    public void getNewRelease(){
        checkExpireToken();
        mService.getNewRelease(tokenObj.getToken());
        albums = jsonService.extractNewRelease(mService.getResponseString());
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