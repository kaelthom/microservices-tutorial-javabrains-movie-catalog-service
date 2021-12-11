package com.microservicestutorial.moviecatalogservice.resources;

import java.util.List;

public class MovieCatalogResource {
    String userId;
    String username;
    List<MovieRatingResource> movies;

    public MovieCatalogResource(String userId, String username, List<MovieRatingResource> movies) {
        this.userId = userId;
        this.username = username;
        this.movies = movies;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MovieRatingResource> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieRatingResource> movies) {
        this.movies = movies;
    }
}
