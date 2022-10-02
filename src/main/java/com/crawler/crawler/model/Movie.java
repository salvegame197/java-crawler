package com.crawler.crawler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    public String title;
    public String director;
    public Float rating;
    public List<String> cast;
    public List<Comment> comments;
    private @Id
    @GeneratedValue Long id;
    private String URL;

    public Movie() {
        this.cast = new ArrayList<String>();
        this.comments = new ArrayList<Comment>();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast.add(cast);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(Comment comments) {
        this.comments.add(comments);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(String rating) {
        float floatRating = Float.parseFloat(rating);
        this.rating = floatRating;
    }

    @JsonIgnore
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

}
