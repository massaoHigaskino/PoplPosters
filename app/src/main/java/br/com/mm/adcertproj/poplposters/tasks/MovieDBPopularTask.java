package br.com.mm.adcertproj.poplposters.tasks;

import android.os.AsyncTask;

public class MovieDBPopularTask extends AsyncTask<Void, Void, MovieDBPopularTask.TaskResult> {

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
