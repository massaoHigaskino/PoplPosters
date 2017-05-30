package br.com.mm.adcertproj.poplposters.tasks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import br.com.mm.adcertproj.poplposters.R;
import br.com.mm.adcertproj.poplposters.helpers.AsyncTaskHelper;
import br.com.mm.adcertproj.poplposters.model.MDBDeserializer;
import br.com.mm.adcertproj.poplposters.model.MDBMovie;
import br.com.mm.adcertproj.poplposters.model.MDBReview;
import br.com.mm.adcertproj.poplposters.model.MDBVideo;
import br.com.mm.adcertproj.poplposters.pref.MDBPreferences;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildMovieContentUri;
import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildReviewByMovieIdContentUri;
import static br.com.mm.adcertproj.poplposters.MDBContentProvider.buildVideoByMovieIdContentUri;

public class MDBRetrofit {

    public static void runMDBMovieTask(final Context context,
                                       final MDBMovieTaskListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MDBPreferences.POPULAR_MOVIES_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .registerTypeAdapter(MDBMovie[].class,
                                new MDBDeserializer<MDBMovie[]>(MDBMovie.MDM_RESULTS))
                        .create()))
                .build();

        MDBApi mdbApi = retrofit.create(MDBApi.class);
        Observable<MDBMovie[]> movies = mdbApi.getMovies(MDBPreferences.getSortType(),
                MDBPreferences.getValueApiKey());

        movies.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MDBMovie[]>() {
                    private MDBMovie[] movies;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        AsyncTaskHelper.showProgressDialog(context);
                    }

                    @Override
                    public void onNext(@NonNull MDBMovie[] mdbMovie) {
                        movies = mdbMovie;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(this.getClass().getName(), e.getMessage(), e);
                        movies = MDBMovie.listFromCursor(context.getContentResolver()
                                .query(buildMovieContentUri(), null, null, null, null));
                        AsyncTaskHelper.dismissProgressDialog();
                        Toast.makeText(
                                context, R.string.showing_favorites_toast, Toast.LENGTH_LONG).show();
                        listener.onTaskResult(movies);
                    }

                    @Override
                    public void onComplete() {
                        AsyncTaskHelper.dismissProgressDialog();
                        listener.onTaskResult(movies);
                    }
                });
    }

    public static void runMDBReviewTask(final Context context,
                                        final MDBReviewTaskListener listener,
                                        final MDBMovie movie) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MDBPreferences.POPULAR_MOVIES_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .registerTypeAdapter(MDBReview[].class,
                                new MDBDeserializer<MDBReview[]>(MDBReview.MDM_RESULTS))
                        .create()))
                .build();

        MDBApi mdbApi = retrofit.create(MDBApi.class);
        Observable<MDBReview[]> reviews = mdbApi.getReviews(movie.getId(),
                MDBPreferences.getValueApiKey());

        reviews.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MDBReview[]>() {
                    private MDBReview[] reviews;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MDBReview[] mdbReviews) {
                        reviews = mdbReviews;

                        // TODO this should be in another thread
                        if(reviews != null) {
                            for(MDBReview review : reviews) {
                                review.setMovieId(movie.getId());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(this.getClass().getName(), e.getMessage(), e);
                        reviews = MDBReview.listFromCursor(context.getContentResolver()
                                .query(buildReviewByMovieIdContentUri(movie.getId()),
                                        null, null, null, null));
                        listener.onTaskResult(reviews);
                    }

                    @Override
                    public void onComplete() {
                        listener.onTaskResult(reviews);
                    }
                });
    }

    public static void runMDBVideoTask(final Context context,
                                       final MDBVideoTaskListener listener,
                                       final MDBMovie movie) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MDBPreferences.POPULAR_MOVIES_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .registerTypeAdapter(MDBVideo[].class,
                                new MDBDeserializer<MDBVideo[]>(MDBVideo.MDM_RESULTS))
                        .create()))
                .build();

        MDBApi mdbApi = retrofit.create(MDBApi.class);
        Observable<MDBVideo[]> reviews = mdbApi.getVideos(movie.getId(),
                MDBPreferences.getValueApiKey());

        reviews.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MDBVideo[]>() {
                    private MDBVideo[] videos;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MDBVideo[] mdbVideos) {
                        videos = mdbVideos;

                        // TODO this should be in another thread
                        if(videos != null) {
                            for(MDBVideo video : videos) {
                                video.setMovieId(movie.getId());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(this.getClass().getName(), e.getMessage(), e);
                        videos = MDBVideo.listFromCursor(context.getContentResolver()
                                .query(buildVideoByMovieIdContentUri(movie.getId()),
                                        null, null, null, null));
                        listener.onTaskResult(videos);
                    }

                    @Override
                    public void onComplete() {
                        listener.onTaskResult(videos);
                    }
                });
    }

    public interface MDBApi {
        @GET("{sort}?")
        Observable<MDBMovie[]> getMovies(@Path("sort") String sortType,
                                         @Query("api_key") String apiKey);

        @GET("{id}/reviews?")
        Observable<MDBReview[]> getReviews(@Path("id") Integer id,
                                           @Query("api_key") String apiKey);

        @GET("{id}/videos?")
        Observable<MDBVideo[]> getVideos(@Path("id") Integer id,
                                         @Query("api_key") String apiKey);
    }

    // region AUXILIARY CLASSES
    public interface MDBMovieTaskListener {
        void onTaskResult(MDBMovie[] taskResultArray);
    }

    public interface MDBReviewTaskListener {
        void onTaskResult(MDBReview[] taskResultArray);
    }

    public interface MDBVideoTaskListener {
        void onTaskResult(MDBVideo[] taskResultArray);
    }
    // endregion
}
