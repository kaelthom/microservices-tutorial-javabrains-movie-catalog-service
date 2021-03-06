package com.microservicestutorial.moviecatalogservice.controllers;

import com.microservicestutorial.moviecatalogservice.resources.MovieCatalogResource;
import com.microservicestutorial.moviecatalogservice.resources.MovieInfoResource;
import com.microservicestutorial.moviecatalogservice.resources.MovieRatingResource;
import com.microservicestutorial.moviecatalogservice.resources.UserRatingsResource;
import com.microservicestutorial.moviecatalogservice.services.MovieInfoService;
import com.microservicestutorial.moviecatalogservice.services.UserRatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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
    private UserRatingsService userRatingsService;

    @Autowired
    private MovieInfoService movieInfoService;

    @Autowired
    private WebClient.Builder builder;

    @GetMapping(value = "/{id}")
    public MovieCatalogResource getMoviesById(@PathVariable(value = "id") String userId) {

        UserRatingsResource userRatingsResource = userRatingsService.getUserRatings(userId);

        List<MovieRatingResource> movies = userRatingsResource.getRatings().stream()
                .map(moviesRate -> {
                    MovieInfoResource movieInfoResource = movieInfoService.getMovieInfoResource(moviesRate);
                    return new MovieRatingResource(movieInfoResource.getId(), moviesRate.getRate(), movieInfoResource.getOriginal_title(), movieInfoResource.getOverview());
                })
                .collect(Collectors.toList());

        return new MovieCatalogResource(userId, "Mika T", movies);
    }

}
