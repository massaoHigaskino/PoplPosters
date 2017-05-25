package br.com.mm.adcertproj.poplposters.tasks;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import br.com.mm.adcertproj.poplposters.helpers.AsyncTaskHelper;
import br.com.mm.adcertproj.poplposters.model.MDBDeserializer;
import br.com.mm.adcertproj.poplposters.model.MDBMovie;
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

public class MDBRetrofit {

    public static void runMDBMovieTask(final Context context, final MDBMovieTaskListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MDBPreferences.POPULAR_MOVIES_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .registerTypeAdapter(MDBMovie[].class, new MDBDeserializer<MDBMovie[]>(MDBMovie.MDM_RESULTS))
                        .create()))
                .build();

        MDBApi mdbApi = retrofit.create(MDBApi.class);
        Observable<MDBMovie[]> movies = mdbApi.getMovies(MDBPreferences.getSortType(), MDBPreferences.getValueApiKey());

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
                    // remember onNext and onComplete will not be called after this method.
                    public void onError(@NonNull Throwable e) {
                        AsyncTaskHelper.dismissProgressDialog();
                        Log.e(this.getClass().getName(), e.getMessage());
                        listener.onTaskResult(null);
                    }

                    @Override
                    public void onComplete() {
                        AsyncTaskHelper.dismissProgressDialog();
                        listener.onTaskResult(movies);
                    }
                });
    }

    public interface MDBApi {
        @GET("{sort}?")
        Observable<MDBMovie[]> getMovies(@Path("sort") String sortType, @Query("api_key") String apiKey);
    }

    // region AUXILIARY CLASSES
    public interface MDBMovieTaskListener {
        void onTaskResult(MDBMovie[] taskResultArray);
    }
    // endregion
}
