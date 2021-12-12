package com.example.spotifyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.MyViewHolder> {
    ArrayList<String> artists=new ArrayList<>();
    Context context;

    public ArtistAdapter(Context context, ArrayList<String> artists){
        this.context=context;
        this.artists=artists;
    }

    @NonNull
    @Override
    public ArtistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.artist_item, parent, false);
        return new ArtistAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.MyViewHolder holder, int position) {
        holder.artistNameView.setText(artists.get(position));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView artistNameView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            artistNameView = itemView.findViewById(R.id.artistName);
        }
    }
}
