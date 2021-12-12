package com.example.spotifyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp.Data.Album;
import com.example.spotifyapp.Data.Track;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {
    ArrayList<Track> tracks=new ArrayList<>();
    Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClicked(Track track);
        void playButtonClicked(Track track);
        void stopButtonClicked(Track track);
        void addFavoriteClicked(Track track);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener =listener;
    }

    public AlbumAdapter(Context context, ArrayList<Track> tracks) {
        this.context= context;
        this.tracks=tracks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.track_item, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("##");
        String temp = tracks.get(position).getTrackNumber()+" "+
                tracks.get(position).getTrackName()+" - "+(TimeUnit.MILLISECONDS.toMinutes(tracks.get(position).getTrackDuration()))+
                ":"+String.format("%02d", (TimeUnit.MILLISECONDS.toSeconds(tracks.get(position).getTrackDuration())/60));
        holder.trackDetail.setText(temp);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView trackDetail;
        Button playButton, stopButton;
        ImageView addFavorite;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            trackDetail = itemView.findViewById(R.id.trackDetail);
            playButton = itemView.findViewById(R.id.playButton);
            stopButton = itemView.findViewById(R.id.stopButton);
            addFavorite = itemView.findViewById(R.id.addFavorite);

            itemView.setOnClickListener((v)->{
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.onItemClicked(tracks.get(position));
                    }
                }
            });

            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.playButtonClicked(tracks.get(position));
                        }
                    }
                }
            });

            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.stopButtonClicked(tracks.get(position));
                        }
                    }
                }
            });

            addFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.addFavoriteClicked(tracks.get(position));
                        }
                    }
                }
            });
        }


    }
}
