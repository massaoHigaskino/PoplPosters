package br.com.mm.adcertproj.poplposters.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

import br.com.mm.adcertproj.poplposters.helpers.JSONHelper;

public class MDBMovie implements Serializable {
    public static final int serialVersionUID = 1;

    private static final String MDM_RESULTS = "results";
    private static final String MDM_POSTER_PATH = "poster_path";
    private static final String MDM_ADULT = "adult";
    private static final String MDM_OVERVIEW = "overview";
    private static final String MDM_RELEASE_DATE = "release_date";
    private static final String MDM_GENRE_IDS = "genre_ids";
    private static final String MDM_ID = "id";
    private static final String MDM_ORIGINAL_TITLE = "original_title";
    private static final String MDM_ORIGINAL_LANGUAGE = "original_language";
    private static final String MDM_TITLE = "title";
    private static final String MDM_BACKDROP_PATH = "backdrop_path";
    private static final String MDM_POPULARITY = "popularity";
    private static final String MDM_VOTE_COUNT = "vote_count";
    private static final String MDM_VIDEO = "video";
    private static final String MDM_VOTE_AVERAGE = "vote_average";

    private String posterPath; // "poster_path": "/4PiiNGXj1KENTmCBHeN6Mskj2Fq.jpg",
    private Boolean adult; // "adult": false,
    private String overview; // "overview": "After his career is destroyed, a brilliant but arrogant surgeon gets a new lease on life when a sorcerer takes him under his wing and trains him to defend the world against evil.",
    private Date releaseDate; // "release_date": "2016-10-25",
    private String releaseDateString;
    private Integer[] genreIds; // "genre_ids": [ 28, 12, 14, 878 ],
    private Integer id; // "id": 284052,
    private String originalTitle; // "original_title": "Doctor Strange",
    private String originalLanguage; // "original_language": "en",
    private String title; // "title": "Doctor Strange",
    private String backdropPath; // "backdrop_path": "/tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg",
    private Double popularity; // "popularity": 21.287906,
    private Integer voteCount; // "vote_count": 3206,
    private Boolean video; // "video": false,
    private Double voteAverage; // "vote_average": 6.9

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDateString() {
        return releaseDateString;
    }

    public void setReleaseDateString(String releaseDateString) {
        this.releaseDateString = releaseDateString;
    }

    public Integer[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(Integer[] genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

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
                        movie.setAdult(movieJson.optBoolean(MDM_ADULT));
                        movie.setOverview(movieJson.optString(MDM_OVERVIEW));
                        movie.setReleaseDate(JSONHelper.getDate(movieJson, MDM_RELEASE_DATE));
                        movie.setReleaseDateString(movieJson.optString(MDM_RELEASE_DATE));
                        movie.setGenreIds(JSONHelper.getIntArray(movieJson, MDM_GENRE_IDS));
                        movie.setId(movieJson.optInt(MDM_ID));
                        movie.setOriginalTitle(movieJson.optString(MDM_ORIGINAL_TITLE));
                        movie.setOriginalLanguage(movieJson.optString(MDM_ORIGINAL_LANGUAGE));
                        movie.setTitle(movieJson.optString(MDM_TITLE));
                        movie.setBackdropPath(movieJson.optString(MDM_BACKDROP_PATH));
                        movie.setPopularity(movieJson.optDouble(MDM_POPULARITY));
                        movie.setVoteCount(movieJson.optInt(MDM_VOTE_COUNT));
                        movie.setVideo(movieJson.optBoolean(MDM_VIDEO));
                        movie.setVoteAverage(movieJson.optDouble(MDM_VOTE_AVERAGE));
                    }
                    moviesArray[i] = movie;
                }
            }
        }
        return moviesArray;
    }
}
