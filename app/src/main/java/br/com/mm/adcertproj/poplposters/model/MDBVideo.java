package br.com.mm.adcertproj.poplposters.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@DatabaseTable(tableName = "videos")
public class MDBVideo extends MDBAbstract implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;

    /**
     * Attribute name of the movie list, which is returned nested into the json response.
     */
    public static final String MDM_RESULTS = "results";

    @DatabaseField
    private Integer movieId;

    @DatabaseField(generatedId = true)
    private Integer id;

    @SerializedName("key")
    @Expose
    @DatabaseField
    private String key;

    @SerializedName("name")
    @Expose
    @DatabaseField
    private String name;
    //endregion

    // region GETTERS & SETTERS
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public static MDBVideo[] listByMovieId(Context context, Integer id) throws SQLException {
        List<MDBVideo> results = MDBVideo.getDao(context, MDBVideo.class).queryBuilder().where().eq("movieId", id).query();
        MDBVideo[] resultsArray = null;
        if(results != null && !results.isEmpty()) {
            resultsArray = results.toArray(new MDBVideo[results.size()]);
        }
        return resultsArray;
    }
    //endregion
}
