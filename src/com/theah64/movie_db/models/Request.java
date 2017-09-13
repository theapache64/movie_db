package com.theah64.movie_db.models;

/**
 * Created by theapache64 on 2/7/17.
 */
public class Request {
    private static final int EXPIRY_IN_DAYS = 10;
    private final String id, keyword, movieId;
    private final int hits;
    private boolean isExpired = false;

    public Request(String id, String keyword, String movieId, int hits, int createdDaysBefore) {
        this.id = id;
        this.keyword = keyword;
        this.movieId = movieId;
        this.hits = hits;
        this.isExpired = createdDaysBefore >=EXPIRY_IN_DAYS;
    }

    public String getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getMovieId() {
        return movieId;
    }

    public int getHits() {
        return hits;
    }

    public boolean isExpired() {
        return isExpired;
    }
}
