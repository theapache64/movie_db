package com.theah64.movie_db.models;

/**
 * Created by shifar on 14/12/15.
 */
public class Movie {

    private final String name, rating, genre, plot, posterUrl, year, stars, director, imdbId;
    private final boolean hasValidRating;

    public Movie(String name, String rating, String genre, String plot, String posterUrl, String year, String stars, String director, String imdbId, boolean hasValidRating) {
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.plot = plot;
        this.posterUrl = posterUrl;
        this.year = year;
        this.stars = stars;
        this.director = director;
        this.imdbId = imdbId;
        this.hasValidRating = hasValidRating;
    }

    public boolean isHasValidRating() {
        return hasValidRating;
    }

    public String getImdbId() {
        return imdbId;
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

    public String getYear() {
        return year;
    }

    public String getStars() {
        return stars;
    }

    public String getDirector() {
        return director;
    }
}
