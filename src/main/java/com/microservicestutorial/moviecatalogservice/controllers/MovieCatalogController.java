package com.microservicestutorial.moviecatalogservice.controllers;

import com.microservicestutorial.moviecatalogservice.resources.MovieCatalogResource;
import com.microservicestutorial.moviecatalogservice.resources.MovieRatingResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @GetMapping(value = "/{id}")
    public MovieCatalogResource getMoviesById(@PathVariable(value = "id") String userId) {

        List<MovieRatingResource> movies = new ArrayList<>();
        movies.add(new MovieRatingResource(0, 5, "Minority Report", "sci fi film with Tom Cruise"));
        movies.add(new MovieRatingResource(1, 5, "Le seigneur des anneaux", "fantasy film based on the book  of Tolkien"));
        movies.add(new MovieRatingResource(2, 3, "Titanic", "romance film wih Leonoardo Di Caprio and Kate Winslet"));
        movies.add(new MovieRatingResource(0, 5, "La ligne verte", "fiction film with Tom Hanks"));

        return new MovieCatalogResource(userId, "Mika T", movies);
    }
}
