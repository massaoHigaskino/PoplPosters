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
import br.com.mm.adcertproj.poplposters.tasks.MDBRetrofit;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PostersActivity extends AppCompatActivity
        implements PostersAdapter.PostersClickListener, MDBRetrofit.MDBMovieTaskListener, MDBHelper.IApiKeyInput{

    //region ATTRIBUTES
    public static final String EXTRA_MOVIE_KEY = "mdbMovieObj";

    @BindView(R.id.rv_posters) RecyclerView mRecyclerView;
    private PostersAdapter mPostersAdapter;
    @BindView(R.id.tv_error) TextView mErrorTextView;
    // endregion

    // region PROTECTED METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);
        ButterKnife.bind(this);

        mPostersAdapter = new PostersAdapter(this);

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
    // endregion

    // region PUBLIC METHODS
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
    // endregion

    // region PRIVATE METHODS
    private void startMovieTask() {
        MDBRetrofit.runMDBMovieTask(this, this);
    }

    private void showResults() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }
    // endregion
}
