package com.example.spotifyapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp.Data.Album;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    ArrayList<Album> albums=new ArrayList<>();
    Context context;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerView mRecyclerView;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClicked(Album album);
    }


    public MainAdapter(Context ct, ArrayList<Album> albums, OnItemClickListener onItemClickListener) {
        this.context = ct;
        this.albums = albums;
        this.listener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, releaseDate, trackCount;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            trackCount = itemView.findViewById(R.id.trackCount);
            image = itemView.findViewById(R.id.imageView);
            mRecyclerView = itemView.findViewById(R.id.recycleViewArtist);
        }
    }

    @NonNull
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MyViewHolder holder, int position) {
        holder.itemName.setText(albums.get(position).getAlbumName());
        holder.releaseDate.setText(albums.get(position).getReleaseDate());
        holder.trackCount.setText(String.valueOf(albums.get(position).getTotalTrack()));
        Bitmap bitmap=albums.get(position).getImageBitmap();
        holder.image.setImageBitmap(bitmap);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        ArtistAdapter artistAdapter = new ArtistAdapter(context, albums.get(position).getArtistNames());
        mRecyclerView.setAdapter(artistAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClicked(albums.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
