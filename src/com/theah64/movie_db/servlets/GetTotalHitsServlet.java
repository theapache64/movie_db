package com.theah64.movie_db.servlets;

import com.theah64.movie_db.database.tables.Requests;
import com.theah64.movie_db.utils.RequestException;
import com.theah64.movie_db.utils.Response;
import com.theah64.movie_db.utils.StringUtils;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by theapache64 on 11/2/18.
 */
@WebServlet(urlPatterns = "/get_total_hits")
public class GetTotalHitsServlet extends AdvancedBaseServlet {
    @Override
    protected String[] getRequiredParameters() {
        return new String[0];
    }

    @Override
    protected void doAdvancedPost() throws JSONException, SQLException, RequestException, IOException, ServletException, RequestException {
        getWriter().write(new Response("OK", "count", StringUtils.toIndianNumber(Requests.getInstance().getTotalHits())).toString());
    }
}
