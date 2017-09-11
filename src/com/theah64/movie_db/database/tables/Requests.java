package com.theah64.movie_db.database.tables;


import com.theah64.movie_db.database.Connection;
import com.theah64.movie_db.database.tables.base.BaseTable;
import com.theah64.movie_db.models.Request;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shifar on 24/12/15.
 */
public class Requests extends BaseTable<Request> {

    public static final String COLUMN_KEYWORD = "keyword";
    private static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_HITS = "hits";


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
        final String query = "INSERT INTO requests (keyword) VALUES (?);";
        String error = null;
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, request.getKeyword());
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
        final String query = String.format("SELECT id, keyword, movie_id, hits FROM requests WHERE %s = ? LIMIT 1", column);
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

                request = new Request(id, keyword, movieId, hits);
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
}
