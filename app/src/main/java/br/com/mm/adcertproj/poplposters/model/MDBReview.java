package br.com.mm.adcertproj.poplposters.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

import br.com.mm.adcertproj.poplposters.helpers.JSONHelper;

public class MDBReview implements Serializable {

    //region ATTRIBUTES
    public static final int serialVersionUID = 1;

    private static final String MDM_ID = "id";
    private static final String MDM_RESULTS = "results";
    private static final String MDM_AUTHOR = "author";
    private static final String MDM_CONTENT = "content";

    private String id;
    private String author;
    private String content;
    //endregion

    // region GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    //endregion

    // region PUBLIC METHODS
    public static MDBReview[] listFromJSON(String json) {
        MDBReview[] reviewsArray = null;
        JSONObject mdbJson = JSONHelper.getJSONObject(json);
        if(mdbJson != null) {
            JSONArray reviewsJson = JSONHelper.getJSONArray(mdbJson, MDM_RESULTS);
            if(reviewsJson != null && reviewsJson.length() > 0) {
                reviewsArray = new MDBReview[reviewsJson.length()];
                for(int i = 0; i < reviewsJson.length(); i++) {
                    JSONObject reviewJson = reviewsJson.optJSONObject(i);
                    MDBReview review = null;
                    if(reviewJson != null) {
                        review = new MDBReview();
                        review.setId(reviewJson.optString(MDM_ID));
                        review.setAuthor(reviewJson.optString(MDM_AUTHOR));
                        review.setContent(reviewJson.optString(MDM_CONTENT));
                    }
                    reviewsArray[i] = review;
                }
            }
        }
        return reviewsArray;
    }
    //endregion
}
