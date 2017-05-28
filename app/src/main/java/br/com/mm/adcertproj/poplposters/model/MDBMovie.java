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
import java.util.Date;

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

    @SerializedName("adult")
    @Expose
    @DatabaseField
    private Boolean adult;

    @SerializedName("overview")
    @Expose
    @DatabaseField
    private String overview;

    private Date releaseDate;

    @SerializedName("release_date")
    @Expose
    @DatabaseField
    private String releaseDateString;

    @SerializedName("genre_ids")
    @Expose
    private Integer[] genreIds;

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private Integer id;

    @SerializedName("original_title")
    @Expose
    @DatabaseField
    private String originalTitle;

    @SerializedName("original_language")
    @Expose
    @DatabaseField
    private String originalLanguage;

    @SerializedName("title")
    @Expose
    @DatabaseField
    private String title;

    @SerializedName("backdrop_path")
    @Expose
    @DatabaseField
    private String backdropPath;

    @SerializedName("popularity")
    @Expose
    @DatabaseField
    private Double popularity;

    @SerializedName("vote_count")
    @Expose
    @DatabaseField
    private Integer voteCount;

    @SerializedName("video")
    @Expose
    @DatabaseField
    private Boolean video;

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

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDateString() {
        return releaseDateString;
    }

    public void setReleaseDateString(String releaseDateString) {
        this.releaseDateString = releaseDateString;
    }

    public Integer[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(Integer[] genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
    // endregion

    // region PUBLIC METHODS
    public static MDBMovie[] listFromGson(String json) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(MDBMovie[].class, new MDBDeserializer<MDBMovie[]>(MDM_RESULTS))
                .create();
        return gson.fromJson(json, MDBMovie[].class);
    }

    public boolean idExists(Context context) throws SQLException {
        return getDao(context).idExists(getId());
    }
    // endregion
}
