package com.microservicestutorial.moviecatalogservice.services;

import com.microservicestutorial.moviecatalogservice.controllers.FallbackMethod;
import com.microservicestutorial.moviecatalogservice.resources.MovieInfoResource;
import com.microservicestutorial.moviecatalogservice.resources.RatingResource;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getMovieInfoResourceFallback")
    public MovieInfoResource getMovieInfoResource(RatingResource moviesRate) {
        return restTemplate.getForObject("http://movie-info-api/movie/" + moviesRate.getMovieId(), MovieInfoResource.class);
    }

    @FallbackMethod
    private MovieInfoResource getMovieInfoResourceFallback(RatingResource moviesRate) {
        return new MovieInfoResource(-1, "No movie found", "");
    }

}
