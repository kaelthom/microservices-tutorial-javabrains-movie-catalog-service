package com.microservicestutorial.moviecatalogservice.services;

import com.microservicestutorial.moviecatalogservice.controllers.FallbackMethod;
import com.microservicestutorial.moviecatalogservice.resources.RatingResource;
import com.microservicestutorial.moviecatalogservice.resources.UserRatingsResource;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserRatingsService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getUserRatingsFallback", commandProperties =
            {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            },
            threadPoolKey = "userRatingsInfoServiceThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
            }
    )
    public UserRatingsResource getUserRatings(String userId) {
        return restTemplate.getForObject("http://movie-ratings-api/movie-rating/user/" + userId, UserRatingsResource.class);
    }

    @FallbackMethod
    private UserRatingsResource getUserRatingsFallback(String userId) {
        return new UserRatingsResource(-1, Collections.singletonList(new RatingResource(-1, -1)));
    }
}
