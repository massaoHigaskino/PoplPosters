package br.com.mm.adcertproj.poplposters.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MDBVideo implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;

    /**
     * Attribute name of the movie list, which is returned nested into the json response.
     */
    public static final String MDM_RESULTS = "results";

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("name")
    @Expose
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
    public static MDBVideo[] listFromGson(String json) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(MDBVideo[].class, new MDBDeserializer<MDBVideo[]>(MDM_RESULTS))
                .create();
        return gson.fromJson(json, MDBVideo[].class);
    }
    //endregion
}
