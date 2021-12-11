package com.microservicestutorial.moviecatalogservice.controllers;

import com.microservicestutorial.moviecatalogservice.resources.MovieCatalogResource;
import com.microservicestutorial.moviecatalogservice.resources.MovieInfoResource;
import com.microservicestutorial.moviecatalogservice.resources.MovieRatingResource;
import com.microservicestutorial.moviecatalogservice.resources.RatingResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @GetMapping(value = "/{id}")
    public MovieCatalogResource getMoviesById(@PathVariable(value = "id") String userId) {

        RestTemplate restTemplate = new RestTemplate();

        List<RatingResource> moviesRates = Arrays.asList(
                new RatingResource(0, 4),
                new RatingResource(1, 3),
                new RatingResource(2, 5)
        );

        List<MovieRatingResource> movies = moviesRates.stream()
                .map(moviesRate -> {
                    MovieInfoResource movieInfoResource = restTemplate.getForObject("http://localhost:8081/movie/" + moviesRate.getMovieId(), MovieInfoResource.class);
                    return new MovieRatingResource(movieInfoResource.getId(), moviesRate.getRate(), movieInfoResource.getName(), movieInfoResource.getDescription());
                })
                .collect(Collectors.toList());


        return new MovieCatalogResource(userId, "Mika T", movies);
    }
}
