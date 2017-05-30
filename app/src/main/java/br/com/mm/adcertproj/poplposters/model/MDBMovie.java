package br.com.mm.adcertproj.poplposters.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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
    public static final String TABLE_NAME = "favorite_movies";
    public static final String ID_COLUMN = "id";
    public static final String TITLE_COLUMN = "title";
    public static final String VOTE_AVE_COLUMN = "voteAverage";
    public static final String REL_DATE_COLUMN = "releaseDateString";
    public static final String OVERVIEW_COLUMN = "overview";
    public static final String POSTER_COLUMN = "posterPath";

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

    public MDBMovie(Integer id, String title, Double voteAverage, String releaseDateString,
                    String overview, String posterPath) {
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
        this.releaseDateString = releaseDateString;
        this.overview = overview;
        this.posterPath = posterPath;
    }

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

    public MDBMovie[] listFromCursor(Cursor cursor) {
        if(cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        MDBMovie[] movies = new MDBMovie[cursor.getCount()];

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Integer id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            String title = cursor.getString(cursor.getColumnIndex(TITLE_COLUMN));
            Double voteAverage = cursor.getDouble(cursor.getColumnIndex(VOTE_AVE_COLUMN));
            String releaseDateString = cursor.getString(cursor.getColumnIndex(REL_DATE_COLUMN));
            String overview = cursor.getString(cursor.getColumnIndex(OVERVIEW_COLUMN));
            String posterPath = cursor.getString(cursor.getColumnIndex(POSTER_COLUMN));
            movies[i] = new MDBMovie(id, title, voteAverage, releaseDateString, overview, posterPath);
        }

        return movies;
    }

    public ContentValues createContentValues() {
        ContentValues contentValues = new ContentValues(6);
        contentValues.put(ID_COLUMN, id);
        contentValues.put(TITLE_COLUMN, title);
        contentValues.put(VOTE_AVE_COLUMN, voteAverage);
        contentValues.put(REL_DATE_COLUMN, releaseDateString);
        contentValues.put(OVERVIEW_COLUMN, overview);
        contentValues.put(POSTER_COLUMN, posterPath);
        return contentValues;
    }
    // endregion
}
