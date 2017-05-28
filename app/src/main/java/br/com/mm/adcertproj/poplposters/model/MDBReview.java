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

@DatabaseTable(tableName = "reviews")
public class MDBReview extends MDBAbstract implements Serializable {

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

    @SerializedName("author")
    @Expose
    @DatabaseField
    private String author;

    @SerializedName("content")
    @Expose
    @DatabaseField
    private String content;
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

    public static MDBReview[] listByMovieId(Context context, Integer id) throws SQLException {
        List<MDBReview> results = MDBReview.getDao(context, MDBReview.class).queryBuilder().where().eq("movieId", id).query();
        MDBReview[] resultsArray = null;
        if(results != null && !results.isEmpty()) {
            resultsArray = results.toArray(new MDBReview[results.size()]);
        }
        return resultsArray;
    }
    //endregion
}
