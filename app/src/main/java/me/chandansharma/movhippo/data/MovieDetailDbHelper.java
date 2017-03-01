package me.chandansharma.movhippo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.chandansharma.movhippo.data.MovieDetailContract.MovieDetailEntry;

/**
 * Created by CS on 2/3/2017.
 */
public class MovieDetailDbHelper extends SQLiteOpenHelper {

    //Tag for the class
    public static final String TAG = MovieDetailDbHelper.class.getSimpleName();

    //Database name and database version
    public static final String DATABASE_NAME = "movie.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDetailDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_MOVIE_DETAIL_QUERY =
                "CREATE TABLE " + MovieDetailEntry.TABLE_NAME + "(" +
                        MovieDetailEntry._ID + " INTEGER PRIMARY KEY, " +
                        MovieDetailEntry.COLUMN_MOVIE_NAME + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_TAG_LINE + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_RUNTIME + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_VOTE_COUNT + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_POPULARITY + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_LANGUAGE + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_OVERVIEW + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_POSTER + " TEXT, " +
                        MovieDetailEntry.COLUMN_MOVIE_BACKDROP + " TEXT" + ")";

        db.execSQL(SQL_CREATE_MOVIE_DETAIL_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_MOVIE_DETAIL_QUERY =
                "DELETE FROM " + MovieDetailEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_MOVIE_DETAIL_QUERY);
    }
}
