package br.com.mm.adcertproj.poplposters.pref;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import br.com.mm.adcertproj.poplposters.BuildConfig;

public class MDBPreferences {

    // region ATTRIBUTES
    public static final String POPULAR_MOVIES_URL = "http://api.themoviedb.org/3/movie/";
    public static final String SORT_POPULAR = "popular";
    public static final String SORT_TOP_RATED = "top_rated";
    public static final String MOVIE_VIDEO_SUFIX_FORMAT = "{0}/videos";
    public static final String MOVIE_REVIEW_SUFIX_FORMAT = "{0}/reviews";
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String[] POSTER_SIZES = {"w92/", "w154/", "w185/", "w342/", "w500/", "w780/", "original/"};
    public static final int POSTER_DEFAULT_SIZE_IDX = 2;
    public static final String YOUTUBE_WATCH_URL = "http://www.youtube.com/watch";
    private static String paramApiKey;
    private static String valueApiKey;
    private static String sortType = SORT_POPULAR;

    static {
        paramApiKey = BuildConfig.MOVIE_DB_PARAM_API_KEY[0];
        valueApiKey = BuildConfig.MOVIE_DB_PARAM_API_KEY[1];
    }
    // endregion

    // region GETTERS & SETTERS
    public static String getParamApiKey() {
        return paramApiKey;
    }

    public static String getValueApiKey() {
        return valueApiKey;
    }

    public static void setValueApiKey(String valueApiKey) {
        MDBPreferences.valueApiKey = valueApiKey;
    }

    public static String getSortType() {
        return MDBPreferences.sortType;
    }

    public static void setSortType(String sortType) {
        MDBPreferences.sortType = sortType;
    }
    // endregion

    // region PUBLIC METHODS
    public static String buildPosterUrl(String posterPath) {
        return POSTER_BASE_URL + POSTER_SIZES[POSTER_DEFAULT_SIZE_IDX] + posterPath;
    }

    public static URL buildPopMoviesQueryUrl() {
        Uri popMoviesQueryUri = Uri.parse(POPULAR_MOVIES_URL + sortType).buildUpon()
                .appendQueryParameter(paramApiKey, valueApiKey).build();
        return generateFromUri(popMoviesQueryUri);
    }

    /**
     * Builds an URL to list a movie's videos. Will be listed in a "results" key, including "key" and "name"
     * @param movieId
     * @return
     */
    public static URL buildMovieVideosQueryUrl(String movieId) {
        String videosQrySufix = MessageFormat.format(MOVIE_VIDEO_SUFIX_FORMAT, movieId);
        Uri movieVideosQueryUri = Uri.parse(POPULAR_MOVIES_URL + videosQrySufix).buildUpon()
                .appendQueryParameter(paramApiKey, valueApiKey).build();
        return generateFromUri(movieVideosQueryUri);
    }

    /**
     * Builds an URL to list a movie's reviews. Will be listed in a "results" key, including "author" and "content"
     * @param movieId
     * @return
     */
    public static URL buildMovieReviewsQueryUrl(String movieId) {
        String videosQrySufix = MessageFormat.format(MOVIE_REVIEW_SUFIX_FORMAT, movieId);
        Uri movieVideosQueryUri = Uri.parse(POPULAR_MOVIES_URL + videosQrySufix).buildUpon()
                .appendQueryParameter(paramApiKey, valueApiKey).build();
        return generateFromUri(movieVideosQueryUri);
    }

    /**
     * Builds an Uri, which should be used through an Intent to start up the YouTube app.
     *
     * @param key YouTube v key corresponding to the video to be watched.
     * @return Uri built as a YouTube URL.
     */
    // TODO Use startActivity(new Intent(Intent.ACTION_VIEW, buildYouTubeUri("key")));
    public static Uri buildYouTubeUri(String key) {
        return Uri.parse(YOUTUBE_WATCH_URL).buildUpon().appendQueryParameter("v", key).build();
    }
    //endregion

    //region PRIVATE METHODS
    public static URL generateFromUri(Uri input) {
        URL output = null;
        try {
            output = new URL(input.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return output;
    }
    //endregion
}
