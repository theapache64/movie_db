package com.theah64.movie_db.servlets;

import com.theah64.movie_db.database.tables.Movies;
import com.theah64.movie_db.database.tables.Requests;
import com.theah64.movie_db.models.Movie;
import com.theah64.movie_db.models.Request;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

/**
 * Created by theapache64 on 30/6/17.
 */
@WebServlet(urlPatterns = {"/search"})
public class SearchServlet extends AdvancedBaseServlet {

    private static final String KEY_KEYWORD = "keyword";
    private static final String[] REQ_PARAMS = {KEY_KEYWORD};
    private Requests reqTab;
    private Request request;

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

        try {
            System.out.println("-----------------------");

            //Getting keyword first
            String keyword = getStringParameter(KEY_KEYWORD);
            keyword = keyword.trim().toLowerCase().trim();

            System.out.println("Search: " + keyword);

            reqTab = Requests.getInstance();
            final Movies movTab = Movies.getInstance();

            //Search if it exist in requests
            request = reqTab.get(Requests.COLUMN_KEYWORD, keyword);

            if (request != null && !request.isExpired()) {

                //keyword exist in db
                if (request.getMovieId() != null) {

                    //request has movie
                    final Movie movie = movTab.get(Movies.COLUMN_ID, request.getMovieId());

                    if (movie.isHasValidRating()) {
                        //Movie has valid rating
                        incrementHits();
                        setResponse(movie);
                    } else {
                        //Movie rating should be updated
                        final String newRating = new IMDBDotComHelper(movie.getImdbUrl()).getRating();
                        movie.setRating(newRating);
                        movTab.updateRating(movie.getId(), newRating);
                        incrementHits();
                        setResponse(movie);
                    }

                } else {
                    //request doesn't have movie
                    incrementHits();
                    showNoMovieExists(keyword);
                }
            } else {


                //keyword doesn't exist in db
                final MovieBuff.IMDB imdbUrl;


                if (keyword.matches("^tt\\d{7}$")) {
                    //It's imdb id
                    System.out.println("Directly building imdb url");
                    imdbUrl = new MovieBuff.IMDB(keyword);
                } else {
                    //Its some text
                    imdbUrl = new MovieBuff().getIMDBUrl(keyword);
                }


                if (imdbUrl != null) {
                    try {

                        //Checking if the movie exist in db
                        final Movie movie = movTab.get(Movies.COLUMN_IMDB_ID, imdbUrl.getId());
                        if (movie != null) {
                            final Request goodReq = new Request(null, keyword, movie.getId(), 1, 1);
                            reqTab.add(goodReq);
                            setResponse(movie);
                        } else {
                            final Movie imdbMovie = new IMDBDotComHelper(imdbUrl.getUrl()).getMovie(imdbUrl.getId());
                            final String movieId = movTab.addv3(imdbMovie);
                            final Request goodReq = new Request(null, keyword, movieId, 1, 1);
                            reqTab.add(goodReq);
                            setResponse(imdbMovie);
                        }
                    } catch (PatternSyntaxException e) {
                        e.printStackTrace();

                        final Request badReq = new Request(null, keyword, null, 1, 1);
                        reqTab.add(badReq);
                        showNoMovieExists(keyword);
                    }
                } else {
                    //No movie exist with the given keyword
                    final Request badReq = new Request(null, keyword, null, 1, 1);
                    reqTab.add(badReq);
                    showNoMovieExists(keyword);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            getWriter().write(new Response(1, e.getMessage()).getResponse());
        }

        System.out.println("-----------------------");
    }

    private void showNoMovieExists(String keyword) throws RequestException {
        throw new RequestException("No movie found with the keyword " + keyword);
    }

    private void incrementHits() throws SQLException {
        reqTab.update(Requests.COLUMN_ID, request.getId(), Requests.COLUMN_HITS, String.valueOf(request.getHits() + 1));
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
