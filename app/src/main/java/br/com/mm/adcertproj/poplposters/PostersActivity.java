package br.com.mm.adcertproj.poplposters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.mm.adcertproj.poplposters.adapter.PostersAdapter;
import br.com.mm.adcertproj.poplposters.helpers.MDBHelper;
import br.com.mm.adcertproj.poplposters.model.MDBMovie;
import br.com.mm.adcertproj.poplposters.tasks.MDBMovieTask;

public class PostersActivity extends AppCompatActivity
        implements PostersAdapter.PostersClickListener, MDBMovieTask.MDBMovieTaskListener, MDBHelper.IApiKeyInput{

    public static final String EXTRA_MOVIE_KEY = "mdbMovieObj";

    private RecyclerView mRecyclerView;
    private PostersAdapter mPostersAdapter;
    private TextView mErrorTextView;
//    private TextView mDebugWarningTextView;
//    private LinearLayout mErrorContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_posters);
        mPostersAdapter = new PostersAdapter(this);
        mErrorTextView = (TextView) findViewById(R.id.tv_error);
//        mDebugWarningTextView = (TextView) findViewById(R.id.tv_debug_warning);
//        mErrorContainer = (LinearLayout) findViewById(R.id.error_container);

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mPostersAdapter);

        startMovieTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMovieTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.posters_menu, menu);
        MenuItem enterKeyMenuItem = menu.findItem(R.id.action_enter_key);
        enterKeyMenuItem.setVisible(BuildConfig.DEBUG);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                MDBHelper.showSortSpinner(this, this);
                return true;
            case R.id.action_enter_key:
                MDBHelper.showApiKeyInput(this, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTaskResult(MDBMovie[] taskResultArray) {
        if(taskResultArray != null && taskResultArray.length > 0) {
            showResults();
            mPostersAdapter.setMovies(taskResultArray);
        } else {
            showError();
        }
    }

    @Override
    public void onPostersClick(MDBMovie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(EXTRA_MOVIE_KEY, movie);
        startActivity(intent);
    }

    @Override
    public void onApiKeyInput() {
        startMovieTask();
    }

    private void startMovieTask() {
        new MDBMovieTask(this, this).execute();
    }

    private void showResults() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
//        mDebugWarningTextView.setVisibility(View.INVISIBLE);
//        mErrorContainer.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        if(BuildConfig.DEBUG) {
//            mDebugWarningTextView.setVisibility(View.VISIBLE);
        }
//        mErrorContainer.setVisibility(View.VISIBLE);
    }
}
