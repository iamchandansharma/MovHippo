package me.chandansharma.movhippo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.chandansharma.movhippo.R;
import me.chandansharma.movhippo.models.Movies;
import me.chandansharma.movhippo.movhippo.MovieDetailsActivity;

/**
 * Created by CS on 1/18/2017.
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Context of the application
    private Context mContext;
    private ArrayList<Movies> mMovieArrayList;
    //to get the which view is display either 3 item or one item views
    public static final int THREE_VIEWS = 0;
    public static final int ONE_VIEW = 1;


    public MovieListAdapter(Context context, ArrayList<Movies> movieArrayList) {
        mContext = context;
        mMovieArrayList = movieArrayList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == THREE_VIEWS)
            return new MovieThreeListItemHolder(LayoutInflater
                    .from(mContext).inflate(R.layout.movie_list_three_item, parent, false));
        else
            return new MovieOneListItemHolder(LayoutInflater
                    .from(mContext).inflate(R.layout.movie_list_one_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == THREE_VIEWS)
            ((MovieThreeListItemHolder) holder).bindView(position);
        else
            ((MovieOneListItemHolder) holder).bindView(position);
    }

    @Override
    public int getItemViewType(int position) {
        if((position+1) % 4 == 0)
            return ONE_VIEW;
        else
            return THREE_VIEWS;
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }

    private class MovieOneListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //get the references of the all the views
        private ImageView mMovieImage;
        private TextView mMovieTitle;
        private TextView mMovieReleaseDate;
        private TextView mMovieOverView;
        private int mMovieIndex;

        public MovieOneListItemHolder(View itemView) {
            super(itemView);
            mMovieImage = (ImageView) itemView.findViewById(R.id.movie_image_view);
            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title_text_view);
            mMovieReleaseDate = (TextView) itemView.findViewById(R.id.movie_release_date_text_view);
            mMovieOverView = (TextView) itemView.findViewById(R.id.movie_overview_text_view);
            ((TextView)itemView.findViewById(R.id.continue_reading_text_view))
                    .setTypeface(Typeface.createFromAsset(mContext.getAssets(),"Sansation-Italic.ttf"));

            itemView.findViewById(R.id.continue_reading_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set the intent to the MovieDetails Activity
                    Intent movieDetailIntent = new Intent(mContext, MovieDetailsActivity.class);
                    movieDetailIntent.putExtra(Intent.EXTRA_TEXT,String
                            .valueOf(mMovieArrayList.get(mMovieIndex).getMovieId()));
                    mContext.startActivity(movieDetailIntent);
                }
            });
            mMovieImage.setOnClickListener(this);
        }

        private void bindView(int position) {
            //set position of the movie item
            mMovieIndex = position;

            //set the movie details to views
            Picasso.with(mContext).load(mMovieArrayList.get(position).getMoviePosterUrl())
                    .into(mMovieImage);
            mMovieTitle.setText(mMovieArrayList.get(position).getMovieTitle());
            mMovieReleaseDate.setText(mMovieArrayList.get(position).getMovieReleaseDate());
            mMovieOverView.setText(mMovieArrayList.get(position).getMovieOverView());

            mMovieTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Sansation-Bold.ttf"));
            mMovieReleaseDate.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"Sansation-Bold.ttf"));
            mMovieOverView.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"Sansation-Italic.ttf"));
        }
        @Override
        public void onClick(View v) {
            //set the intent to the MovieDetails Activity
            Intent movieDetailIntent = new Intent(mContext, MovieDetailsActivity.class);
            movieDetailIntent.putExtra(Intent.EXTRA_TEXT,String
                    .valueOf(mMovieArrayList.get(mMovieIndex).getMovieId()));
            mContext.startActivity(movieDetailIntent);
        }

    }
    private class MovieThreeListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //get the references of the all the views
        private ImageView mMovieImage;
        private TextView mMovieTitle;
        private TextView mMovieReleaseDate;
        private int mMovieIndex;

        public MovieThreeListItemHolder(View itemView) {
            super(itemView);
            mMovieImage = (ImageView) itemView.findViewById(R.id.movie_image_view);
            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title_text_view);
            mMovieReleaseDate = (TextView) itemView.findViewById(R.id.movie_release_date_text_view);

            mMovieImage.setOnClickListener(this);
        }

        private void bindView(int position) {
            //set position of the movie item
            mMovieIndex = position;

            //set the movie details to views
            Picasso.with(mContext).load(mMovieArrayList.get(position).getMoviePosterUrl())
                    .into(mMovieImage);
            mMovieTitle.setText(mMovieArrayList.get(position).getMovieTitle());
            mMovieReleaseDate.setText(mMovieArrayList.get(position).getMovieReleaseDate().substring(0,4));
            mMovieTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Sansation-Bold.ttf"));
            mMovieReleaseDate.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"Sansation-Bold.ttf"));
        }

        @Override
        public void onClick(View v) {
            //set the intent to the MovieDetails Activity
            Intent movieDetailIntent = new Intent(mContext, MovieDetailsActivity.class);
            movieDetailIntent.putExtra(Intent.EXTRA_TEXT,String
                    .valueOf(mMovieArrayList.get(mMovieIndex).getMovieId()));
            mContext.startActivity(movieDetailIntent);
        }
    }
}
