package br.com.mm.adcertproj.poplposters;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import br.com.mm.adcertproj.poplposters.helpers.DatabaseHelper;
import br.com.mm.adcertproj.poplposters.model.MDBAbstract;
import br.com.mm.adcertproj.poplposters.model.MDBMovie;
import br.com.mm.adcertproj.poplposters.model.MDBReview;
import br.com.mm.adcertproj.poplposters.model.MDBVideo;

public class MDBContentProvider extends ContentProvider {

    public static final String AUTHORITY_NAME = "br.com.mm.adcertproj.poplposters";
    public static final String MOVIE_QUERY_PATH = "movies";
    public static final String MOVIE_M_ID_PATH = "movies/#";
    public static final String REVIEW_QUERY_PATH = "reviews";
    public static final String REVIEW_M_ID_PATH = "reviews/#";
    public static final String VIDEO_QUERY_PATH = "videos";
    public static final String VIDEO_M_ID_PATH = "videos/#";

    public static final int MOVIE_QUERY_CODE = 100;
    public static final int MOVIE_M_ID_CODE = 101;
    public static final int REVIEW_QUERY_CODE = 200;
    public static final int REVIEW_M_ID_CODE = 201;
    public static final int VIDEO_QUERY_CODE = 300;
    public static final int VIDEO_M_ID_CODE = 301;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DatabaseHelper dbHelper;

    static {
        uriMatcher.addURI(AUTHORITY_NAME, MOVIE_QUERY_PATH, MOVIE_QUERY_CODE);
        uriMatcher.addURI(AUTHORITY_NAME, MOVIE_M_ID_PATH, MOVIE_M_ID_CODE);
        uriMatcher.addURI(AUTHORITY_NAME, REVIEW_QUERY_PATH, REVIEW_QUERY_CODE);
        uriMatcher.addURI(AUTHORITY_NAME, REVIEW_M_ID_PATH, REVIEW_M_ID_CODE);
        uriMatcher.addURI(AUTHORITY_NAME, VIDEO_QUERY_PATH, VIDEO_QUERY_CODE);
        uriMatcher.addURI(AUTHORITY_NAME, VIDEO_M_ID_PATH, VIDEO_M_ID_CODE);
    }

    @Override
    public boolean onCreate() {
        dbHelper = MDBAbstract.getHelper(getContext());
        return dbHelper != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String table = null;
        selection = null;
        selectionArgs = null;
        switch (uriMatcher.match(uri)) {
            case MOVIE_QUERY_CODE:
                table = MDBMovie.TABLE_NAME;
                break;
            case MOVIE_M_ID_CODE:
                table = MDBMovie.TABLE_NAME;
                selection = MDBMovie.ID_COLUMN + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            case REVIEW_QUERY_CODE:
                table = MDBReview.TABLE_NAME;
                break;
            case REVIEW_M_ID_CODE:
                table = MDBReview.TABLE_NAME;
                selection = MDBReview.MOVIE_ID_COLUMN + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            case VIDEO_QUERY_CODE:
                table = MDBVideo.TABLE_NAME;
                break;
            case VIDEO_M_ID_CODE:
                table = MDBVideo.TABLE_NAME;
                selection = MDBVideo.MOVIE_ID_COLUMN + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            default:
                return null;
        }

        return dbHelper.getReadableDatabase().query(table, null, selection, selectionArgs, null, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "application/x-java-serialized-object";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String table;
        String path;
        String id;
        switch (uriMatcher.match(uri)) {
            case MOVIE_QUERY_CODE:
                table = MDBMovie.TABLE_NAME;
                path = MOVIE_QUERY_PATH;
                id = MDBMovie.ID_COLUMN;
                break;
            case REVIEW_QUERY_CODE:
                table = MDBReview.TABLE_NAME;
                path = REVIEW_QUERY_PATH;
                id = MDBReview.MOVIE_ID_COLUMN;
                break;
            case VIDEO_QUERY_CODE:
                table = MDBVideo.TABLE_NAME;
                path = VIDEO_QUERY_PATH;
                id = MDBVideo.MOVIE_ID_COLUMN;
                break;
            default:
                return null;
        }

        Uri result = Uri.parse("content://" + AUTHORITY_NAME + "/" + path + "/" + values.get(id));

        boolean success = dbHelper.getWritableDatabase().insert(table, null, values) > 0;

        if(success) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = null;
        selection = "1";
        selectionArgs = null;
        switch (uriMatcher.match(uri)) {
            case MOVIE_QUERY_CODE:
                table = MDBMovie.TABLE_NAME;
                break;
            case MOVIE_M_ID_CODE:
                table = MDBMovie.TABLE_NAME;
                selection = MDBMovie.ID_COLUMN + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            case REVIEW_QUERY_CODE:
                table = MDBReview.TABLE_NAME;
                break;
            case REVIEW_M_ID_CODE:
                table = MDBReview.TABLE_NAME;
                selection = MDBReview.MOVIE_ID_COLUMN + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            case VIDEO_QUERY_CODE:
                table = MDBVideo.TABLE_NAME;
                break;
            case VIDEO_M_ID_CODE:
                table = MDBVideo.TABLE_NAME;
                selection = MDBVideo.MOVIE_ID_COLUMN + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            default:
                return 0;
        }

        return dbHelper.getWritableDatabase().delete(table, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
