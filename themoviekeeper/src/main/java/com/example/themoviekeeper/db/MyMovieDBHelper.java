package com.example.themoviekeeper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.themoviekeeper.db.DBConstants.LOG_TAG;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_actors;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_awards;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_country;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_director;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_extracol1;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_extracol2;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_extracol3;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_genre;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_id;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_imdbid;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_imdbrating;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_imdbvotes;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_language;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_metascore;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_plot;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_poster;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_rated;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_released;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_response;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_runtime;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_title;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_type;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_writer;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_year;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_TABLE_NAME;

/**
 * Created by jbt on 25/12/2016.
 */

public class MyMovieDBHelper extends SQLiteOpenHelper {

    public MyMovieDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Creating all the tables");

        String CREATE_MOVIE_TABLE = "CREATE TABLE " + MOVIE_TABLE_NAME + "("
                +MOVIE_COLUMN_NAME_id + " INTEGER PRIMARY KEY," + MOVIE_COLUMN_NAME_title + " TEXT,"
                + MOVIE_COLUMN_NAME_year + " TEXT," + MOVIE_COLUMN_NAME_rated + " TEXT," +
                MOVIE_COLUMN_NAME_released + " TEXT," + MOVIE_COLUMN_NAME_runtime + " TEXT,"+
                MOVIE_COLUMN_NAME_genre + " TEXT," + MOVIE_COLUMN_NAME_director + " TEXT," +
                MOVIE_COLUMN_NAME_writer + " TEXT," + MOVIE_COLUMN_NAME_actors + " TEXT," +
                MOVIE_COLUMN_NAME_plot + " TEXT," + MOVIE_COLUMN_NAME_language + " TEXT," +
                MOVIE_COLUMN_NAME_country + " TEXT," + MOVIE_COLUMN_NAME_awards + " TEXT," +
                MOVIE_COLUMN_NAME_poster + " TEXT," + MOVIE_COLUMN_NAME_metascore + " TEXT," +
                MOVIE_COLUMN_NAME_imdbrating + " TEXT," + MOVIE_COLUMN_NAME_imdbvotes + " TEXT," +
                MOVIE_COLUMN_NAME_imdbid + " TEXT," + MOVIE_COLUMN_NAME_type + " TEXT," +
                MOVIE_COLUMN_NAME_response + " TEXT," + MOVIE_COLUMN_NAME_extracol1 + " TEXT," +
                MOVIE_COLUMN_NAME_extracol2 + " FLOAT," + MOVIE_COLUMN_NAME_extracol3 + " TEXT)";



        try {
            db.execSQL(CREATE_MOVIE_TABLE);
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, "Create table exception: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old date");
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        onCreate(db);
    }
}
