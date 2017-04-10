package br.com.mm.adcertproj.poplposters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.mm.adcertproj.poplposters.model.MDBMovie;
import br.com.mm.adcertproj.poplposters.pref.MDBPreferences;

public class MovieActivity extends AppCompatActivity {

    // region ATTRIBUTES
    public static final String EXTRA_MOVIE_KEY = "mdbMovieObj";

    private ImageView mPosterImageView;
    private TextView mTitleTextView;
    private TextView mRelDateTextView;
    private TextView mVoteAveTextView;
    private TextView mSynopsisTextView;
    // endregion

    // region PROTECTED METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mPosterImageView = (ImageView) findViewById(R.id.iv_movie_poster);
        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mRelDateTextView = (TextView) findViewById(R.id.tv_movie_release_date);
        mVoteAveTextView = (TextView) findViewById(R.id.tv_movie_vote_average);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_movie_plot_synopsis);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_MOVIE_KEY)) {
            MDBMovie movie = (MDBMovie) intent.getSerializableExtra(EXTRA_MOVIE_KEY);

            if(movie != null) {
                Picasso.with(this).load(MDBPreferences.buildPosterUrl(movie.getPosterPath())).into(mPosterImageView);
                mTitleTextView.setText(movie.getTitle());
                mRelDateTextView.setText(movie.getReleaseDateString());
                mVoteAveTextView.setText(String.valueOf(movie.getVoteAverage()));
                mSynopsisTextView.setText(movie.getOverview());
            }
        }
    }
    // endregion
}
