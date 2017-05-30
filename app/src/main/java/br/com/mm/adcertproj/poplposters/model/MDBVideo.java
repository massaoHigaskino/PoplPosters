package br.com.mm.adcertproj.poplposters.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "videos")
public class MDBVideo extends MDBAbstract implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;
    public static final String TABLE_NAME = "videos";
    public static final String ID_COLUMN = "id";
    public static final String MOVIE_ID_COLUMN = "movieId";
    public static final String KEY_COLUMN = "key";
    public static final String NAME_COLUMN = "name";

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

    /**
     * ORMLite required constructor.
     */
    public MDBVideo() {
    }

    public MDBVideo(Integer id, Integer movieId, String key, String name) {
        this.id = id;
        this.movieId = movieId;
        this.key = key;
        this.name = name;
    }

    // region GETTERS & SETTERS
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
    //endregion

    // region PUBLIC METHODS
    public ContentValues createContentValues() {
        ContentValues contentValues = new ContentValues(4);
        contentValues.put(ID_COLUMN, id);
        contentValues.put(MOVIE_ID_COLUMN, movieId);
        contentValues.put(KEY_COLUMN, key);
        contentValues.put(NAME_COLUMN, name);
        return contentValues;
    }

    public static MDBVideo[] listFromCursor(Cursor cursor) {
        if(cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        MDBVideo[] videos = new MDBVideo[cursor.getCount()];

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Integer id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            Integer movieId = cursor.getInt(cursor.getColumnIndex(MOVIE_ID_COLUMN));
            String key = cursor.getString(cursor.getColumnIndex(KEY_COLUMN));
            String name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
            videos[i] = new MDBVideo(id, movieId, key, name);
        }

        cursor.close();

        return videos;
    }
    //endregion
}
