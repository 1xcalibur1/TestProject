package com.example.themoviekeeper.networking;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.db.MyMovie;
import com.example.themoviekeeper.ui.RecyclerViewAdapter_Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Xcalibur on 28/12/2016.
 */

public class Task_GetMyMovieList extends AsyncTask<String,Void,String> {

    private Activity activity;
    private ProgressBar pb_search;

    public Task_GetMyMovieList(Activity act) {

        activity = act;
        pb_search = (ProgressBar)activity.findViewById(R.id.pb_search);

    }

    @Override
    protected void onPreExecute() {
        pb_search.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        return sendHttpRequest(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        String all = "";
        ArrayList<MyMovie> list = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(s);
            JSONArray arr = object.getJSONArray("Search");

            for (int i = 0; i <arr.length() ; i++) {

                JSONObject obj = arr.getJSONObject(i);

                String title = obj.getString("Title");
                String poster = obj.getString("Poster");
                String imdb_id = obj.getString("imdbID");
                String type = obj.getString("Type");
                String year = obj.getString("Year");

                MyMovie movie = new MyMovie(title,year,poster,type,imdb_id);

                list.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView rv_search = (RecyclerView)activity.findViewById(R.id.rv_search);
        RecyclerViewAdapter_Search rva_search = new RecyclerViewAdapter_Search(list,activity);
        rv_search.setLayoutManager(new LinearLayoutManager(activity));
        rv_search.setAdapter(rva_search);

        pb_search.setVisibility(View.GONE);


    }

    private String sendHttpRequest(String urlString) {
        BufferedReader input = null;
        HttpURLConnection httpCon = null;
        InputStream input_stream =null;
        InputStreamReader input_stream_reader = null;
        StringBuilder response = new StringBuilder();
        try{
            URL url = new URL(urlString);
            httpCon = (HttpURLConnection)url.openConnection();
            if(httpCon.getResponseCode()!=HttpURLConnection.HTTP_OK){
                Log.e("TAG","Cannot Connect to : "+ urlString);
                return null;
            }

            input_stream = httpCon.getInputStream();
            input_stream_reader = new InputStreamReader(input_stream);
            input = new BufferedReader(input_stream_reader);
            String line ;
            while ((line = input.readLine())!= null){
                response.append(line +"\n");
            }



        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(input!=null){
                try {
                    input_stream_reader.close();
                    input_stream.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(httpCon != null){
                    httpCon.disconnect();
                }
            }
        }

        return response.toString();
    }
}

