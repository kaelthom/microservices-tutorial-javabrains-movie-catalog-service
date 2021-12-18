package com.microservicestutorial.moviecatalogservice.controllers;

import com.microservicestutorial.moviecatalogservice.resources.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder builder;

    @GetMapping(value = "/{id}")
    public MovieCatalogResource getMoviesById(@PathVariable(value = "id") String userId) {

        UserRatingsResource userRatingsResource = getUserRatings(userId);

        List<MovieRatingResource> movies = userRatingsResource.getRatings().stream()
                .map(moviesRate -> {
                    MovieInfoResource movieInfoResource = getMovieInfoResource(moviesRate);
                    return new MovieRatingResource(movieInfoResource.getId(), moviesRate.getRate(), movieInfoResource.getOriginal_title(), movieInfoResource.getOverview());
                })
                .collect(Collectors.toList());

        return new MovieCatalogResource(userId, "Mika T", movies);
    }

    @HystrixCommand(fallbackMethod = "getMovieInfoResourceFallback")
    private MovieInfoResource getMovieInfoResource(RatingResource moviesRate) {
        return restTemplate.getForObject("http://movie-info-api/movie/" + moviesRate.getMovieId(), MovieInfoResource.class);
    }

    @HystrixCommand(fallbackMethod = "getUserRatingsFallback")
    private UserRatingsResource getUserRatings(String userId) {
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
