package com.example.spotifyapp.Data;

public class Token {
    private String token;
    private long expireIn;
    private long timeGetToken;

    public Token() {
        token="";
        expireIn=0;
        timeGetToken=0;
    }

    public Token(String token, long expireIn, long timeGetToken) {
        this.token = token;
        this.expireIn = expireIn;
        this.timeGetToken = timeGetToken;
    }

    public boolean isEmpty(){
        return token=="";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }

    public long getTimeGetToken() {
        return timeGetToken;
    }

    public void setTimeGetToken(long timeGetToken) {
        this.timeGetToken = timeGetToken;
    }
}
