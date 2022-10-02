package com.crawler.crawler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Movie {


    private @Id @GeneratedValue Long id;
    public String title;
    public String director;
    public Float rating;
    public List<String> cast;
    public List<Comment> comments;
    private String URL ;

    public Movie() {
        this.cast = new ArrayList<String>();
        this.comments = new ArrayList<Comment>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setRating(String rating)
    {
        float floatRating = Float.parseFloat(rating);
        this.rating = floatRating;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    public void setCast(String cast) {
        this.cast.add(cast);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setComments(Comment comments) {
        this.comments.add(comments);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getTitle() {
        return title;
    }

    public Float getRating() {
        return rating;
    }

    @JsonIgnore
    public String getURL() {
        return URL;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", rating=" + rating +
                ", cast=" + cast +
                ", comments=" + comments +
                ", URL='" + URL + '\'' +
                '}';
    }

}
