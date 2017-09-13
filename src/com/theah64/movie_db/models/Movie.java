package com.theah64.movie_db.models;

/**
 * Created by shifar on 14/12/15.
 */
public class Movie {

    public static final String IMDB_URL_FORMAT = "http://www.imdb.com/title/%s/";
    private final String id;
    private final String name;
    private String rating;
    private final String genre;
    private final String plot;
    private final String posterUrl;
    private final String year;
    private final String stars;
    private final String director;
    private final String imdbId;
    private final boolean hasValidRating;
    private final String imdbUrl;

    public Movie(String id, String name, String rating, String genre, String plot, String posterUrl, String year, String stars, String director, String imdbId, boolean hasValidRating) {
        this.id = id;
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
        this.imdbUrl = String.format(IMDB_URL_FORMAT, imdbId);
    }

    public String getId() {
        return id;
    }

    public String getImdbUrl() {
        return imdbUrl;
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

    public void setRating(String rating) {
        this.rating = rating;
    }
}
