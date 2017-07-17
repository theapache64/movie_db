package com.theah64.movie_db.database.tables;

import com.theah64.movie_db.database.Connection;
import com.theah64.movie_db.models.Movie;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by theapache64 on 12/7/17.
 */
public class Movies {
    private Movies() {
    }

    private static Movies instance;

    public static Movies getInstance() {
        if (instance == null) {
            instance = new Movies();
        }
        return instance;
    }

    public Movie get(String imdbId) {
        Movie movie = null;
        final String query = "SELECT name, rating, genre, plot, posterUrl, year,stars,director FROM movies WHERE imdb_id = ?";
        return movie;
    }

    public void add(Movie movie) {
        final String query = "INSERT INTO movies ( name, rating, genre, plot, posterUrl, year,stars,director, imdb_id) VALUES (?,?,?,?,?,?,?,?,?);";
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, movie.getName());
            ps.setString(2, movie.getRating());
            ps.setString(3, movie.getGenre());
            ps.setString(4, movie.getPlot());
            ps.setString(5, movie.getPosterUrl());
            ps.setString(6, movie.getYear());
            ps.setString(7, movie.getStars());
            ps.setString(8, movie.getDirector());
            ps.setString(9, movie.getImdbId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Movie getByKeyword(String keyword) {

    }
}
