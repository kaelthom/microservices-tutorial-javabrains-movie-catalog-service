package com.microservicestutorial.moviecatalogservice.resources;

import java.util.List;

public class UserRatingsResource {

    private int userId;
    private List<RatingResource> ratings;

    public UserRatingsResource() {
    }

    public UserRatingsResource(int userId, List<RatingResource> ratings) {
        this.userId = userId;
        this.ratings = ratings;
    }

    public List<RatingResource> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingResource> ratings) {
        this.ratings = ratings;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
