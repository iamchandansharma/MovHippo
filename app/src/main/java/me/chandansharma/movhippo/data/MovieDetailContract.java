package me.chandansharma.movhippo.data;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by CS on 2/3/2017.
 */
public final class MovieDetailContract {

    private MovieDetailContract(){}

    //Content Authority
    public static final String CONTENT_AUTHORITY = "me.chandansharma.movhippo";

    //Base Uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //content path
    public static final String PATH_MOVIE = "movie";

    public static final class MovieDetailEntry {

        //Content Uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MOVIE);

        //all the constant filed that are used to store data into database
        public static final String TABLE_NAME = "movie";
        public static final String _ID = "movie_id";
        public static final String COLUMN_MOVIE_NAME = "movie_name";
        public static final String COLUMN_MOVIE_TAG_LINE = "movie_tag_line";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String COLUMN_MOVIE_RUNTIME = "movie_runtime";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "movie_vote_count";
        public static final String COLUMN_MOVIE_POPULARITY = "movie_popularity";
        public static final String COLUMN_MOVIE_LANGUAGE = "movie_language";
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";
        public static final String COLUMN_MOVIE_DB_STATUS = "movie_status";
        public static final String COLUMN_MOVIE_POSTER = "movie_poster";
        public static final String COLUMN_MOVIE_BACKDROP = "movie_backdrop";

        //the MIME type of the Movie
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        //the MIME type of the single Movie
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
    }
}
