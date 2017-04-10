package br.com.mm.adcertproj.poplposters.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.com.mm.adcertproj.poplposters.helpers.AsyncTaskHelper;
import br.com.mm.adcertproj.poplposters.model.MDBMovie;
import br.com.mm.adcertproj.poplposters.pref.MDBPreferences;

public class MDBMovieTask extends AsyncTask<Void, Void, MDBMovie[]> {

    // region ATTRIBUTES
    private Context context;
    private MDBMovieTaskListener mTaskResultListerner;
    private int retries = 1;
    // endregion

    public MDBMovieTask(Context context, MDBMovieTaskListener listener) {
        this.context = context;
        this.mTaskResultListerner = listener;
    }

    // region PROTECTED METHODS
    @Override
    protected void onPreExecute() {
        AsyncTaskHelper.showProgressDialog(context);
    }

    @Override
    protected MDBMovie[] doInBackground(Void... params) {
        MDBMovie[] taskResult = null;
        try {
            String jsonResponse = AsyncTaskHelper.getResponseFromHttpUrl(MDBPreferences.buildPopMoviesQueryUrl());
            taskResult = MDBMovie.listFromJSON(jsonResponse);
        } catch (Throwable t) {
            Log.e(this.getClass().getName(), t.getMessage());
        }
        return taskResult;
    }

    @Override
    protected void onPostExecute(MDBMovie[] mdbMovieArray) {
        AsyncTaskHelper.dismissProgressDialog();
        mTaskResultListerner.onTaskResult(mdbMovieArray);
    }
    // endregion

    // region AUXILIARY CLASSES
    public interface MDBMovieTaskListener {
        void onTaskResult(MDBMovie[] taskResultArray);
    }
    // endregion

}
