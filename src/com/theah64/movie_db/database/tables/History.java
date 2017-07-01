package com.theah64.movie_db.database.tables;


import com.theah64.movie_db.database.Connection;
import com.theah64.movie_db.models.Hiztory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by shifar on 24/12/15.
 */
public class History {

    private History() {
    }

    private static History instance;

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    public void add(Hiztory hiztory) {
        final String query = "INSERT INTO history (keyword, error) VALUES (?,?);";
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, hiztory.getKeyword());
            ps.setString(2, hiztory.getError());
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


}
