package com.theah64.movie_db.servlets;

import com.theah64.movie_db.database.tables.History;
import com.theah64.movie_db.database.tables.Movies;
import com.theah64.movie_db.models.Hiztory;
import com.theah64.movie_db.models.Movie;
import com.theah64.movie_db.utils.IMDBDotComHelper;
import com.theah64.movie_db.utils.MovieBuff;
import com.theah64.movie_db.utils.RequestException;
import com.theah64.movie_db.utils.Response;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by theapache64 on 30/6/17.
 */
@WebServlet(urlPatterns = {"/search"})
public class SearchServlet extends AdvancedBaseServlet {

    private static final String KEY_KEYWORD = "keyword";
    private static final String[] REQ_PARAMS = {KEY_KEYWORD};

    @Override
    protected String[] getRequiredParameters() {
        return REQ_PARAMS;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doAdvancedPost() throws JSONException, SQLException, RequestException, IOException, ServletException {

        System.out.println("-----------------------");

        //Getting keyword first
        final String keyword = getStringParameter(KEY_KEYWORD);
        System.out.println("Search: " + keyword);

        try {

            //MovieBuff knows the imdb url
            final MovieBuff.IMDB imdb = new MovieBuff().getIMDBUrl(keyword);

            if (imdb != null) {

                final Movies movies = Movies.getInstance();
                final Movie dbMovie = movies.get(imdb.getId());

                if (dbMovie == null) {

                    System.out.println(imdb.getUrl());

                    final HttpURLConnection con = (HttpURLConnection) new URL(imdb.getUrl()).openConnection();

                    if (con.getResponseCode() == 200) {

                        final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        final StringBuilder sb = new StringBuilder();

                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line.trim());
                        }

                        br.close();

                        final IMDBDotComHelper imdbHelper = new IMDBDotComHelper(sb.toString());
                        final Movie movie = imdbHelper.getMovie(imdb.getId());

                        if (movie != null) {
                            movies.add(movie);
                            History.getInstance().add(new Hiztory(keyword, null));
                            setResponse(movie);
                        } else {
                            throw new RequestException("Something went wrong while collecting movie details from imdb database");
                        }

                    } else {
                        throw new RequestException("Movie not found");
                    }
                } else {
                    History.getInstance().add(new Hiztory(keyword, null));
                    setResponse(dbMovie);
                }

            } else {
                //No movie exist with the name
                throw new RequestException("Invalid search");
            }
        } catch (RequestException e) {
            History.getInstance().add(new Hiztory(keyword, e.getMessage()));
            throw e;
        }


        System.out.println("-----------------------");

    }

    private void setResponse(Movie movie) throws JSONException {
        final JSONObject joMovie = new JSONObject();
        joMovie.put("name", movie.getName());
        joMovie.put("rating", movie.getRating());
        joMovie.put("genre", movie.getGenre());
        joMovie.put("plot", movie.getPlot());
        joMovie.put("poster_url", movie.getPosterUrl());
        joMovie.put("stars", movie.getStars());
        joMovie.put("year", movie.getYear());
        joMovie.put("director", movie.getDirector());
        joMovie.put("imdb_id", movie.getImdbId());

        getWriter().write(new Response("Movie found", joMovie).getResponse());
    }
}
