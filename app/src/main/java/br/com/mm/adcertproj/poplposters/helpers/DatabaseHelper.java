package br.com.mm.adcertproj.poplposters.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.com.mm.adcertproj.poplposters.model.MDBMovie;
import br.com.mm.adcertproj.poplposters.model.MDBReview;
import br.com.mm.adcertproj.poplposters.model.MDBVideo;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "helloAndroid.db";
    private static final int DATABASE_VERSION = 1;
    private static final Class[] TABLES = {MDBMovie.class, MDBVideo.class, MDBReview.class};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        for(Class table : TABLES) {
            try {
                TableUtils.createTable(connectionSource, table);
            } catch (SQLException e) {
                Log.e(getClass().getName(), e.getMessage(), e);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        for(Class table : TABLES) {
            try {
                TableUtils.dropTable(connectionSource, table, true);
            } catch (SQLException e) {
                Log.e(getClass().getName(), e.getMessage(), e);
            }
        }

        onCreate(database, connectionSource);
    }
}
