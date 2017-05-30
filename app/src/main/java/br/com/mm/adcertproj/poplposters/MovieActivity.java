package br.com.mm.adcertproj.poplposters;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildMovieContentUri;
import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildMovieIdContentUri;
import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildReviewByMovieIdContentUri;
import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildReviewContentUri;
import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildVideoByMovieIdContentUri;
import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildVideoContentUri;

public class MovieActivity extends AppCompatActivity
        implements VideosAdapter.VideosClickListener, MDBRetrofit.MDBVideoTaskListener, MDBRetrofit.MDBReviewTaskListener {

    // region ATTRIBUTES
    public static final String EXTRA_MOVIE_KEY = "mdbMovieObj";

    @BindView(R.id.iv_movie_poster)
    ImageView mPosterImageView;
    @BindView(R.id.tv_movie_title)
    TextView mTitleTextView;
    @BindView(R.id.tv_movie_release_date)
    TextView mRelDateTextView;
    @BindView(R.id.tv_movie_vote_average)
    TextView mVoteAveTextView;
    @BindView(R.id.tv_movie_plot_synopsis)
    TextView mSynopsisTextView;
    @BindView(R.id.tv_no_trailers)
    TextView mNoTrailersTextView;
    @BindView(R.id.tv_no_reviews)
    TextView mNoReviewsTextView;
    @BindView(R.id.rv_trailers)
    RecyclerView mTrailersRecyclerView;
    @BindView(R.id.rv_reviews)
    RecyclerView mReviewsRecyclerView;
    @BindView(R.id.fab_fav_it)
    FloatingActionButton mFavItFloatingActionButton;

    private MenuItem shareMenuItem;
    private ShareActionProvider mShareActionProvider;

    private VideosAdapter mVideosAdapter;
    private ReviewsAdapter mReviewsAdapter;

    private MDBMovie movie;

    private Toast lastToast;
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
        movie = null;
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_KEY)) {
            movie = (MDBMovie) intent.getSerializableExtra(EXTRA_MOVIE_KEY);

            if (movie != null) {
                Picasso.with(this).load(MDBPreferences.buildPosterUrl(movie.getPosterPath())).into(mPosterImageView);
                mTitleTextView.setText(movie.getTitle());
                mRelDateTextView.setText(movie.getReleaseDateString());
                mVoteAveTextView.setText(String.valueOf(movie.getVoteAverage()));
                mSynopsisTextView.setText(movie.getOverview());
            }
        }

        updateFabIcon();
        mFavItFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePersistMovie();
            }
        });

        startTrailersTask(movie);
        startReviewsTask(movie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);

        shareMenuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);
        return true;
    }

    @Override
    public void onTaskResult(MDBReview[] taskResultArray) {
        if (taskResultArray != null && taskResultArray.length > 0) {
            showReviewsResults();
            mReviewsAdapter.setReviews(taskResultArray);
        } else {
            showNoReviewsText();
        }
    }

    @Override
    public void onTaskResult(MDBVideo[] taskResultArray) {
        if (taskResultArray != null && taskResultArray.length > 0) {
            showTrailersResults();
            mVideosAdapter.setVideos(taskResultArray);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, MDBPreferences.buildYouTubeUri(taskResultArray[0].getKey()).toString());
            shareIntent.setType("text/plain");
            setShareIntent(shareIntent);
        } else {
            showNoTrailersText();
            setShareIntent(null);
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
        toggleViewVisibility(mTrailersRecyclerView, mNoTrailersTextView);
    }

    private void showNoTrailersText() {
        toggleViewVisibility(mNoTrailersTextView, mTrailersRecyclerView);

    }

    private void showReviewsResults() {
        toggleViewVisibility(mReviewsRecyclerView, mNoReviewsTextView);
    }

    private void showNoReviewsText() {
        toggleViewVisibility(mNoReviewsTextView, mReviewsRecyclerView);
    }

    private void toggleViewVisibility(View showThis, View hideThis) {
        showThis.setVisibility(View.VISIBLE);
        hideThis.setVisibility(View.INVISIBLE);
    }

    private boolean isSaved() {
        boolean isSaved = false;
        if (movie != null && movie.getId() != null) {
            Cursor cursor = getContentResolver().query(buildMovieIdContentUri(
                    movie.getId()), null, null, null, null);
            if(cursor != null) {
                isSaved = cursor.getCount() > 0;
                cursor.close();
            }
        }
        return isSaved;
    }

    private void togglePersistMovie() {
        if (movie != null) {
            boolean isSaved = isSaved();

            ContentResolver contentResolver = getContentResolver();
            if (isSaved) {
                contentResolver
                        .delete(buildMovieIdContentUri(movie.getId()), null, null);
            } else {
                contentResolver
                        .insert(buildMovieContentUri(), movie.createContentValues());
            }

            if (isSaved) {
                contentResolver
                        .delete(buildVideoByMovieIdContentUri(movie.getId()), null, null);
            } else {
                if (mVideosAdapter.getVideos() != null) {
                    for (MDBVideo video : mVideosAdapter.getVideos()) {
                        contentResolver
                                .insert(buildVideoContentUri(), video.createContentValues());
                    }
                }
            }

            if (isSaved) {
                contentResolver
                        .delete(buildReviewByMovieIdContentUri(movie.getId()), null, null);
            } else {
                if (mReviewsAdapter.getReviews() != null) {
                    for (MDBReview review : mReviewsAdapter.getReviews()) {
                        contentResolver
                                .insert(buildReviewContentUri(), review.createContentValues());
                    }
                }
            }

            int messageId = R.string.movie_added_toast;
            if(isSaved) {
                messageId = R.string.movie_removed_toast;
            }
            if(lastToast != null) {
                lastToast.cancel();
            }
            lastToast = Toast.makeText(this, messageId, Toast.LENGTH_LONG);
            lastToast.show();

            updateFabIcon();
        }
    }

    private void updateFabIcon() {
        int fabResId = isSaved() ? android.R.drawable.ic_menu_delete : android.R.drawable.ic_menu_add;
        mFavItFloatingActionButton.setImageResource(fabResId);
    }

    private void setShareIntent(Intent shareIntent) {
        if(shareIntent != null) {
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(shareIntent);
            }
            if(shareMenuItem != null) {
                shareMenuItem.setVisible(true);
            }
        } else {
            if(shareMenuItem != null) {
                shareMenuItem.setVisible(false);
            }
        }
    }

    // endregion
}
