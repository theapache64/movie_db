package com.theah64.movie_db.models;

/**
 * Created by theapache64 on 2/7/17.
 */
public class Hiztory {
    private final String keyword, error;

    public Hiztory(String keyword, String error) {
        this.keyword = keyword;
        this.error = error;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getError() {
        return error;
    }
}
