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

        System.out.println("-----------------------");

        //Getting keyword first
        String keyword = getStringParameter(KEY_KEYWORD);
        keyword = keyword.trim().toLowerCase();

        System.out.println("Search: " + keyword);

        try {

            reqTab = Requests.getInstance();
            final Movies movTab = Movies.getInstance();

            //Search if it exist in requests
            request = reqTab.get(Requests.COLUMN_KEYWORD, keyword);

            if (request != null) {
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
                        movTab.update(Movies.COLUMN_ID, movie.getId(), Movies.COLUMN_RATING, newRating);
                        incrementHits();
                        setResponse(movie);
                    }

                } else {
                    //request doesn't have movie
                }
            } else {
                //keyword doesn't exist in db
            }

            //MovieBuff knows the imdb url
            final MovieBuff.IMDB imdb = new MovieBuff().getIMDBUrl(keyword);

            final Movies moviesTable = Movies.getInstance();

            final Movie keywordMovie = moviesTable.getByKeyword(keyword);

            if (keywordMovie == null) {

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

                            if (movie != null) {
                                movies.add(movie);
                                Requests.getInstance().add(new Request(keyword, null));
                                setResponse(movie);
                            } else {
                                throw new RequestException("Movie not found");
                            }
                        } else {
                            History.getInstance().add(new Hiztory(keyword, null));
                            setResponse(dbMovie);
                        }

                    } else {
                        Requests.getInstance().add(new Request(keyword, null));
                        setResponse(dbMovie);
                        //No movie exist with the name
                        throw new RequestException("Invalid search");
                    }

                } else {
                    setResponse(keywordMovie);
                }

            } catch(RequestException e){
                Requests.getInstance().add(new Request(keyword, e.getMessage()));
                throw e;
            }


            System.out.println("-----------------------");

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
