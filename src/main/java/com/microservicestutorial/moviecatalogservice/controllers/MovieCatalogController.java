package com.microservicestutorial.moviecatalogservice.controllers;

import com.microservicestutorial.moviecatalogservice.resources.MovieCatalogResource;
import com.microservicestutorial.moviecatalogservice.resources.MovieInfoResource;
import com.microservicestutorial.moviecatalogservice.resources.MovieRatingResource;
import com.microservicestutorial.moviecatalogservice.resources.UserRatingsResource;
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
    private WebClient.Builder builder;

    @GetMapping(value = "/{id}")
    public MovieCatalogResource getMoviesById(@PathVariable(value = "id") String userId) {

        UserRatingsResource userRatingsResource = restTemplate.getForObject("http://movie-ratings-api/movie-rating/user/" + userId, UserRatingsResource.class);

        List<MovieRatingResource> movies = userRatingsResource.getRatings().stream()
                .map(moviesRate -> {
                    MovieInfoResource movieInfoResource = restTemplate.getForObject("http://movie-info-api/movie/" + moviesRate.getMovieId(), MovieInfoResource.class);
                    return new MovieRatingResource(movieInfoResource.getId(), moviesRate.getRate(), movieInfoResource.getName(), movieInfoResource.getDescription());
                })
                .collect(Collectors.toList());

        System.out.println("instances : " + discoveryClient.getInstances("movie-ratings-api"));
        System.out.println("services : " + discoveryClient.getServices());
        return new MovieCatalogResource(userId, "Mika T", movies);
    }
}
