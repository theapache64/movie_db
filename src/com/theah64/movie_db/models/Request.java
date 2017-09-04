package com.theah64.movie_db.models;

/**
 * Created by theapache64 on 2/7/17.
 */
public class Request {
    private final String id, keyword, movieId;
    private final int hits;

    public Request(String id, String keyword, String movieId, int hits) {
        this.id = id;
        this.keyword = keyword;
        this.movieId = movieId;
        this.hits = hits;
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
}
