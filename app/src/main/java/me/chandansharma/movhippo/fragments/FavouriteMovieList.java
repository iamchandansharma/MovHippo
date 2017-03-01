package me.chandansharma.movhippo.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.chandansharma.movhippo.R;
import me.chandansharma.movhippo.adapters.MovieListAdapter;
import me.chandansharma.movhippo.data.MovieDetailContract.MovieDetailEntry;
import me.chandansharma.movhippo.models.Movies;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteMovieList extends Fragment {

    //get the tag of the class
    private static final String TAG = FavouriteMovieList.class.getSimpleName();
    private ArrayList<Movies> mMoviesArrayList;

    //references of the adapter and views
    RecyclerView mRecyclerView;
    MovieListAdapter mMovieListAdapter;
    GridLayoutManager mGridLayoutManager;

    public FavouriteMovieList() {
        // Required empty public constructor
    }

    //to save app's instance
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mMoviesArrayList", mMoviesArrayList);
    }

    //restore app's instance
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mMoviesArrayList = savedInstanceState.getParcelableArrayList("mMoviesArrayList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_favourite_movie_list,container,false);

        mMoviesArrayList = new ArrayList<>();
        //get the MovieList data from DataBase
        getMovieListData();

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
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mMoviesArrayList.clear();
        getMovieListData();
        mMovieListAdapter.notifyDataSetChanged();
    }

    private void getMovieListData() {

        //Uri for requesting data from Database
        Uri movieContentUri = MovieDetailEntry.CONTENT_URI;

        //Cursor Object to get resultSet from Database
        Cursor movieDataCursor = getContext().getContentResolver().query(movieContentUri,
                null,null,null,null);

        if(movieDataCursor.moveToFirst()){
            do{
                Movies singleMovieDetail = new Movies(movieDataCursor.getInt(movieDataCursor.getColumnIndex(MovieDetailEntry._ID)),
                        movieDataCursor.getString(movieDataCursor.getColumnIndex(MovieDetailEntry.COLUMN_MOVIE_NAME)),
                        movieDataCursor.getString(movieDataCursor.getColumnIndex(MovieDetailEntry.COLUMN_MOVIE_RELEASE_DATE)),
                        movieDataCursor.getString(movieDataCursor.getColumnIndex(MovieDetailEntry.COLUMN_MOVIE_POSTER)),
                        movieDataCursor.getString(movieDataCursor.getColumnIndex(MovieDetailEntry.COLUMN_MOVIE_OVERVIEW))
                );
                mMoviesArrayList.add(singleMovieDetail);
            }while (movieDataCursor.moveToNext());
        }
        movieDataCursor.close();
    }
}
