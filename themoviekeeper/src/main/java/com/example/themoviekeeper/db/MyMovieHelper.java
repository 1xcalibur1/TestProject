package com.example.themoviekeeper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.util.ArrayList;

import static com.example.themoviekeeper.db.DBConstants.LOG_TAG;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_actors;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_awards;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_country;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_director;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_extracol1;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_extracol2;
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
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_actors_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_awards_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_country_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_director_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_genre_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_id_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_imdbid_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_imdbrating_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_imdbvotes_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_language_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_metascore_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_moviestate_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_personalRating_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_plot_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_poster_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_rated_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_released_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_response_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_runtime_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_title_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_type_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_writer_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_year_Index;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_DATABASE_NAME;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_DATABASE_VERSION;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_TABLE_NAME;

/**
 * Created by jbt on 25/12/2016.
 */

public class MyMovieHelper {

    private MyMovieDBHelper dbhelper;

    public MyMovieHelper(Context context) {
        dbhelper = new MyMovieDBHelper(context, MOVIE_DATABASE_NAME, null,
                MOVIE_DATABASE_VERSION);
    }

    // Get all books
    public ArrayList<MyMovie> getAllMyMovies() {

        ArrayList<MyMovie> list = new ArrayList<MyMovie>();

        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(MOVIE_TABLE_NAME, null, null,null,
                    null, null, null);
        }catch (SQLiteException e){
            e.getCause();
        }


        if (cursor == null){
            //list.add(new MyMovie());
            return list;
        }
        else{
            while (cursor.moveToNext()){
                int id = cursor.getInt(MOVIE_COLUMN_id_Index);

                 String title = cursor.getString(MOVIE_COLUMN_title_Index);
                 String rated = cursor.getString(MOVIE_COLUMN_rated_Index);
                 String released = cursor.getString(MOVIE_COLUMN_released_Index);

                 String runtime = cursor.getString(MOVIE_COLUMN_runtime_Index);
                 String genre = cursor.getString(MOVIE_COLUMN_genre_Index);
                 String director = cursor.getString(MOVIE_COLUMN_director_Index);
                 String writer = cursor.getString(MOVIE_COLUMN_writer_Index);
                 String actors = cursor.getString(MOVIE_COLUMN_actors_Index);
                 String plot = cursor.getString(MOVIE_COLUMN_plot_Index);
                 String language = cursor.getString(MOVIE_COLUMN_language_Index);
                 String country = cursor.getString(MOVIE_COLUMN_country_Index);
                 String awards = cursor.getString(MOVIE_COLUMN_awards_Index);
                 String poster = cursor.getString(MOVIE_COLUMN_poster_Index);
                 String year = cursor.getString(MOVIE_COLUMN_year_Index);
                 String metascore = cursor.getString(MOVIE_COLUMN_metascore_Index);
                 String imdbrating = cursor.getString(MOVIE_COLUMN_imdbrating_Index);
                 String imdbvotes = cursor.getString(MOVIE_COLUMN_imdbvotes_Index);
                 String imdbid = cursor.getString(MOVIE_COLUMN_imdbid_Index);
                 String type = cursor.getString(MOVIE_COLUMN_type_Index);
                 String response = cursor.getString(MOVIE_COLUMN_response_Index);
                String moviestate = cursor.getString(MOVIE_COLUMN_moviestate_Index);
                float personalRating = cursor.getFloat(MOVIE_COLUMN_personalRating_Index);

                list.add(new MyMovie(id,title,year,rated,
                       released,runtime,genre,
                       director,writer,actors,plot,
                       language,country,awards,poster,
                       metascore,imdbrating,imdbvotes,
                       imdbid,type,response,moviestate,personalRating));
            }
        }

