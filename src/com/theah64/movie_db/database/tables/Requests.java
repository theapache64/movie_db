package com.theah64.movie_db.database.tables;


import com.theah64.movie_db.database.Connection;
import com.theah64.movie_db.database.tables.base.BaseTable;
import com.theah64.movie_db.models.Request;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by shifar on 24/12/15.
 */
public class Requests extends BaseTable<Request> {

    public static final String COLUMN_KEYWORD = "keyword";
    private static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_HITS = "hits";
    private static final String COLUMN_AS_CREATED_DAYS_BEFORE = "created_days_before";


    private static Requests instance;

    private Requests() {
        super("requests");
    }

    public static Requests getInstance() {
        if (instance == null) {
            instance = new Requests();
        }
        return instance;
    }

    @Override
    public boolean add(Request request) throws SQLException {
        final String query = "INSERT INTO requests (keyword,movie_id) VALUES (?,?);";
        String error = null;
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, request.getKeyword());
            ps.setString(2, request.getMovieId());
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

    @Override
    public Request get(String column, String value) {
        final String query = String.format("SELECT id, keyword, movie_id, hits,  DATEDIFF(now(),created_at) AS created_days_before FROM requests WHERE %s = ? ORDER BY id DESC LIMIT 1", column);
        final java.sql.Connection con = Connection.getConnection();
        Request request = null;
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, value);
            final ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                final String id = rs.getString(COLUMN_ID);
                final String keyword = rs.getString(COLUMN_KEYWORD);
                final String movieId = rs.getString(COLUMN_MOVIE_ID);
                final int hits = rs.getInt(COLUMN_HITS);

                final int createdDaysBefore = rs.getInt(COLUMN_AS_CREATED_DAYS_BEFORE);

                request = new Request(id, keyword, movieId, hits, createdDaysBefore);
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
        return request;
    }

    public long getTotalHits() {

        final String query = "SELECT SUM(hits) FROM requests;";
        long count = -1;

        final java.sql.Connection con = Connection.getConnection();
        try {
            final Statement stmt = con.createStatement();
            final ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) {
                count = rs.getLong(1);
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;

    }
}
