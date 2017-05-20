package br.com.mm.adcertproj.poplposters.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

import br.com.mm.adcertproj.poplposters.helpers.JSONHelper;

public class MDBVideo implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;

    private static final String MDM_ID = "id";
    private static final String MDM_RESULTS = "results";
    private static final String MDM_KEY = "key";
    private static final String MDM_NAME = "name";

    private String id;
    private String key;
    private String name;
    //endregion

    // region GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    // region PUBLIC METHODS
    public static MDBVideo[] listFromJSON(String json) {
        MDBVideo[] videosArray = null;
        JSONObject mdbJson = JSONHelper.getJSONObject(json);
        if(mdbJson != null) {
            JSONArray videosJson = JSONHelper.getJSONArray(mdbJson, MDM_RESULTS);
            if(videosJson != null && videosJson.length() > 0) {
                videosArray = new MDBVideo[videosJson.length()];
                for(int i = 0; i < videosJson.length(); i++) {
                    JSONObject videoJson = videosJson.optJSONObject(i);
                    MDBVideo video = null;
                    if(videoJson != null) {
                        video = new MDBVideo();
                        video.setId(videoJson.optString(MDM_ID));
                        video.setKey(videoJson.optString(MDM_KEY));
                        video.setName(videoJson.optString(MDM_NAME));
                    }
                    videosArray[i] = video;
                }
            }
        }
        return videosArray;
    }
    //endregion
}
