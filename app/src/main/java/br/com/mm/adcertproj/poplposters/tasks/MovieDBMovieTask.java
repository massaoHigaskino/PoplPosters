package br.com.mm.adcertproj.poplposters.tasks;

import android.os.AsyncTask;

/**
 * Created by massao on 03.04.17.
 */

public class MovieDBMovieTask extends AsyncTask<Void, Void, MovieDBMovieTask.TaskResult> {

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
