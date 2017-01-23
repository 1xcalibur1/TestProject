package com.example.themoviekeeper.networking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.db.MyMovie;
import com.example.themoviekeeper.db.MyMovieCategory;
import com.example.themoviekeeper.ui.EditMyMovieActivity;
import com.example.themoviekeeper.ui.RecyclerViewAdapter_Edit;
import com.example.themoviekeeper.ui.RecyclerViewAdapter_Search;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.themoviekeeper.R.id.rv_search;

/**
 * Created by Xcalibur on 28/12/2016.
 */

public class Task_GetMyMovieDetails extends AsyncTask<String,Void,String> {

    private Activity activity;

    public Task_GetMyMovieDetails(Activity act) {

        activity = act;

    }

    @Override
    protected String doInBackground(String... strings) {
        return sendHttpRequest(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        //String all = "";
        try {


            JSONObject object = new JSONObject(s);
            ArrayList<MyMovieCategory> categoryArrayList = new ArrayList<>();

            String Title = object.getString("Title");
            categoryArrayList.add(new MyMovieCategory("Title",Title));

            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                    activity.findViewById(R.id.collapse_toolbar);
            collapsingToolbarLayout.setTitle(Title);

            String Year = object.getString("Year");
            categoryArrayList.add(new MyMovieCategory("Year",Year));
            String Rated = object.getString("Rated");
            categoryArrayList.add(new MyMovieCategory("Rated",Rated));
            String Released = object.getString("Released");
            categoryArrayList.add(new MyMovieCategory("Released",Released));
            String Runtime = object.getString("Runtime");
            categoryArrayList.add(new MyMovieCategory("Runtime",Runtime));
            String Genre = object.getString("Genre");
            categoryArrayList.add(new MyMovieCategory("Genre",Genre));
            String Director = object.getString("Director");
            categoryArrayList.add(new MyMovieCategory("Director",Director));
            String Writer = object.getString("Writer");
            categoryArrayList.add(new MyMovieCategory("Writer",Writer));
            String Actors = object.getString("Actors");
            categoryArrayList.add(new MyMovieCategory("Actors",Actors));
            String Plot = object.getString("Plot");
            categoryArrayList.add(new MyMovieCategory("Plot",Plot));
            String Language = object.getString("Language");
            categoryArrayList.add(new MyMovieCategory("Language",Language));
            String Country = object.getString("Country");
            categoryArrayList.add(new MyMovieCategory("Country",Country));
            String Awards = object.getString("Awards");
            categoryArrayList.add(new MyMovieCategory("Awards",Awards));
            String Poster = object.getString("Poster");

            Picasso.with(activity).load(Poster).placeholder(R.drawable.movie_default).into((ImageView)activity.findViewById(R.id.bgheader));

            categoryArrayList.add(new MyMovieCategory("Poster",Poster));
            String Metascore = object.getString("Metascore");
            categoryArrayList.add(new MyMovieCategory("Metascore",Metascore));
            String imdbRating = object.getString("imdbRating");
            categoryArrayList.add(new MyMovieCategory("imdbRating",imdbRating));
            String imdbVotes = object.getString("imdbVotes");
            categoryArrayList.add(new MyMovieCategory("imdbVotes",imdbVotes));
            String imdbID = object.getString("imdbID");
            categoryArrayList.add(new MyMovieCategory("imdbID",imdbID));
            String Type = object.getString("Type");
            categoryArrayList.add(new MyMovieCategory("Type",Type));

            //String Response = object.getString("Response");
            //categoryArrayList.add(new MyMovieCategory("Response",Response));

            RecyclerView rv_edit = (RecyclerView)activity.findViewById(R.id.rv_edit);
            RecyclerViewAdapter_Edit rva_edit = new RecyclerViewAdapter_Edit(activity,categoryArrayList);
            rv_edit.setLayoutManager(new LinearLayoutManager(activity));
            rv_edit.setAdapter(rva_edit);

            ((EditMyMovieActivity)activity).assignValuesFromTask_GetMyMovieDetails(rva_edit);


        } catch (JSONException e) {
            e.printStackTrace();
        }


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
