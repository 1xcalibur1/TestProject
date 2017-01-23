package com.example.themoviekeeper.db;

/**
 * Created by Xcalibur on 08/12/2016.
 */

public class DBConstants {

    public final static int MOVIE_DATABASE_VERSION =6;
    public final static String MOVIE_DATABASE_NAME = "MOVIEDb.db";
    public final static String MOVIE_TABLE_NAME = "movie";
    public final static String MOVIE_COLUMN_NAME_id = "_id";
    public final static String MOVIE_COLUMN_NAME_title = "title";
    public final static String MOVIE_COLUMN_NAME_year = "year";
    public final static String MOVIE_COLUMN_NAME_rated = "rated";
    public final static String MOVIE_COLUMN_NAME_released = "released";
    public final static String MOVIE_COLUMN_NAME_runtime = "runtime";
    public final static String MOVIE_COLUMN_NAME_genre = "genre";
    public final static String MOVIE_COLUMN_NAME_director = "director";
    public final static String MOVIE_COLUMN_NAME_writer = "writer";
    public final static String MOVIE_COLUMN_NAME_actors = "actors";
    public final static String MOVIE_COLUMN_NAME_plot = "plot";
    public final static String MOVIE_COLUMN_NAME_language = "language";
    public final static String MOVIE_COLUMN_NAME_country = "country";
    public final static String MOVIE_COLUMN_NAME_awards = "awards";
    public final static String MOVIE_COLUMN_NAME_poster = "poster";
    public final static String MOVIE_COLUMN_NAME_metascore = "metascore";
    public final static String MOVIE_COLUMN_NAME_imdbrating = "imdbrating";
    public final static String MOVIE_COLUMN_NAME_imdbvotes = "imdbvotes";
    public final static String MOVIE_COLUMN_NAME_imdbid = "imdbid";
    public final static String MOVIE_COLUMN_NAME_type = "type";
    public final static String MOVIE_COLUMN_NAME_response = "response";
    public final static String MOVIE_COLUMN_NAME_extracol1 = "moviestate";
    public final static String MOVIE_COLUMN_NAME_extracol2 = "personalRating";
    public final static String MOVIE_COLUMN_NAME_extracol3 = "extracol3";

    public final static int MOVIE_COLUMN_id_Index = 0;
    public final static int MOVIE_COLUMN_title_Index = 1;
    public final static int MOVIE_COLUMN_year_Index = 2;
    public final static int MOVIE_COLUMN_rated_Index = 3;
    public final static int MOVIE_COLUMN_released_Index = 4;
    public final static int MOVIE_COLUMN_runtime_Index = 5;
    public final static int MOVIE_COLUMN_genre_Index = 6;
    public final static int MOVIE_COLUMN_director_Index = 7;
    public final static int MOVIE_COLUMN_writer_Index = 8;
    public final static int MOVIE_COLUMN_actors_Index = 9;
    public final static int MOVIE_COLUMN_plot_Index = 10;
    public final static int MOVIE_COLUMN_language_Index = 11;
    public final static int MOVIE_COLUMN_country_Index = 12;
    public final static int MOVIE_COLUMN_awards_Index = 13;
    public final static int MOVIE_COLUMN_poster_Index = 14;
    public final static int MOVIE_COLUMN_metascore_Index = 15;
    public final static int MOVIE_COLUMN_imdbrating_Index = 16;
    public final static int MOVIE_COLUMN_imdbvotes_Index = 17;
    public final static int MOVIE_COLUMN_imdbid_Index = 18;
    public final static int MOVIE_COLUMN_type_Index = 19;
    public final static int MOVIE_COLUMN_response_Index = 20;
    public final static int MOVIE_COLUMN_moviestate_Index = 21;
    public final static int MOVIE_COLUMN_personalRating_Index = 22;

    public final static String[] CATEGORY_ARRAY = { "Title","Year","Rated", "Released",
    "Runtime","Genre", "Director", "Writer","Actors","Plot",
    "Language","Country","Awards","Poster","Metascore","imdbRating","imdbVotes",
    "imdbID","Type"};

    public final static int CALL_SEARCHMYMOVIEACTIVITY = 10;
    public final static int CALL_EDITMYMOVIEACTIVITY_MANUAL = 11;
    public final static int CALL_EDITMYMOVIEACTIVITY_UPDATE = 12;
    public final static int CALL_EDITMYMOVIEACTIVITY_SEARCH = 13;

    public static final String LOG_TAG = "MOVIEDb";

}
