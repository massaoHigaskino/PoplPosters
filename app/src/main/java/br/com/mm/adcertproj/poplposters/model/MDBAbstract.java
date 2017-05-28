package br.com.mm.adcertproj.poplposters.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import br.com.mm.adcertproj.poplposters.helpers.DatabaseHelper;

public class MDBAbstract {

    public static Dao getDao(Context context, Class tableClazz) throws SQLException {
        return getHelper(context).getDao(tableClazz);
    }

    public static DatabaseHelper getHelper(Context context) {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static List queryForAll(Context context, Class tableClazz) throws SQLException {
        return getDao(context, tableClazz).queryForAll();
    }

    public Dao getDao(Context context) throws SQLException {
        return getHelper(context).getDao(getClass());
    }

    public void createOrUpdate(Context context) throws SQLException {
        getDao(context).createOrUpdate(this);
    }

    public int delete(Context context) throws SQLException {
        return getDao(context).delete(this);
    }
}
