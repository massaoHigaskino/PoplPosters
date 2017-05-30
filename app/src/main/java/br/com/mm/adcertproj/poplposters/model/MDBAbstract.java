package br.com.mm.adcertproj.poplposters.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import br.com.mm.adcertproj.poplposters.helpers.DatabaseHelper;

public class MDBAbstract {
    public static DatabaseHelper getHelper(Context context) {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }
}
