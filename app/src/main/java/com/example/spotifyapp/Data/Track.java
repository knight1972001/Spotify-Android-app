package com.example.spotifyapp.Data;

public class Track {
    private String id;
    private int trackNumber;
    private String trackName;
    private long trackDuration;
    private String trackPreviewUrl;

    public Track(String id, int trackNumber, String trackName, long trackDuration, String trackPreviewUrl) {
        this.id = id;
        this.trackNumber = trackNumber;
        this.trackName = trackName;
        this.trackDuration = trackDuration;
        this.trackPreviewUrl = trackPreviewUrl;
    }

    public String getId() {
        return id;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public String getTrackName() {
        return trackName;
    }

    public long getTrackDuration() {
        return trackDuration;
    }

    public String getTrackPreviewUrl() {
        return trackPreviewUrl;
    }
}
