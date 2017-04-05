package br.com.mm.adcertproj.poplposters.pref;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.mm.adcertproj.poplposters.BuildConfig;

public class MDBPreferences {
    public static final String POPULAR_MOVIES_URL = "http://api.themoviedb.org/3/movie/popular";
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String[] POSTER_SIZES = {"w92/", "w154/", "w185/", "w342/", "w500/", "w780/", "original/"};
    public static final int POSTER_DEFAULT_SIZE_IDX = 2;
    private static String paramApiKey;
    private static String valueApiKey;

    static {
        paramApiKey = BuildConfig.MOVIE_DB_PARAM_API_KEY[0];
        valueApiKey = BuildConfig.MOVIE_DB_PARAM_API_KEY[1];
    }

    public static String getParamApiKey() {
        return paramApiKey;
    }

//    public static void setParamApiKey(String paramApiKey) {
//        MDBPreferences.paramApiKey = paramApiKey;
//    }

    public static String getValueApiKey() {
        return valueApiKey;
    }

    public static void setValueApiKey(String valueApiKey) {
        MDBPreferences.valueApiKey = valueApiKey;
    }

    public static String buildPosterUrl(String posterPath) {
        return POSTER_BASE_URL + POSTER_SIZES[POSTER_DEFAULT_SIZE_IDX] + posterPath;
    }

    public static URL buildPopMoviesQueryUrl() {
        Uri popMoviesQueryUri = Uri.parse(POPULAR_MOVIES_URL).buildUpon()
                .appendQueryParameter(paramApiKey, valueApiKey).build();
        URL popMoviesQueryUrl = null;
        try {
            popMoviesQueryUrl = new URL(popMoviesQueryUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return popMoviesQueryUrl;
    }
}
