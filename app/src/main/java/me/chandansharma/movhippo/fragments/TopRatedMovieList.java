package me.chandansharma.movhippo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.chandansharma.movhippo.R;
import me.chandansharma.movhippo.adapters.MovieListAdapter;
import me.chandansharma.movhippo.models.Movies;
import me.chandansharma.movhippo.utils.AppController;
import me.chandansharma.movhippo.utils.TmdbUrls;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedMovieList extends Fragment {

    //get the Tag of the class
    private static final String TAG = PopularMovieList.class.getSimpleName();

    //Make the ArrayList to store all movie's details
    ArrayList<Movies> mMoviesArrayList = new ArrayList<>();

    //references of the adapter and views
    RecyclerView mRecyclerView;
    MovieListAdapter mMovieListAdapter;
    GridLayoutManager mGridLayoutManager;

    //Counter Variable that count the item on screen
    private int visibleItemCounter;
    private int totalItemCounter;
    private int firstVisibleItem;
    private int previousTotalItem = 0;
    private int visibleThresholdItem = 3;
    private int pageCounter = 1;
    private boolean processingNewItem = true;
    String mMovieUrl;

    public TopRatedMovieList() {
        // Required empty public constructor
    }

    //to save app's instance
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mMoviesArrayList", mMoviesArrayList);
        outState.putInt("visibleItemCounter",visibleItemCounter);
        outState.putInt("totalItemCounter",totalItemCounter);
        outState.putInt("firstVisibleItem",firstVisibleItem);
        outState.putInt("previousTotalItem",previousTotalItem);
        outState.putInt("pageCounter",pageCounter);
        outState.putBoolean("processingNewItem",processingNewItem);
    }

    //restore app's instance
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mMoviesArrayList = savedInstanceState.getParcelableArrayList("mMoviesArrayList");
            visibleItemCounter = savedInstanceState.getInt("visibleItemCounter");
            totalItemCounter = savedInstanceState.getInt("totalItemCounter");
            firstVisibleItem = savedInstanceState.getInt("firstVisibleItem");
            previousTotalItem = savedInstanceState.getInt("previousTotalItem");
            pageCounter = savedInstanceState.getInt("pageCounter");
            processingNewItem = savedInstanceState.getBoolean("processingNewItem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_popular_movie_list, container, false);
        mMovieUrl = TmdbUrls.MOVIE_BASE_URL + TmdbUrls.TOP_RATED_TAG
                + "?" + TmdbUrls.API_KEY_TAG + "=" + TmdbUrls.API_KEY;

        getMovieListData(mMovieUrl);

        mMovieListAdapter = new MovieListAdapter(getActivity(), mMoviesArrayList);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((position + 1) % 4 == 0)
                    return 3;
                else
                    return 1;
            }
        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mMovieListAdapter);

        //set the scrollLister to recyclerView
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCounter = mRecyclerView.getChildCount();
                totalItemCounter = mGridLayoutManager.getItemCount();
                firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

                if (processingNewItem) {
                    if (totalItemCounter > previousTotalItem) {
                        processingNewItem = false;
                        previousTotalItem = totalItemCounter;
                        pageCounter++;
                    }
                }
                if (!processingNewItem && (totalItemCounter - visibleItemCounter) <= (firstVisibleItem + visibleThresholdItem)) {
                    Snackbar.make(mRecyclerView, "Loading More Movies...",
                            Snackbar.LENGTH_LONG).show();
                    mMovieUrl = TmdbUrls.MOVIE_BASE_URL + TmdbUrls.TOP_RATED_TAG
                            + "?" + TmdbUrls.API_KEY_TAG + "=" + TmdbUrls.API_KEY + "&" +
                            TmdbUrls.PAGE_OFFSET + "=" + String.valueOf(pageCounter);
                    getMovieListData(mMovieUrl);
                    processingNewItem = true;
                }
            }
        });
        return mRecyclerView;
    }

    private void getMovieListData(String mMovieUrl) {
        //Tag used to cancel the request
        String jsonObjectTag = "jsonObjectTag";

        JsonObjectRequest movieJsonObject = new JsonObjectRequest(Request.Method.GET, mMovieUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray mMovieResultArray = response.getJSONArray("results");
                            for (int i = 0; i < mMovieResultArray.length(); i++) {
                                JSONObject mMovieResultObject = mMovieResultArray.getJSONObject(i);

                                Movies singleMovieDetail = new Movies(mMovieResultObject.getInt("id"),
                                        mMovieResultObject.getString("title"),
                                        mMovieResultObject.getString("release_date"),
                                        "http://image.tmdb.org/t/p/w342/" + mMovieResultObject.getString("poster_path"),
                                        mMovieResultObject.getString("overview"));
                                mMoviesArrayList.add(singleMovieDetail);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mMovieListAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, error.getMessage());
                    }
                });

        //Adding request to the queue
        AppController.getInstance().addToRequestQueue(movieJsonObject, jsonObjectTag);
    }
}
