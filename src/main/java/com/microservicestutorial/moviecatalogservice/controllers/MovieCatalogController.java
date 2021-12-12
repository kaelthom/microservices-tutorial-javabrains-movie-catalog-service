package com.microservicestutorial.moviecatalogservice.controllers;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.microservicestutorial.moviecatalogservice.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder builder;

    @GetMapping(value = "/{id}")
    public MovieCatalogResource getMoviesById(@PathVariable(value = "id") String userId) {

        UserRatingsResource userRatingsResource = restTemplate.getForObject("http://localhost:8083/movie-rating/user/" + userId, UserRatingsResource.class);

        List<MovieRatingResource> movies = userRatingsResource.getRatings().stream()
                .map(moviesRate -> {
                    MovieInfoResource movieInfoResource = restTemplate.getForObject("http://localhost:8081/movie/" + moviesRate.getMovieId(), MovieInfoResource.class);
                    return new MovieRatingResource(movieInfoResource.getId(), moviesRate.getRate(), movieInfoResource.getName(), movieInfoResource.getDescription());
                })
                .collect(Collectors.toList());


        return new MovieCatalogResource(userId, "Mika T", movies);
    }
}
