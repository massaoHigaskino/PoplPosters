package br.com.mm.adcertproj.poplposters.pref;

import br.com.mm.adcertproj.poplposters.BuildConfig;

public class MDBPreferences {
    private static String paramApiKey;
    private static String valueApiKey;

    static {
        paramApiKey = BuildConfig.MOVIE_DB_PARAM_API_KEY[0];
        valueApiKey = BuildConfig.MOVIE_DB_PARAM_API_KEY[1];
    }

    public static String getParamApiKey() {
        return paramApiKey;
    }

    public static void setParamApiKey(String paramApiKey) {
        MDBPreferences.paramApiKey = paramApiKey;
    }

    public static String getValueApiKey() {
        return valueApiKey;
    }

    public static void setValueApiKey(String valueApiKey) {
        MDBPreferences.valueApiKey = valueApiKey;
    }
}
