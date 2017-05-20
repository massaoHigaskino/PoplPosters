package br.com.mm.adcertproj.poplposters.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

import br.com.mm.adcertproj.poplposters.helpers.JSONHelper;

public class MDBMovie implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;

    private static final String MDM_RESULTS = "results";
    private static final String MDM_POSTER_PATH = "poster_path";
    private static final String MDM_OVERVIEW = "overview";
    private static final String MDM_RELEASE_DATE = "release_date";
    private static final String MDM_ID = "id";
    private static final String MDM_TITLE = "title";
    private static final String MDM_VOTE_AVERAGE = "vote_average";

    private String posterPath;
    private String overview;
    private String releaseDateString;
    private Integer id;
    private String title;
    private Double voteAverage;
    // endregion

    // region GETTERS & SETTERS
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDateString() {
        return releaseDateString;
    }

    public void setReleaseDateString(String releaseDateString) {
        this.releaseDateString = releaseDateString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
    // endregion

    // region PUBLIC METHODS
    public static MDBMovie[] listFromJSON(String json) {
        MDBMovie[] moviesArray = null;
        JSONObject mdbJson = JSONHelper.getJSONObject(json);
        if(mdbJson != null) {
            JSONArray moviesJson = JSONHelper.getJSONArray(mdbJson, MDM_RESULTS);
            if(moviesJson != null && moviesJson.length() > 0) {
                moviesArray = new MDBMovie[moviesJson.length()];
                for(int i = 0; i < moviesJson.length(); i++) {
                    JSONObject movieJson = moviesJson.optJSONObject(i);
                    MDBMovie movie = null;
                    if(movieJson != null) {
                        movie = new MDBMovie();
                        movie.setPosterPath(movieJson.optString(MDM_POSTER_PATH));
                        movie.setOverview(movieJson.optString(MDM_OVERVIEW));
                        movie.setReleaseDateString(movieJson.optString(MDM_RELEASE_DATE));
                        movie.setId(movieJson.optInt(MDM_ID));
                        movie.setTitle(movieJson.optString(MDM_TITLE));
                        movie.setVoteAverage(movieJson.optDouble(MDM_VOTE_AVERAGE));
                    }
                    moviesArray[i] = movie;
                }
            }
        }
        return moviesArray;
    }
    // endregion
}
