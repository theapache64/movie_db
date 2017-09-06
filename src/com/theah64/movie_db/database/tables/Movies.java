package com.theah64.movie_db.database.tables;

import com.theah64.movie_db.database.Connection;
import com.theah64.movie_db.database.tables.base.BaseTable;
import com.theah64.movie_db.models.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by theapache64 on 12/7/17.
 */
public class Movies extends BaseTable<Movie> {

    private static final String COLUMN_IMDB_ID = "imdb_id";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_PLOT = "plot";
    private static final String COLUMN_POSTER_URL = "poster_url";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";
    private static final String COLUMN_DIRECTOR = "director";
    private static final String COLUMN_AS_UPDATED_DAYS_BEFORE = "";
    private static final int MAXIMUM_MOVIE_RATING_EXPIRATION_IN_DAYS = 10;
    private static Movies instance;

    public Movies() {
        super("movies");
    }

    public static Movies getInstance() {
        if (instance == null) {
            instance = new Movies();
        }
        return instance;
    }

    @Override
    public Movie get(String column, String value) {
        Movie movie = null;

        final String query = String.format("SELECT imdb_id, name, rating, genre,plot,poster_url,year,stars,director, DATEDIFF(now(),updated_at) AS updated_days_before FROM movies WHERE %s = ?", column);
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, value);

            final ResultSet rs = ps.executeQuery();

            if (rs.first()) {

                final String imdbId = rs.getString(COLUMN_IMDB_ID);
                final String name = rs.getString(COLUMN_NAME);
                final String rating = rs.getString(COLUMN_RATING);
                final String genre = rs.getString(COLUMN_GENRE);
                final String plot = rs.getString(COLUMN_PLOT);
                final String posterUrl = rs.getString(COLUMN_POSTER_URL);
                final String year = rs.getString(COLUMN_YEAR);
                final String stars = rs.getString(COLUMN_STARS);
                final String director = rs.getString(COLUMN_DIRECTOR);
                final int updatedDaysBefore = rs.getInt(COLUMN_AS_UPDATED_DAYS_BEFORE);

                movie = new Movie(name, rating, genre, plot, posterUrl, year, stars, director, imdbId, updatedDaysBefore <= MAXIMUM_MOVIE_RATING_EXPIRATION_IN_DAYS);
            }

            rs.close();
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
        return movie;
    }

    @Override
    public boolean add(Movie movie) throws SQLException {
        final String query = "INSERT INTO movies ( name, rating, genre, plot, poster_url, year,stars,director, imdb_id) VALUES (?,?,?,?,?,?,?,?,?);";
        String error = null;
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
            error = e.getMessage();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return manageError(error);
    }

    public Movie getByKeyword(String keyword) {
        return null;
    }
}
