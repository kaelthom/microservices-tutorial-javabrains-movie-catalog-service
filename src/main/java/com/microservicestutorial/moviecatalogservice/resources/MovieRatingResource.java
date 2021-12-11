package com.microservicestutorial.moviecatalogservice.resources;

public class MovieRatingResource {
    int id;
    int rate;
    String name;
    String description;

    public MovieRatingResource(int id, int rate, String name, String description) {
        this.id = id;
        this.rate = rate;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
