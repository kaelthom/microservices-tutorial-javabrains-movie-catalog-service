package com.microservicestutorial.moviecatalogservice.controllers;

import com.microservicestutorial.moviecatalogservice.resources.MovieInfoResource;
import com.microservicestutorial.moviecatalogservice.resources.RatingResource;
import com.microservicestutorial.moviecatalogservice.resources.UserRatingsResource;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class MovieCatalogFallbackService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getMovieInfoResourceFallback")
    public MovieInfoResource getMovieInfoResource(RatingResource moviesRate) {
        return restTemplate.getForObject("http://movie-info-api/movie/" + moviesRate.getMovieId(), MovieInfoResource.class);
    }

    @HystrixCommand(fallbackMethod = "getUserRatingsFallback")
    public UserRatingsResource getUserRatings(String userId) {
        return restTemplate.getForObject("http://movie-ratings-api/movie-rating/user/" + userId, UserRatingsResource.class);
    }

    @FallbackMethod
    private MovieInfoResource getMovieInfoResourceFallback(RatingResource moviesRate) {
        return new MovieInfoResource(-1, "No movie found", "");
    }

    @FallbackMethod
    private UserRatingsResource getUserRatingsFallback(String userId) {
        return new UserRatingsResource(-1, Collections.singletonList(new RatingResource(-1, -1)));
    }
}
