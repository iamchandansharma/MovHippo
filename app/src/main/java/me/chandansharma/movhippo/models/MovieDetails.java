package me.chandansharma.movhippo.models;

/**
 * Created by CS on 2/4/2017.
 */
public class MovieDetails {

    //All the member variable that are used to store the data
    private int mMovieId;
    private String mMovieTitle;
    private String mMovieTagLine;
    private String mMovieReleaseDate;
    private String mMovieRunTime;
    private String mMovieVoteAverage;
    private String mMovieVoteCount;
    private String mMoviePopularity;
    private String mMovieLanguage;
    private String mMovieOverView;
    private String mMoviePosterUrl;
    private String mMovieBackDropImageUrl;

    public MovieDetails() {
    }

    public MovieDetails(int mMovieId, String mMovieTitle, String mMovieTagLine, String mMovieReleaseDate,
                        String mMovieRunTime, String mMovieVoteAverage, String mMovieVoteCount,
                        String mMoviePopularity, String mMovieLanguage, String mMovieOverView,
                        String mMoviePosterUrl, String mMovieBackDropImageUrl) {

        this.mMovieId = mMovieId;
        this.mMovieTitle = mMovieTitle;
        this.mMovieTagLine = mMovieTagLine;
        this.mMovieReleaseDate = mMovieReleaseDate;
        this.mMovieRunTime = mMovieRunTime;
        this.mMovieVoteAverage = mMovieVoteAverage;
        this.mMovieVoteCount = mMovieVoteCount;
        this.mMoviePopularity = mMoviePopularity;
        this.mMovieLanguage = mMovieLanguage;
        this.mMovieOverView = mMovieOverView;
        this.mMoviePosterUrl = mMoviePosterUrl;
        this.mMovieBackDropImageUrl = mMovieBackDropImageUrl;
    }

    public String getMovieBackDropImageUrl() {
        return mMovieBackDropImageUrl;
    }

    public void setMovieBackDropImageUrl(String movieBackDropImageUrl) {
        mMovieBackDropImageUrl = movieBackDropImageUrl;
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

    public String getMovieTagLine() {
        return mMovieTagLine;
    }

    public void setMovieTagLine(String movieTagLine) {
        mMovieTagLine = movieTagLine;
    }

    public String getMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        mMovieReleaseDate = movieReleaseDate;
    }

    public String getMovieRunTime() {
        return mMovieRunTime;
    }

    public void setMovieRunTime(String movieRunTime) {
        mMovieRunTime = movieRunTime;
    }

    public String getMovieVoteAverage() {
        return mMovieVoteAverage;
    }

    public void setMovieVoteAverage(String movieVoteAverage) {
        mMovieVoteAverage = movieVoteAverage;
    }

    public String getMovieVoteCount() {
        return mMovieVoteCount;
    }

    public void setMovieVoteCount(String movieVoteCount) {
        mMovieVoteCount = movieVoteCount;
    }

    public String getMoviePopularity() {
        return mMoviePopularity;
    }

    public void setMoviePopularity(String moviePopularity) {
        mMoviePopularity = moviePopularity;
    }

    public String getMovieLanguage() {
        return mMovieLanguage;
    }

    public void setMovieLanguage(String movieLanguage) {
        mMovieLanguage = movieLanguage;
    }

    public String getMovieOverView() {
        return mMovieOverView;
    }

    public void setMovieOverView(String movieOverView) {
        mMovieOverView = movieOverView;
    }

    public String getMoviePosterUrl() {
        return mMoviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        mMoviePosterUrl = moviePosterUrl;
    }
}
