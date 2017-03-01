package me.chandansharma.movhippo.movhippo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.chandansharma.movhippo.R;
import me.chandansharma.movhippo.adapters.MovieDetailsAdapter;
import me.chandansharma.movhippo.data.MovieDetailContract;
import me.chandansharma.movhippo.models.MovieDetails;
import me.chandansharma.movhippo.models.MovieReviews;
import me.chandansharma.movhippo.models.MovieTrailers;
import me.chandansharma.movhippo.utils.AppController;
import me.chandansharma.movhippo.utils.TmdbUrls;

public class MovieDetailsActivity extends AppCompatActivity {

    //Tag name of the class
    public static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private ArrayList<MovieTrailers> mMovieTrailerList = new ArrayList<>();
    private ArrayList<MovieReviews> mMovieReviewsList = new ArrayList<>();
    private ArrayList<Object> mMovieDetails = new ArrayList<>();

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mFavoriteIconImageView;
    private RecyclerView mMovieTrailerRecyclerView;
    private MovieDetailsAdapter mMovieDetailsAdapter;
    private Toolbar mMovieActionBar;

    private String mMovieId;
    private MovieDetails mCurrentMovieDetails;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mMovieTrailerList",mMovieTrailerList);
        outState.putParcelableArrayList("mMovieReviewsList",mMovieReviewsList);

        outState.putInt("movieId",mCurrentMovieDetails.getMovieId());
        outState.putString("movieTitle",mCurrentMovieDetails.getMovieTitle());
        outState.putString("movieTagLine",mCurrentMovieDetails.getMovieTagLine());
        outState.putString("movieReleaseDate",mCurrentMovieDetails.getMovieReleaseDate());
        outState.putString("movieRuntime",mCurrentMovieDetails.getMovieRunTime());
        outState.putString("movieVoteAverage",mCurrentMovieDetails.getMovieVoteAverage());
        outState.getString("movieVoteCount",mCurrentMovieDetails.getMovieVoteCount());
        outState.putString("moviePopularity",mCurrentMovieDetails.getMoviePopularity());
        outState.putString("movieLanguage",mCurrentMovieDetails.getMovieLanguage());
        outState.putString("movieOverView",mCurrentMovieDetails.getMovieOverView());
        outState.putString("moviePosterUrl",mCurrentMovieDetails.getMoviePosterUrl());
        outState.putString("movieBackDropImageUrl",mCurrentMovieDetails.getMovieBackDropImageUrl());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMovieTrailerList = savedInstanceState.getParcelableArrayList("mMovieTrailerList");
        mMovieReviewsList = savedInstanceState.getParcelableArrayList("mMovieTrailerList");

