package com.example.spotifyapp.Service;

import android.os.Handler;
import android.os.Looper;

import com.example.spotifyapp.Data.Token;
import com.example.spotifyapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MusicDataService{
    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    private String responseString="";

    public String getResponseString(){
        return responseString;
    }

    public void getNewRelease(String token){
        connectNewRelease(token);
        while(!networkExecutorService.isTerminated()){

        }
        System.out.println("Fetching Finished");
        networkExecutorService=Executors.newFixedThreadPool(4);
    }

    public void getToken(){
        connectToken();
        while(!networkExecutorService.isTerminated()){

        }
        System.out.println("Fetching Finished");
        networkExecutorService=Executors.newFixedThreadPool(4);
    }

    public void getAlbumById(String albumId, String token){
        connectGetAlbumById(albumId, token);
        while(!networkExecutorService.isTerminated()){

        }
        System.out.println("Fetching Finished");
        networkExecutorService=Executors.newFixedThreadPool(4);
    }

    public void connectGetAlbumById(String albumId, String token){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader;
                String line;
                StringBuffer response = new StringBuffer();
                try{
                    URL url = new URL("https://api.spotify.com/v1/albums/"+albumId);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty ("Authorization", "Bearer "+token);

                    int status = connection.getResponseCode();

                    if(status != 200){
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while((line = reader.readLine()) !=null){
                            response.append(line);
                        }
                        reader.close();
                    }else{
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while((line = reader.readLine())!=null){
                            response.append(line);
                        }
                        reader.close();
                    }
                    System.out.println(response.toString());
                    responseString=response.toString();
                }catch(IOException e){
                    e.printStackTrace();
                }finally{
                    connection.disconnect();
                    networkExecutorService.shutdown();
                }
            }
        });
    }


    public void connectNewRelease(String token){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("TOKEN: "+ token);
                HttpURLConnection connection = null;
                BufferedReader reader;
                String line;
                StringBuffer response = new StringBuffer();
                try {
                    URL url = new URL("https://api.spotify.com/v1/browse/new-releases");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Authorization", "Bearer " + token);

                    int status = connection.getResponseCode();

                    if (status != 200) {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                    } else {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                    }
                    responseString=response.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                    networkExecutorService.shutdown();
                }
            }
        });
    }


    public void connectToken(){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader;
                String line;
                StringBuffer response = new StringBuffer();
                try{
                    String auth="3fa081d1f2aa4d839c71bd4858fa7e52:9b846cadfb6e46648747627d54dcd449";
                    String basicAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());

                    Map<String, Object> params = new LinkedHashMap<>();
                    params.put("grant_type", "client_credentials");

                    StringBuilder postData = new StringBuilder();
                    for(Map.Entry<String, Object> param : params.entrySet()){
                        if(postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    URL url = new URL("https://accounts.spotify.com/api/token");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty ("Authorization", basicAuth);
                    connection.setDoOutput(true);
                    connection.getOutputStream().write(postDataBytes);

                    int status = connection.getResponseCode();

                    if(status != 200){
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while((line = reader.readLine()) !=null){
                            response.append(line);
                        }
                        reader.close();
                    }else{
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while((line = reader.readLine())!=null){
                            response.append(line);
                        }
                        reader.close();
                    }
                    responseString=response.toString();
                }catch(IOException e){
                    e.printStackTrace();
                }finally{
                    connection.disconnect();
                    networkExecutorService.shutdown();
                }
            }
        });
    }

    public void getFavourite(String params, String token){
        connectFavourite(params, token);
        while(!networkExecutorService.isTerminated()){

        }
        System.out.println("Fetching Finished");
        networkExecutorService=Executors.newFixedThreadPool(4);
    }

    public void connectFavourite(String params, String token){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader;
                String line;
                StringBuffer response = new StringBuffer();
                try{
                    URL url = new URL("https://api.spotify.com/v1/tracks?ids="+params);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty ("Authorization", "Bearer "+token);

                    int status = connection.getResponseCode();

                    if(status != 200){
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while((line = reader.readLine()) !=null){
                            response.append(line);
                        }
                        reader.close();
                    }else{
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while((line = reader.readLine())!=null){
                            response.append(line);
                        }
                        reader.close();
                    }
                    System.out.println(response.toString());
                    responseString = response.toString();
                }catch(IOException e){
                    e.printStackTrace();
                }finally{
                    connection.disconnect();
                    networkExecutorService.shutdown();
                }
            }
        });
    }

}