        return list;

    }

    // Get a single Movie
    public MyMovie get(int id) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        MyMovie movie = null;

        Cursor cursor = db.query(MOVIE_TABLE_NAME, null, MOVIE_COLUMN_NAME_id + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        // Check if the book was found
        if (cursor.moveToFirst()) {
             movie = new MyMovie(cursor.getInt(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),
                    cursor.getString(6),cursor.getString(7),
                    cursor.getString(8),cursor.getString(9),
                    cursor.getString(10),cursor.getString(11),
                    cursor.getString(12),cursor.getString(13),
                    cursor.getString(14),cursor.getString(15),
                     cursor.getString(16),cursor.getString(17),
                     cursor.getString(18),cursor.getString(19),
                     cursor.getString(20),cursor.getString(21),
                     cursor.getFloat(22));


        }
        return movie;
    }

    public Boolean checkMovieTitleExist(String mymovieTitle) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        //MyMovie movie = null;

        Cursor cursor = db.query(MOVIE_TABLE_NAME, null, MOVIE_COLUMN_NAME_title + "=?",
                new String[] { String.valueOf(mymovieTitle) }, null, null, null, null);

        // Check if the book was found
        if (cursor.moveToFirst()) {
            return true;

        }
        return false;
    }

    public int getID(String mymovieTitle) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        //MyMovie movie = null;

        Cursor cursor = db.query(MOVIE_TABLE_NAME, null, MOVIE_COLUMN_NAME_title + "=?",
                new String[] { String.valueOf(mymovieTitle) }, null, null, null, null);

        // Check if the book was found
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);

        }
        return -1;
    }

    // Adding a new 
    public void add(MyMovie movie) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put(MOVIE_COLUMN_NAME_title,movie.getTitle());
        newValues.put(MOVIE_COLUMN_NAME_year,movie.getYear());
        newValues.put(MOVIE_COLUMN_NAME_rated,movie.getRated());
        newValues.put(MOVIE_COLUMN_NAME_released,movie.getReleased());
        newValues.put(MOVIE_COLUMN_NAME_runtime,movie.getRuntime());
        newValues.put(MOVIE_COLUMN_NAME_genre,movie.getGenre());
        newValues.put(MOVIE_COLUMN_NAME_director,movie.getDirector());
        newValues.put(MOVIE_COLUMN_NAME_writer,movie.getWriter());
        newValues.put(MOVIE_COLUMN_NAME_actors,movie.getActors());
        newValues.put(MOVIE_COLUMN_NAME_plot,movie.getPlot());
        newValues.put(MOVIE_COLUMN_NAME_language,movie.getLanguage());
        newValues.put(MOVIE_COLUMN_NAME_country,movie.getCountry());
        newValues.put(MOVIE_COLUMN_NAME_awards,movie.getAwards());
        newValues.put(MOVIE_COLUMN_NAME_poster,movie.getPoster());
        newValues.put(MOVIE_COLUMN_NAME_metascore,movie.getMetascore());
        newValues.put(MOVIE_COLUMN_NAME_imdbrating,movie.getImdbrating());
        newValues.put(MOVIE_COLUMN_NAME_imdbvotes,movie.getImdbvotes());
        newValues.put(MOVIE_COLUMN_NAME_imdbid,movie.getImdbid());
        newValues.put(MOVIE_COLUMN_NAME_type,movie.getType());
        newValues.put(MOVIE_COLUMN_NAME_extracol1,movie.getMoviestate());
        newValues.put(MOVIE_COLUMN_NAME_extracol2,movie.getPersonalRating());
        //newValues.put(MOVIE_COLUMN_NAME_response,movie.getResponse());

        // Inserting the new row, or throwing an exception if an error occurred
        try {
            db.insertOrThrow(MOVIE_TABLE_NAME, null, newValues);
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }

    // Update a book
    public void updateMyMovie(MyMovie movie) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(MOVIE_COLUMN_NAME_title,movie.getTitle());
            values.put(MOVIE_COLUMN_NAME_year,movie.getYear());
            values.put(MOVIE_COLUMN_NAME_rated,movie.getRated());
            values.put(MOVIE_COLUMN_NAME_released,movie.getReleased());
            values.put(MOVIE_COLUMN_NAME_runtime,movie.getRuntime());
            values.put(MOVIE_COLUMN_NAME_genre,movie.getGenre());
            values.put(MOVIE_COLUMN_NAME_director,movie.getDirector());
            values.put(MOVIE_COLUMN_NAME_writer,movie.getWriter());
            values.put(MOVIE_COLUMN_NAME_actors,movie.getActors());
            values.put(MOVIE_COLUMN_NAME_plot,movie.getPlot());
            values.put(MOVIE_COLUMN_NAME_language,movie.getLanguage());
            values.put(MOVIE_COLUMN_NAME_country,movie.getCountry());
            values.put(MOVIE_COLUMN_NAME_awards,movie.getAwards());
            values.put(MOVIE_COLUMN_NAME_poster,movie.getPoster());
            values.put(MOVIE_COLUMN_NAME_metascore,movie.getMetascore());
            values.put(MOVIE_COLUMN_NAME_imdbrating,movie.getImdbrating());
            values.put(MOVIE_COLUMN_NAME_imdbvotes,movie.getImdbvotes());
            values.put(MOVIE_COLUMN_NAME_imdbid,movie.getImdbid());
            values.put(MOVIE_COLUMN_NAME_type,movie.getType());
            values.put(MOVIE_COLUMN_NAME_extracol1,movie.getMoviestate());
            values.put(MOVIE_COLUMN_NAME_extracol2,movie.getPersonalRating());
            //values.put(MOVIE_COLUMN_NAME_response,movie.getResponse());

            db.update(MOVIE_TABLE_NAME, values, MOVIE_COLUMN_NAME_id + "=?",
                    new String[] { String.valueOf(movie.getId()) } );
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }

    public void updateMyMovieMovieState(int databaseID,String moviestate) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(MOVIE_COLUMN_NAME_extracol1,moviestate);
            //values.put(MOVIE_COLUMN_NAME_response,movie.getResponse());

            db.update(MOVIE_TABLE_NAME, values, MOVIE_COLUMN_NAME_id + "=?",
                    new String[] { String.valueOf(databaseID) } );
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }

    public void updateMyMoviePersonalRating(int databaseID,Float personalRating) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(MOVIE_COLUMN_NAME_extracol2,personalRating);
            //values.put(MOVIE_COLUMN_NAME_response,movie.getResponse());

            db.update(MOVIE_TABLE_NAME, values, MOVIE_COLUMN_NAME_id + "=?",
                    new String[] { String.valueOf(databaseID) } );
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }

    public Boolean getDataExist(int databaseID,String col_name,String col_data) {

        SQLiteDatabase db = dbhelper.getReadableDatabase();
        MyMovie movie = null;

        Cursor cursor = db.query(MOVIE_TABLE_NAME, null, MOVIE_COLUMN_NAME_id + "=? " + "AND "+
                col_name + "=?",
                new String[] { String.valueOf(databaseID), col_data }, null, null, null, null);

        // Check if the book was found
        if (cursor.moveToFirst()) {
            return true;
        }

        return false;
    }

    public void deleteMyMovie(String id) {
        SQLiteDatabase db = null;
        try {
            db = dbhelper.getWritableDatabase();

            db.delete(MOVIE_TABLE_NAME, MOVIE_COLUMN_NAME_id + "=?",
                    new String[]{id});

        } catch (SQLiteException e) {
            e.getCause();

        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
    }

    // Delete a book
    public void deleteMyMovie(MyMovie movie) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try {
            db.delete(MOVIE_TABLE_NAME, MOVIE_COLUMN_NAME_id + "=?",
                    new String[] { String.valueOf(movie.getId()) } );
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }

    public void deleteAllMovies() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try {
            db.delete(MOVIE_TABLE_NAME, MOVIE_COLUMN_NAME_id,null);
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }
}
