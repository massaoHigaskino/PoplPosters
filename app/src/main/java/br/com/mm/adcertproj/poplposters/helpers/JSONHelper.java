package br.com.mm.adcertproj.poplposters.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

public class JSONHelper {

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

    public static Integer getInt(JSONObject jsonObject, String name) {
        Integer value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getInt(name);
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }
        return value;
    }

    public static Integer[] getIntArray(JSONObject jsonObject, String name) {
        Integer[] value = null;

        try {
            if (jsonObject.has(name)) {
                JSONArray jsonArray = jsonObject.getJSONArray(name);
                value = new Integer[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    value[i] = jsonArray.getInt(i);
                }
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }

        return value;
    }

    public static Boolean getBoolean(JSONObject jsonObject, String name) {
        Boolean value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getBoolean(name);
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }
        return value;
    }

    public static Boolean[] getBooleanArray(JSONObject jsonObject, String name) {
        Boolean[] value = null;

        try {
            if (jsonObject.has(name)) {
                JSONArray jsonArray = jsonObject.getJSONArray(name);
                value = new Boolean[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    value[i] = jsonArray.getBoolean(i);
                }
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }

        return value;
    }

    public static Double getDouble(JSONObject jsonObject, String name) {
        Double value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getDouble(name);
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }
        return value;
    }

    public static Double[] getDoubleArray(JSONObject jsonObject, String name) {
        Double[] value = null;

        try {
            if (jsonObject.has(name)) {
                JSONArray jsonArray = jsonObject.getJSONArray(name);
                value = new Double[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    value[i] = jsonArray.getDouble(i);
                }
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }

        return value;
    }

    public static Long getLong(JSONObject jsonObject, String name) {
        Long value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getLong(name);
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }
        return value;
    }

    public static Long[] getLongArray(JSONObject jsonObject, String name) {
        Long[] value = null;

        try {
            if (jsonObject.has(name)) {
                JSONArray jsonArray = jsonObject.getJSONArray(name);
                value = new Long[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    value[i] = jsonArray.getLong(i);
                }
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }

        return value;
    }

    public static String getString(JSONObject jsonObject, String name) {
        String value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getString(name);
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }
        return value;
    }

    public static String[] getStringArray(JSONObject jsonObject, String name) {
        String[] value = null;

        try {
            if (jsonObject.has(name)) {
                JSONArray jsonArray = jsonObject.getJSONArray(name);
                value = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    value[i] = jsonArray.getString(i);
                }
            }
        } catch (Throwable t) {
            Log.e(JSONHelper.class.getName(), t.getMessage());
        }

        return value;
    }

    public static Date getDate(JSONObject jsonObject, String name) {
        // TODO implement getDate using US format
        return null;
    }
}
