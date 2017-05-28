package br.com.mm.adcertproj.poplposters.model;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;

@DatabaseTable(tableName = "favorite_movies")
public class MDBMovie extends MDBAbstract implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;

    /**
     * Attribute name of the movie list, which is returned nested into the json response.
     */
    public static final String MDM_RESULTS = "results";

    @SerializedName("poster_path")
    @Expose
    @DatabaseField
    private String posterPath;

    @SerializedName("overview")
    @Expose
    @DatabaseField
    private String overview;

    @SerializedName("release_date")
    @Expose
    @DatabaseField
    private String releaseDateString;

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private Integer id;

    @SerializedName("title")
    @Expose
    @DatabaseField
    private String title;

    @SerializedName("vote_average")
    @Expose
    @DatabaseField
    private Double voteAverage;
    // endregion

    /**
     * ORMLite required constructor.
     */
    public MDBMovie() {}

    // region GETTERS & SETTERS
    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDateString() {
        return releaseDateString;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
    // endregion

    // region PUBLIC METHODS
    public boolean idExists(Context context) throws SQLException {
        return getDao(context).idExists(getId());
    }
    // endregion
}
