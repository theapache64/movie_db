package com.theah64.movie_db.models;

/**
 * Created by shifar on 14/12/15.
 */
public class Movie {

    private final String name, rating, genre, plot, posterUrl;

    public Movie(String name, String rating, String genre, String plot, String posterUrl) {
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.plot = plot;
        this.posterUrl = posterUrl;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlot() {
        return plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}
