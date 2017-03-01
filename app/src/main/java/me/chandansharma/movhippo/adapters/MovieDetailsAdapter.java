package me.chandansharma.movhippo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.chandansharma.movhippo.R;
import me.chandansharma.movhippo.models.MovieReviews;
import me.chandansharma.movhippo.models.MovieTrailers;

/**
 * Created by CS on 2/2/2017.
 */
public class MovieDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Tag for class
    private static final String TAG = MovieDetailsAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<MovieTrailers> mMovieTrailers;
    private ArrayList<Object> mMovieDetails;

    //to get Movie trailer Details or Movie Reviews Details
    public static final int TRAILER_TYPE = 0;
    public static final int REVIEW_TYPE = 1;

    public MovieDetailsAdapter(Context context, ArrayList<Object> movieDetails, ArrayList<MovieTrailers> movieTrailers) {
        mContext = context;
        mMovieDetails = movieDetails;
        mMovieTrailers = movieTrailers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRAILER_TYPE)
            return new MovieTrailerHolder(LayoutInflater.
                    from(mContext).inflate(R.layout.movie_trailer, parent, false));
        else
            return new MovieReviewHolder(LayoutInflater.
                    from(mContext).inflate(R.layout.movie_review, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TRAILER_TYPE)
            ((MovieTrailerHolder) holder).bindView(position);
        else
            ((MovieReviewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return mMovieDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mMovieTrailers.size()) {
            return TRAILER_TYPE;
        } else
            return REVIEW_TYPE;
    }

    private class MovieTrailerHolder extends RecyclerView.ViewHolder {

        //get the references of the all the views
        private ImageView mMovieTrailerImage;
        private TextView mMovieTrailerTitle;
        private TextView mMovieTrailerSite;
        private TextView mMovieTrailerQualityAndType;

        public MovieTrailerHolder(View itemView) {
            super(itemView);

            mMovieTrailerImage = (ImageView) itemView.findViewById(R.id.trailer_image_view);
            mMovieTrailerTitle = (TextView) itemView.findViewById(R.id.trailer_title);
            mMovieTrailerSite = (TextView) itemView.findViewById(R.id.trailer_site);
            mMovieTrailerQualityAndType = (TextView) itemView.findViewById(R.id.trailer_quality_and_type);
        }

        private void bindView(final int position) {
            Picasso.with(mContext).load("https://img.youtube.com/vi/" +
                    ((MovieTrailers) mMovieDetails.get(position)).getMovieTrailerKey() + "/mqdefault.jpg")
                    .into(mMovieTrailerImage);
            mMovieTrailerTitle.setText(((MovieTrailers) mMovieDetails.get(position)).getMovieTrailerTitle());
            mMovieTrailerSite.setText(((MovieTrailers) mMovieDetails.get(position)).getMovieTrailerSite());
            mMovieTrailerQualityAndType.setText(((MovieTrailers) mMovieDetails.get(position)).getMovieTrailerVideoQualitySize()
                    + ", " + ((MovieTrailers) mMovieDetails.get(position)).getMovieTrailerVideoType());
            mMovieTrailerTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Sansation-Bold.ttf"));
            mMovieTrailerSite.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"Sansation-Italic.ttf"));
            mMovieTrailerQualityAndType.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"Sansation-Italic.ttf"));

            mMovieTrailerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoUrlIntent = new Intent(Intent.ACTION_VIEW);
                    videoUrlIntent.setData(Uri.parse("https://m.youtube.com/watch?v=" +
                            ((MovieTrailers)mMovieDetails.get(position)).getMovieTrailerKey()));
                    videoUrlIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(videoUrlIntent);
                }
            });
        }
    }

    private class MovieReviewHolder extends RecyclerView.ViewHolder {

        private TextView mMovieReviewContent;
        private TextView mMovieReviewAuthor;

        public MovieReviewHolder(View itemView) {
            super(itemView);

            mMovieReviewContent = (TextView) itemView.findViewById(R.id.movie_review_content_text_view);
            mMovieReviewAuthor = (TextView) itemView.findViewById(R.id.movie_review_author_text_view);
        }

        private void bindView(int position) {
            mMovieReviewContent.setText(((MovieReviews) mMovieDetails.get(position)).getMovieReviewContent());
            mMovieReviewAuthor.setText(((MovieReviews) mMovieDetails.get(position)).getMovieReviewAuthor());
            mMovieReviewContent.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"Sansation-Italic.ttf"));
            mMovieReviewAuthor.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Sansation-Bold.ttf"));
        }
    }
}
