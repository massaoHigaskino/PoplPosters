package br.com.mm.adcertproj.poplposters.tasks;

import android.os.AsyncTask;

public class MovieDBPosterTask extends AsyncTask<Void, Void, MovieDBPosterTask.TaskResult> {

    @Override
    protected TaskResult doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        super.onPostExecute(taskResult);
    }

    class TaskResult {

    }
}
