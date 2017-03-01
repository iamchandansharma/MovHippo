package me.chandansharma.movhippo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CS on 2/2/2017.
 */
public class MovieReviews implements Parcelable {

    //Creator
    public static final Creator CREATOR = new Creator() {
        @Override
        public MovieReviews createFromParcel(Parcel source) {
            return new MovieReviews(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new MovieReviews[size];
        }
    };
    private String mMovieReviewAuthor;
    private String mMovieReviewContent;

    public MovieReviews(Parcel input){
        String[] movieReviewData = new String[2];
        input.readStringArray(movieReviewData);

        this.mMovieReviewContent = movieReviewData[0];
        this.mMovieReviewAuthor = movieReviewData[1];
    }

    public MovieReviews(String mMovieReviewAuthor, String mMovieReviewContent){
        this.mMovieReviewAuthor = mMovieReviewAuthor;
        this.mMovieReviewContent = mMovieReviewContent;
    }

    public String getMovieReviewAuthor() {
        return mMovieReviewAuthor;
    }

    public void setMovieReviewAuthor(String movieReviewAuthor) {
        mMovieReviewAuthor = movieReviewAuthor;
    }

    public String getMovieReviewContent() {
        return mMovieReviewContent;
    }

    public void setMovieReviewContent(String movieReviewContent) {
        mMovieReviewContent = movieReviewContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mMovieReviewContent,
                this.mMovieReviewAuthor
        });
    }
}