        mCurrentMovieDetails.setMovieId(savedInstanceState.getInt("movieId"));
        mCurrentMovieDetails.setMovieTitle(savedInstanceState.getString("movieTitle"));
        mCurrentMovieDetails.setMovieTagLine(savedInstanceState.getString("movieTagLine"));
        mCurrentMovieDetails.setMovieReleaseDate(savedInstanceState.getString("movieReleaseDate"));
        mCurrentMovieDetails.setMovieRunTime(savedInstanceState.getString("movieRuntime"));
        mCurrentMovieDetails.setMovieVoteAverage(savedInstanceState.getString("movieVoteAverage"));
        mCurrentMovieDetails.setMovieVoteCount(savedInstanceState.getString("movieVoteCount"));
        mCurrentMovieDetails.setMoviePopularity(savedInstanceState.getString("moviePopularity"));
        mCurrentMovieDetails.setMovieLanguage(savedInstanceState.getString("movieLanguage"));
        mCurrentMovieDetails.setMovieOverView(savedInstanceState.getString("movieOverView"));
        mCurrentMovieDetails.setMoviePosterUrl(savedInstanceState.getString("moviePosterUrl"));
        mCurrentMovieDetails.setMovieBackDropImageUrl(savedInstanceState.getString("movieBackDropImageUrl"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent movieDetailIntent = getIntent();
        mMovieId = movieDetailIntent.getStringExtra(Intent.EXTRA_TEXT);

        //set share icon to toolbar
        mMovieActionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mMovieActionBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //favourite movie all details
        mFavoriteIconImageView = (FloatingActionButton)findViewById(R.id.favorite_button_image_view);

        if(isMovieStoreInDatabase(Integer.parseInt(mMovieId))){
            mFavoriteIconImageView.
                    setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_icon_solid));
        }
        else {
            mFavoriteIconImageView.
                    setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_icon_border));
        }
        mFavoriteIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFavoriteIconImageView.getDrawable().getConstantState().equals(ContextCompat.
                        getDrawable(getApplicationContext(), R.drawable.favorite_icon_border).getConstantState())) {
                    ContentValues currentMovieValue = new ContentValues();
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry._ID,
                            mCurrentMovieDetails.getMovieId());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_NAME,
                            mCurrentMovieDetails.getMovieTitle());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_TAG_LINE,
                            mCurrentMovieDetails.getMovieTagLine());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_RELEASE_DATE,
                            mCurrentMovieDetails.getMovieReleaseDate());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_RUNTIME,
                            mCurrentMovieDetails.getMovieRunTime());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_VOTE_AVERAGE,
                            mCurrentMovieDetails.getMovieVoteAverage());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_VOTE_COUNT,
                            mCurrentMovieDetails.getMovieVoteCount());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_POPULARITY,
                            mCurrentMovieDetails.getMoviePopularity());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_LANGUAGE,
                            mCurrentMovieDetails.getMovieLanguage());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_OVERVIEW,
                            mCurrentMovieDetails.getMovieOverView());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_POSTER,
                            mCurrentMovieDetails.getMoviePosterUrl());
                    currentMovieValue.put(MovieDetailContract.MovieDetailEntry.COLUMN_MOVIE_BACKDROP,
                            mCurrentMovieDetails.getMovieBackDropImageUrl());

                    //insert movie into database;
                    getContentResolver().insert(MovieDetailContract.MovieDetailEntry.CONTENT_URI,
                            currentMovieValue);

                    mFavoriteIconImageView.
                            setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_icon_solid));
                    Toast.makeText(getApplicationContext(), R.string.movie_added_favourite, Toast.LENGTH_SHORT).show();
                } else {

                    Uri movieDetailUri = MovieDetailContract.MovieDetailEntry.CONTENT_URI;
                    String selection = MovieDetailContract.MovieDetailEntry._ID + "=?";
                    String[] selectionArgs = new String[]{String.valueOf(mCurrentMovieDetails.getMovieId())};

                    //delete movie from database
                    getContentResolver().delete(movieDetailUri, selection, selectionArgs);

                    mFavoriteIconImageView.
                            setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_icon_border));
                    Toast.makeText(getApplicationContext(), R.string.movie_removed_favourite, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Movie Url
        String mMovieUrl = TmdbUrls.MOVIE_BASE_URL + mMovieId + "?" + TmdbUrls.API_KEY_TAG + "=" +
                TmdbUrls.API_KEY;
        mCurrentMovieDetails = new MovieDetails();
        getMovieData(mMovieUrl);


        //set the CollapsingToolbarLayout title
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white));

        //mMovieDetailsAdapter = new MovieDetailsAdapter(this,mMovieTrailerList,true);
        mMovieTrailerRecyclerView = (RecyclerView) findViewById(R.id.movie_trailer_and_review_recycler_view);
        mMovieTrailerRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        //Play button
        findViewById(R.id.play_button_image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoUrlIntent = new Intent(Intent.ACTION_VIEW);
                videoUrlIntent.setData(Uri.parse("https://m.youtube.com/watch?v=" +
                        ((MovieTrailers) mMovieDetails.get(0)).getMovieTrailerKey()));
                startActivity(videoUrlIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_share:
                Log.d(TAG,"Share Intent");
                Intent shareMovieIntent = new Intent(Intent.ACTION_SEND);
                shareMovieIntent.setType("text/plain");
                shareMovieIntent.putExtra(Intent.EXTRA_TEXT, "Hey Check Out this new Movie : " +
                        "https://www.themoviedb.org/movie/" + mCurrentMovieDetails.getMovieId());
                startActivity(shareMovieIntent);
                return true;
        }
        return true;
    }

    private void getMovieData(String mMovieUrl) {
        //Tag used to cancel the request
        String jsonObjectTag = "jsonObjectTag";

        JsonObjectRequest movieJsonObject = new JsonObjectRequest(Request.Method.GET, mMovieUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject mMovieResultObject) {
                        try {
                            mCurrentMovieDetails.setMovieId(Integer.parseInt(mMovieId));
                            mCurrentMovieDetails.setMovieTitle(mMovieResultObject.getString("original_title"));
                            mCurrentMovieDetails.setMovieTagLine("\"" + mMovieResultObject.getString("tagline") + "\"");
                            mCurrentMovieDetails.setMovieReleaseDate(mMovieResultObject.getString("release_date"));
                            mCurrentMovieDetails.setMovieRunTime(mMovieResultObject.getString("runtime") + " Minutes");
                            mCurrentMovieDetails.setMovieVoteAverage(mMovieResultObject.getString("vote_average"));
                            mCurrentMovieDetails.setMovieVoteCount(mMovieResultObject.getString("vote_count") + " " + "Votes");
                            mCurrentMovieDetails.setMoviePopularity(mMovieResultObject.getString("popularity").substring(0, 4));
                            mCurrentMovieDetails.setMovieLanguage(mMovieResultObject.getString("original_language"));
                            mCurrentMovieDetails.setMovieOverView(mMovieResultObject.getString("overview"));
                            mCurrentMovieDetails.setMoviePosterUrl("http://image.tmdb.org/t/p/w342/"
                                    + mMovieResultObject.getString("poster_path"));
                            mCurrentMovieDetails.setMovieBackDropImageUrl("http://image.tmdb.org/t/p/w780/"
                                    + mMovieResultObject.getString("backdrop_path"));

                            ((TextView) findViewById(R.id.movie_title_text_view))
                                    .setText(mCurrentMovieDetails.getMovieTitle());
                            mCollapsingToolbarLayout.setTitle(mMovieResultObject.getString("original_title"));
                            ((TextView) findViewById(R.id.movie_title_text_view))
                                    .setTypeface(Typeface.createFromAsset(getAssets(), "Sansation-Bold.ttf"));

                            ((TextView) findViewById(R.id.movie_tag_line_text_view))
                                    .setText(mCurrentMovieDetails.getMovieTagLine());
                            ((TextView) findViewById(R.id.movie_tag_line_text_view))
                                    .setTypeface(Typeface.createFromAsset(getAssets(), "Sansation-Italic.ttf"));

                            ((TextView) findViewById(R.id.movie_release_date_text_view))
                                    .setText(mCurrentMovieDetails.getMovieReleaseDate());
                            ((TextView) findViewById(R.id.movie_release_date_text_view))
                                    .setTypeface(Typeface.createFromAsset(getAssets(), "Sansation-Italic.ttf"));

                            ((TextView) findViewById(R.id.movie_duration_text_view))
                                    .setText(mCurrentMovieDetails.getMovieRunTime());
                            ((TextView) findViewById(R.id.movie_duration_text_view))
                                    .setTypeface(Typeface.createFromAsset(getAssets(), "Sansation-Italic.ttf"));

                            ((TextView) findViewById(R.id.movie_over_view_text_view))
                                    .setText(mMovieResultObject.getString("overview"));
                            ((TextView) findViewById(R.id.movie_over_view_text_view))
                                    .setTypeface(Typeface.createFromAsset(getAssets(), "Sansation-Italic.ttf"));

                            ((TextView) findViewById(R.id.user_rating_text_view))
                                    .setText(mCurrentMovieDetails.getMovieVoteAverage());
                            ((TextView) findViewById(R.id.user_rating_text_view))
                                    .setTypeface(Typeface.createFromAsset(getAssets(), "Sansation-Italic.ttf"));

                            ((TextView) findViewById(R.id.vote_average_text_view))
                                    .setText(mCurrentMovieDetails.getMovieVoteCount());

                            ((TextView) findViewById(R.id.popularity_text_view))
                                    .setText(mCurrentMovieDetails.getMoviePopularity());
                            ((TextView) findViewById(R.id.popularity_text_view))
                                    .setTypeface(Typeface.createFromAsset(getAssets(), "Sansation-Italic.ttf"));

                            ((TextView) findViewById(R.id.language_text_view))
                                    .setText(mCurrentMovieDetails.getMovieLanguage());
                            ((TextView) findViewById(R.id.language_text_view))
                                    .setTypeface(Typeface.createFromAsset(getAssets(), "Sansation-Italic.ttf"));


                            Picasso.with(getApplicationContext()).load(mCurrentMovieDetails.getMoviePosterUrl())
                                    .into((ImageView) findViewById(R.id.movie_image_view));
                            Picasso.with(getApplicationContext()).load(mCurrentMovieDetails.getMovieBackDropImageUrl())
                                    .into((ImageView) findViewById(R.id.movie_poster_image_view));

                            //Movie trailerUrl
                            String mMovieTrailerUrl = TmdbUrls.MOVIE_BASE_URL + mMovieId + "/" +
                                    TmdbUrls.TRAILER_VIDEO_TAG + "?" + TmdbUrls.API_KEY_TAG + "=" + TmdbUrls.API_KEY;
                            getMovieTrailers(mMovieTrailerUrl);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(mCollapsingToolbarLayout, "Please Check Your Internet Connection..",
                                Snackbar.LENGTH_LONG).show();
                    }
                });
        //Adding request to the queue
        AppController.getInstance().addToRequestQueue(movieJsonObject, jsonObjectTag);
    }

    private void getMovieTrailers(String mMovieTrailerUrl) {
        //Tag used to cancel the request
        String jsonObjectTag = "jsonObjectTag";

        JsonObjectRequest mMovieTrailerJsonObject = new JsonObjectRequest(Request.Method.GET,
                mMovieTrailerUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //get the root object array
                            JSONArray mMovieResultArray = response.getJSONArray("results");

                            for (int i = 0; i < mMovieResultArray.length(); i++) {
                                JSONObject mMovieResultObject = mMovieResultArray.getJSONObject(i);

                                MovieTrailers singleMovieTrailerDetail = new MovieTrailers(
                                        mMovieResultObject.getString("key"),
                                        mMovieResultObject.getString("name"),
                                        mMovieResultObject.getString("site"),
                                        mMovieResultObject.getString("size"),
                                        mMovieResultObject.getString("type"));
                                mMovieTrailerList.add(singleMovieTrailerDetail);
                                mMovieDetails.add(singleMovieTrailerDetail);
                            }
                            mMovieDetailsAdapter = new MovieDetailsAdapter(getBaseContext(), mMovieDetails, mMovieTrailerList);
                            mMovieTrailerRecyclerView.setNestedScrollingEnabled(true);
                            mMovieTrailerRecyclerView.setAdapter(mMovieDetailsAdapter);
                            mMovieTrailerRecyclerView.setNestedScrollingEnabled(false);
                            //Movie reviewUrl
                            String mMovieReviewUrl = TmdbUrls.MOVIE_BASE_URL + mMovieId + "/" +
                                    TmdbUrls.MOVIE_REVIEW_TAG + "?" + TmdbUrls.API_KEY_TAG + "=" + TmdbUrls.API_KEY;
                            getMovieReviews(mMovieReviewUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Movie reviewUrl
                        String mMovieReviewUrl = TmdbUrls.MOVIE_BASE_URL + mMovieId + "/" +
                                TmdbUrls.MOVIE_REVIEW_TAG + "?" + TmdbUrls.API_KEY_TAG + "=" + TmdbUrls.API_KEY;
                        getMovieReviews(mMovieReviewUrl);
                    }
                });
        //Adding response to the queue
        AppController.getInstance().addToRequestQueue(mMovieTrailerJsonObject, jsonObjectTag);
    }

    private void getMovieReviews(String mMovieReviewUrl) {
        //Tag used to cancel the request
        String jsonObjectTag = "jsonObjectTag";

        JsonObjectRequest mMovieReviewJsonObject = new JsonObjectRequest(Request.Method.GET, mMovieReviewUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray mMovieResultArray = response.getJSONArray("results");
                            for (int i = 0; i < mMovieResultArray.length(); i++) {
                                JSONObject mMovieResultObject = mMovieResultArray.getJSONObject(i);
                                MovieReviews singleMovieReviews = new MovieReviews(
                                        mMovieResultObject.getString("author"),
                                        mMovieResultObject.getString("content"));
                                mMovieReviewsList.add(singleMovieReviews);
                                mMovieDetails.add(singleMovieReviews);
                            }
                            mMovieDetailsAdapter = new MovieDetailsAdapter(getBaseContext(), mMovieDetails, mMovieTrailerList);
                            mMovieDetailsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(mMovieReviewJsonObject, jsonObjectTag);
    }

    private boolean isMovieStoreInDatabase(int Id) {

        //Uri for requesting data from Database
        Uri movieContentUri = MovieDetailContract.MovieDetailEntry.CONTENT_URI;

        //ArrayList to store all movieId information
        ArrayList<Integer> movieId = new ArrayList<>();
        //Cursor Object to get resultSet from Database
        Cursor movieDataCursor = getContentResolver().query(movieContentUri,
                null, null, null, null);

        if (movieDataCursor.moveToFirst()) {
            do {
                int id = movieDataCursor.getInt(movieDataCursor.getColumnIndex(MovieDetailContract.MovieDetailEntry._ID));
                movieId.add(id);
            } while (movieDataCursor.moveToNext());
        } else
            return false;

        if(movieId.size() != 0) {
            for (int i = 0; i < movieId.size(); i++) {
                if (movieId.get(i) == Id)
                    return true;
            }
        }
        return false;
    }
}