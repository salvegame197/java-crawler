package com.crawler.crawler.service;

import com.crawler.crawler.model.Comment;
import com.crawler.crawler.model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class CrawlerServiceRep implements CrawlerService {

    @Value("${PRIMARY_URL}")
    private String primaryURL;

    @Override
    public List<Movie> getWorstsMovies() {

        List <Movie> movies = new ArrayList<>();
        getMovies(movies);
        return movies;
    }

    private void getMovies(List<Movie> movies) {
        try {

            List<Movie> moviesTemp = new ArrayList<>();

            String MOVIES_URL = "https://www.imdb.com/chart/bottom?sort=rk,asc&mode=simple&page=1";
            Document doc = Jsoup.connect(this.primaryURL).header("Accept-Language", "en").get();

            Elements titles = doc.select("td.titleColumn");
            Elements ratings = doc.select("td.ratingColumn.imdbRating");

            for (int i = 0; i < titles.size(); i++) {
                Element title = titles.get(i);
                Element rating = ratings.get(i);

                Movie movie = new Movie();
                movie.setId((long) i);
                movie.setTitle(title.select("a").text());
                movie.setRating(rating.text());

                setSanitizedURL(title.select("a").attr("href"), movie);

                moviesTemp.add(movie);
            }


            for (int i = 9; i >= 0; i--) {
                movies.add(moviesTemp.toArray(new Movie[0])[i]);
            }

            movies.forEach(movie -> {
                setBestComment(movie);
                setMovieUrlInformation(movie);
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setBestComment(Movie movie) {
        try {
            String listURL = "https://www.imdb.com/" + movie.getURL() + "reviews?sort=userRating&dir=desc&ratingFilter=0";
            Document doc = Jsoup.connect(listURL).header("Accept-Language", "en").get();

            Elements comments = doc.select("div.review-container");
            Elements ratings = doc.select("span.rating-other-user-rating");

            for (int i = 0; i < comments.size(); i++) {
                Boolean isBestComment = false;
                Integer ratingValue = 0;
                for (Element rating : ratings.get(i).select("span")) {
                    if (rating.text().contains("/")) {
                        continue;
                    }
                    ratingValue = Integer.parseInt(rating.text());
                    if (ratingValue >= 5) {
                        isBestComment = true;
                        break;
                    }

                }
                if (isBestComment) {
                    Comment comment = new Comment();
                    comment.setId((long) i);
                    comment.setComment(comments.get(i).select("div.content > div.text.show-more__control").text());
                    comment.setRating(ratingValue);
                    movie.setComments(comment);
                    break;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setSanitizedURL(String url, Movie movie) {
        String sanitizedURL = url.substring(0, url.indexOf("?"));
        movie.setURL(sanitizedURL);
    }

    private void setMovieUrlInformation(Movie movie) {
        try {
            Document doc = Jsoup.connect("https://www.imdb.com/" + movie.getURL()).header("Accept-Language", "en").get();

            setCasts(movie, doc);
            setDirectorsNames(movie, doc);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setCasts(Movie movie, Document doc) {

        Elements casts = doc.select("a.sc-36c36dd0-1.QSQgP");

        for (Element cast : casts) {
            movie.setCast(cast.text());
        }

    }

    private void setDirectorsNames(Movie movie, Document doc) {
        Elements elements = doc.select("span.ipc-metadata-list-item__label");

        for (Element element : elements) {

            if (element.text().contains("Directors")) {
                movie.setDirector(element.nextElementSibling().text());
                break;
            }

            if (element.text().contains("Director")) {
                movie.setDirector(element.nextElementSibling().text());
                break;
            }
        }
    }
}
