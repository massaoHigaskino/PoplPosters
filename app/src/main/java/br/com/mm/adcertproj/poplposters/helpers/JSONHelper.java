package br.com.mm.adcertproj.poplposters.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONHelper {

    // region PUBLIC METHODS
    public static JSONObject getJSONObject(String json) {
        JSONObject value = null;
        try {
            value = new JSONObject(json);
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }
        return value;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String name) {
        JSONArray value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getJSONArray(name);
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }
        return value;
    }
    // endregion

}
