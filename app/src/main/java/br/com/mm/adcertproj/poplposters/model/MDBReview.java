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
import java.util.List;

@DatabaseTable(tableName = "reviews")
public class MDBReview extends MDBAbstract implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;
    public static final String TABLE_NAME = "reviews";
    public static final String ID_COLUMN = "id";
    public static final String MOVIE_ID_COLUMN = "movieId";
    public static final String AUTHOR_COLUMN = "author";
    public static final String CONTENT_COLUMN = "content";

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

    /**
     * ORMLite required constructor.
     */
    public MDBReview() {
    }

    public MDBReview(Integer id, Integer movieId, String author, String content) {
        this.id = id;
        this.movieId = movieId;
        this.author = author;
        this.content = content;
    }

    // region GETTERS & SETTERS
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
    //endregion

    // region PUBLIC METHODS
    public static MDBReview[] listByMovieId(Context context, Integer id) throws SQLException {
        List<MDBReview> results = MDBReview.getDao(context, MDBReview.class).queryBuilder().where().eq("movieId", id).query();
        MDBReview[] resultsArray = null;
        if(results != null && !results.isEmpty()) {
            resultsArray = results.toArray(new MDBReview[results.size()]);
        }
        return resultsArray;
    }

    public MDBReview[] listFromCursor(Cursor cursor) {
        if(cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        MDBReview[] reviews = new MDBReview[cursor.getCount()];

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Integer id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            Integer movieId = cursor.getInt(cursor.getColumnIndex(MOVIE_ID_COLUMN));
            String author = cursor.getString(cursor.getColumnIndex(AUTHOR_COLUMN));
            String content = cursor.getString(cursor.getColumnIndex(CONTENT_COLUMN));
            reviews[i] = new MDBReview(id, movieId, author, content);
        }

        return reviews;
    }

    public ContentValues createContentValues() {
        ContentValues contentValues = new ContentValues(4);
        contentValues.put(ID_COLUMN, id);
        contentValues.put(MOVIE_ID_COLUMN, movieId);
        contentValues.put(AUTHOR_COLUMN, author);
        contentValues.put(CONTENT_COLUMN, content);
        return contentValues;
    }
    //endregion
}
