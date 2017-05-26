package br.com.mm.adcertproj.poplposters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.mm.adcertproj.poplposters.adapter.ReviewsAdapter;
import br.com.mm.adcertproj.poplposters.adapter.VideosAdapter;
import br.com.mm.adcertproj.poplposters.model.MDBMovie;
import br.com.mm.adcertproj.poplposters.model.MDBReview;
import br.com.mm.adcertproj.poplposters.model.MDBVideo;
import br.com.mm.adcertproj.poplposters.pref.MDBPreferences;
import br.com.mm.adcertproj.poplposters.tasks.MDBRetrofit;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity
        implements VideosAdapter.VideosClickListener, MDBRetrofit.MDBVideoTaskListener, MDBRetrofit.MDBReviewTaskListener {

    // region ATTRIBUTES
    public static final String EXTRA_MOVIE_KEY = "mdbMovieObj";

    @BindView(R.id.iv_movie_poster) ImageView mPosterImageView;
    @BindView(R.id.tv_movie_title) TextView mTitleTextView;
    @BindView(R.id.tv_movie_release_date) TextView mRelDateTextView;
    @BindView(R.id.tv_movie_vote_average) TextView mVoteAveTextView;
    @BindView(R.id.tv_movie_plot_synopsis) TextView mSynopsisTextView;
    @BindView(R.id.tv_no_trailers) TextView mNoTrailersTextView;
    @BindView(R.id.tv_no_reviews) TextView mNoReviewsTextView;
    @BindView(R.id.rv_trailers) RecyclerView mTrailersRecyclerView;
    @BindView(R.id.rv_reviews) RecyclerView mReviewsRecyclerView;
    private VideosAdapter mVideosAdapter;
    private ReviewsAdapter mReviewsAdapter;
    // endregion

    // region PROTECTED METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        mVideosAdapter = new VideosAdapter(this);
        mReviewsAdapter = new ReviewsAdapter();

        mTrailersRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mReviewsRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mTrailersRecyclerView.setHasFixedSize(true);
        mReviewsRecyclerView.setHasFixedSize(true);

        mTrailersRecyclerView.setAdapter(mVideosAdapter);
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);

        Intent intent = getIntent();
        MDBMovie movie = null;
        if(intent != null && intent.hasExtra(EXTRA_MOVIE_KEY)) {
            movie = (MDBMovie) intent.getSerializableExtra(EXTRA_MOVIE_KEY);

            if(movie != null) {
                Picasso.with(this).load(MDBPreferences.buildPosterUrl(movie.getPosterPath())).into(mPosterImageView);
                mTitleTextView.setText(movie.getTitle());
                mRelDateTextView.setText(movie.getReleaseDateString());
                mVoteAveTextView.setText(String.valueOf(movie.getVoteAverage()));
                mSynopsisTextView.setText(movie.getOverview());
            }
        }

        startTrailersTask(movie);
        startReviewsTask(movie);
    }

    @Override
    public void onTaskResult(MDBReview[] taskResultArray) {
        if(taskResultArray != null && taskResultArray.length > 0) {
            showReviewsResults();
            mReviewsAdapter.setReviews(taskResultArray);
        } else {
            showNoReviewsText();
        }
    }

    @Override
    public void onTaskResult(MDBVideo[] taskResultArray) {
        if(taskResultArray != null && taskResultArray.length > 0) {
            showTrailersResults();
            mVideosAdapter.setVideos(taskResultArray);
        } else {
            showNoTrailersText();
        }
    }

    @Override
    public void onVideosClick(MDBVideo video) {
        Intent intent = new Intent(Intent.ACTION_VIEW, MDBPreferences.buildYouTubeUri(video.getKey()));
        startActivity(intent);
    }
    // endregion

    // region PRIVATE METHODS
    private void startTrailersTask(MDBMovie movie) {
        MDBRetrofit.runMDBVideoTask(this, this, movie);
    }

    private void startReviewsTask(MDBMovie movie) {
        MDBRetrofit.runMDBReviewTask(this, this, movie);
    }

    private void showTrailersResults() {
        mTrailersRecyclerView.setVisibility(View.VISIBLE);
        mNoTrailersTextView.setVisibility(View.INVISIBLE);
    }

    private void showNoTrailersText() {
        mTrailersRecyclerView.setVisibility(View.INVISIBLE);
        mNoTrailersTextView.setVisibility(View.VISIBLE);

    }

    private void showReviewsResults() {
        mReviewsRecyclerView.setVisibility(View.VISIBLE);
        mNoReviewsTextView.setVisibility(View.INVISIBLE);
    }

    private void showNoReviewsText() {
        mReviewsRecyclerView.setVisibility(View.INVISIBLE);
        mNoReviewsTextView.setVisibility(View.VISIBLE);
    }
    // endregion
}
