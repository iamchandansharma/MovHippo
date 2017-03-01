package me.chandansharma.movhippo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CS on 1/17/2017.
 */
public class Movies implements Parcelable {

    //All the member variable that are used to store the data;
    private int mMovieId;
    private String mMovieTitle;
    private String mMovieReleaseDate;
    private String mMoviePosterUrl;
    private String mMovieOverView;

    //Creator
    public static final Creator CREATOR = new Creator() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public Movies(int mMovieId, String mMovieTitle, String mMovieReleaseDate,
                  String mMoviePosterUrl, String mMovieOverView) {
        this.mMovieId = mMovieId;
        this.mMovieTitle = mMovieTitle;
        this.mMovieReleaseDate = mMovieReleaseDate;
        this.mMoviePosterUrl = mMoviePosterUrl;
        this.mMovieOverView = mMovieOverView;
    }

    public Movies(Parcel input){
        String[] movieData = new String[5];

        input.readStringArray(movieData);
        this.mMovieId = Integer.parseInt(movieData[0]);
        this.mMovieTitle = movieData[1];
        this.mMovieReleaseDate = movieData[2];
        this.mMoviePosterUrl = movieData[3];
        this.mMovieOverView = movieData[4];
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        mMovieTitle = movieTitle;
    }

    public String getMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        mMovieReleaseDate = movieReleaseDate;
    }

    public String getMoviePosterUrl() {
        return mMoviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        mMoviePosterUrl = moviePosterUrl;
    }

    public String getMovieOverView() {
        return mMovieOverView;
    }

    public void setMovieOverView(String movieOverView) {
        mMovieOverView = movieOverView;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                String.valueOf(this.mMovieId),
                this.mMovieTitle,
                this.mMovieReleaseDate,
                this.mMoviePosterUrl,
                this.mMovieOverView
        });
    }
}
