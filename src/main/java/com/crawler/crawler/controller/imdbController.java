package com.crawler.crawler.controller;

import com.crawler.crawler.model.Movie;
import com.crawler.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class imdbController {

    @Autowired
    CrawlerService crawlerService;

    @GetMapping("")
    List<Movie> index() {
        return crawlerService.getWorstsMovies();
    }

}
