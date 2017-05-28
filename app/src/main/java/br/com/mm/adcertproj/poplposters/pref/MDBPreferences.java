package br.com.mm.adcertproj.poplposters.pref;

import android.net.Uri;

import br.com.mm.adcertproj.poplposters.BuildConfig;

public class MDBPreferences {

    // region ATTRIBUTES
    public static final String POPULAR_MOVIES_URL = "http://api.themoviedb.org/3/movie/";
    public static final String SORT_POPULAR = "popular";
    public static final String SORT_TOP_RATED = "top_rated";
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String[] POSTER_SIZES = {"w92/", "w154/", "w185/", "w342/", "w500/", "w780/", "original/"};
    public static final int POSTER_DEFAULT_SIZE_IDX = 2;
    public static final String YOUTUBE_WATCH_URL = "http://www.youtube.com/watch";
    private static String valueApiKey;
    private static String sortType = SORT_POPULAR;

    static {
        valueApiKey = BuildConfig.MOVIE_DB_PARAM_API_KEY[1];
    }
    // endregion

    // region GETTERS & SETTERS
    public static String getValueApiKey() {
        return valueApiKey;
    }

    public static void setValueApiKey(String valueApiKey) {
        if(BuildConfig.DEBUG) {
            MDBPreferences.valueApiKey = valueApiKey;
        }
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

    /**
     * Builds an Uri, which should be used through an Intent to start up the YouTube app.
     *
     * @param key YouTube v key corresponding to the video to be watched.
     * @return Uri built as a YouTube URL.
     */
    public static Uri buildYouTubeUri(String key) {
        return Uri.parse(YOUTUBE_WATCH_URL).buildUpon().appendQueryParameter("v", key).build();
    }
    //endregion
}
