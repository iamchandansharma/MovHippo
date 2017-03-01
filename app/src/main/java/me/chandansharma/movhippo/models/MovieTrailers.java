package me.chandansharma.movhippo.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CS on 2/2/2017.
 */
public class MovieTrailers implements Parcelable {

    //Creator
    public static final Creator CREATOR = new Creator() {
        @Override
        public MovieTrailers createFromParcel(Parcel source) {
            return new MovieTrailers(source);
        }

        @Override
        public MovieTrailers[] newArray(int size) {
            return new MovieTrailers[size];
        }
    };

    //All the filed inorder to store all movie trailer information
    private String mMovieTrailerKey;
    private String mMovieTrailerTitle;
    private String mMovieTrailerSite;
    private String mMovieTrailerVideoQualitySize;
    private String mMovieTrailerVideoType;

    //constructor to initialized data
    public MovieTrailers(String mMovieTrailerKey, String mMovieTrailerTitle, String mMovieTrailerSite ,
                         String mMovieTrailerVideoQualitySize, String mMovieTrailerVideoType){

        this.mMovieTrailerKey = mMovieTrailerKey;
        this.mMovieTrailerTitle = mMovieTrailerTitle;
        this.mMovieTrailerSite = mMovieTrailerSite;
        this.mMovieTrailerVideoQualitySize = mMovieTrailerVideoQualitySize;
        this.mMovieTrailerVideoType = mMovieTrailerVideoType;
    }

    public MovieTrailers(Parcel input){
        String[] movieTrailerData = new String[5];
        input.readStringArray(movieTrailerData);

        this.mMovieTrailerKey = movieTrailerData[0];
        this.mMovieTrailerTitle = movieTrailerData[1];
        this.mMovieTrailerSite = movieTrailerData[2];
        this.mMovieTrailerVideoQualitySize = movieTrailerData[3];
        this.mMovieTrailerVideoType = movieTrailerData[4];
    }

    public String getMovieTrailerKey() {
        return mMovieTrailerKey;
    }

    public void setMovieTrailerKey(String movieTrailerKey) {
        mMovieTrailerKey = movieTrailerKey;
    }

    public String getMovieTrailerTitle() {
        return mMovieTrailerTitle;
    }

    public void setMovieTrailerTitle(String movieTrailerTitle) {
        mMovieTrailerTitle = movieTrailerTitle;
    }

    public String getMovieTrailerSite() {
        return mMovieTrailerSite;
    }

    public void setMovieTrailerSite(String movieTrailerSite) {
        mMovieTrailerSite = movieTrailerSite;
    }

    public String getMovieTrailerVideoQualitySize() {
        return mMovieTrailerVideoQualitySize;
    }

    public void setMovieTrailerVideoQualitySize(String movieTrailerVideoQualitySize) {
        mMovieTrailerVideoQualitySize = movieTrailerVideoQualitySize;
    }

    public String getMovieTrailerVideoType() {
        return mMovieTrailerVideoType;
    }

    public void setMovieTrailerVideoType(String movieTrailerVideoType) {
        mMovieTrailerVideoType = movieTrailerVideoType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mMovieTrailerKey,
                this.mMovieTrailerTitle,
                this.mMovieTrailerSite,
                this.mMovieTrailerVideoQualitySize,
                this.mMovieTrailerVideoType
        });
    }
}
