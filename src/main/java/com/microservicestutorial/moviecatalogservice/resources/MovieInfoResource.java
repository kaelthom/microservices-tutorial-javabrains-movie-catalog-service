package com.microservicestutorial.moviecatalogservice.resources;

public class MovieInfoResource {

    private int id;
    private String original_title;
    private String overview;

    public MovieInfoResource() {
    }

    public MovieInfoResource(int id, String originalTitle, String description) {
        this.id = id;
        this.original_title = originalTitle;
        this.overview = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
