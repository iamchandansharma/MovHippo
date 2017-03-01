package me.chandansharma.movhippo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import me.chandansharma.movhippo.data.MovieDetailContract.MovieDetailEntry;

/**
 * Created by CS on 2/4/2017.
 */
public class MovieDetailProvider extends ContentProvider {

    //Tag of the class
    private static final String TAG = MovieDetailProvider.class.getSimpleName();

    //path name for retrieve data from database
    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;

    //Uri matcher for matching uri
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //create content Uri
        sUriMatcher.addURI(MovieDetailContract.CONTENT_AUTHORITY, MovieDetailContract.PATH_MOVIE,MOVIE);
        sUriMatcher.addURI(MovieDetailContract.CONTENT_AUTHORITY, MovieDetailContract.PATH_MOVIE + "/#",MOVIE_ID);
    }

    //database object
    private MovieDetailDbHelper mMovieDetailDbHelper;

    @Override
    public boolean onCreate() {
        mMovieDetailDbHelper = new MovieDetailDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase movieDatabase = mMovieDetailDbHelper.getReadableDatabase();
        //to get the result cursor
        Cursor cursor;
        int matchMovieUri = sUriMatcher.match(uri);
        switch (matchMovieUri){
            case MOVIE :
                cursor = movieDatabase.query(MovieDetailEntry.TABLE_NAME,
                        projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MOVIE_ID :
                selection = MovieDetailEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = movieDatabase.query(MovieDetailEntry.TABLE_NAME,projection,
                        selection,selectionArgs,null,null,sortOrder);
                break;
            default :
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        //return cursor
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //match uri in in order to insert data
        int matchMovieUri = sUriMatcher.match(uri);
        switch (matchMovieUri){
            case MOVIE :
                return insertMovieDetails(uri,values);
            default:
                throw new IllegalArgumentException("Insertion is not support for " + uri);
        }
    }

    private Uri insertMovieDetails(Uri uri, ContentValues contentValues) {

        //Get writable database for inserting value in database
        SQLiteDatabase movieDatabase = mMovieDetailDbHelper.getWritableDatabase();

        //insert new Movie detail with given values
        long id = movieDatabase.insert(MovieDetailEntry.TABLE_NAME,null,contentValues);
        if(id == -1){
            Log.e(TAG,"Failed to insert data into DB" + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);

        //return the new uri with the ID append at the end
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        //get writable database for deleting content from DB
        SQLiteDatabase movieDatabase = mMovieDetailDbHelper.getWritableDatabase();

        //track the number of row deleted from the DB
        int rowDeleted;

        //match uri in order to delete the data
        int matchMovieUri = sUriMatcher.match(uri);
        switch (matchMovieUri){
            case MOVIE :
                //delete all movie details (all row) from database
                rowDeleted = movieDatabase.delete(MovieDetailEntry.TABLE_NAME,
                        selection,selectionArgs);
                break;
            case MOVIE_ID :
                //delete single Movie data from the DB
                selection = MovieDetailEntry._ID + "?=";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted = movieDatabase.delete(MovieDetailEntry.TABLE_NAME,
                        selection,selectionArgs);
                break;
            default :
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        //if 1 or more rows were deleted, then notify all listeners that the data at the
        //given URI changed
        getContext().getContentResolver().notifyChange(uri,null);

        //return no of row deleted
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        int matchMovieUri = sUriMatcher.match(uri);
        switch (matchMovieUri){
            case MOVIE :
                return MovieDetailEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID :
                return MovieDetailEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + matchMovieUri);
        }
    }
}
