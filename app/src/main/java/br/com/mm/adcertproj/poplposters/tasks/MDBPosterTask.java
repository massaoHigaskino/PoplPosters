package br.com.mm.adcertproj.poplposters.tasks;

import android.content.Context;
import android.os.AsyncTask;

import br.com.mm.adcertproj.poplposters.helpers.AsyncTaskHelper;

public class MDBPosterTask extends AsyncTask<Void, Void, MDBPosterTask.TaskResult> {

    private Context context;

    public MDBPosterTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        AsyncTaskHelper.showProgressDialog(context);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        AsyncTaskHelper.dismissProgressDialog();
    }

    class TaskResult {

    }
}
