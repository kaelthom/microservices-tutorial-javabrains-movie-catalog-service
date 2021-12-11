package com.microservicestutorial.moviecatalogservice.resources;

public class RatingResource {
    private int movieId;
    private int rate;

    public RatingResource(int movieId, int rate) {
        this.movieId = movieId;
        this.rate = rate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
