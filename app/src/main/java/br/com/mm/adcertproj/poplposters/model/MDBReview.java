package br.com.mm.adcertproj.poplposters.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MDBReview implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;

    /**
     * Attribute name of the movie list, which is returned nested into the json response.
     */
    public static final String MDM_RESULTS = "results";

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("content")
    @Expose
    private String content;
    //endregion

    // region GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    //endregion

    // region PUBLIC METHODS
    public static MDBReview[] listFromGson(String json) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(MDBReview[].class, new MDBDeserializer<MDBReview[]>(MDM_RESULTS))
                .create();
        return gson.fromJson(json, MDBReview[].class);
    }
    //endregion
}
