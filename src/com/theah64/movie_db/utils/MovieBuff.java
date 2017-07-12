package com.theah64.movie_db.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shifar Shifz on 8/31/2015 12:04 PM.
 */
public class MovieBuff {

    private static final String FAKE_USER_AGENT = "ExampleBot 1.0 (+http://example.com/bot)";
    private static final String KEY_IMDB_URL = "imdbUrl";
    private static final String GOOGLE_SEARCH_URL_FORMAT = "http://google.com/search?q=%s%%20imdb%%20rating";
    private static final Pattern IMDB_URL_PATTERN = Pattern.compile("(?<imdbUrl>imdb\\.com\\/title\\/(?<imdbId>tt\\d{7}))");
    private static final String KEY_IMDB_ID = "imdbId";


    public static class IMDB {
        private final String url, id;

        public IMDB(String url, String id) {
            this.url = url;
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public String getId() {
            return id;
        }
    }

    public IMDB getIMDBUrl(final String keyword) {

        //Un-optimized code - starts
        final String googleUrlFormat;
        try {
            googleUrlFormat = String.format(GOOGLE_SEARCH_URL_FORMAT, URLEncoder.encode(keyword, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        final String googleData = getNetworkResponse(googleUrlFormat, true);

        //Checking if data is downloader
        if (googleData == null) {
            //Failed to downlaod data from google
            return null;
        }

        //Finding imdb url from google response.
        final Matcher imdbUrlMatcher = IMDB_URL_PATTERN.matcher(googleData);

        if (imdbUrlMatcher.find()) {
            //Converting url to http instead of www
            final String url = String.format("http://%s", imdbUrlMatcher.group(KEY_IMDB_URL));
            final String imdbId = imdbUrlMatcher.group(KEY_IMDB_ID);
            return new IMDB(url, imdbId);
        }


        return null;
    }

    /**
     * Used to get live network response of the given url
     *
     * @param urlString A valid url.
     * @return String The response.
     */
    private static String getNetworkResponse(final String urlString, final boolean isFakeUserAgent) {

        try {


            System.out.println("Accessing : " + urlString);

            //Creating url object
            URL urlOb = new URL(urlString);


            HttpURLConnection con = (HttpURLConnection) urlOb.openConnection();

            //Faking user-agent to mock google.
            if (isFakeUserAgent) {
                con.addRequestProperty("User-Agent", FAKE_USER_AGENT);
            }

            //Downloading response
            final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            //Closing network resource
            br.close();

            if (sb.length() > 0) {
                return sb.toString();
            }

        } catch (IOException e) {
            //Something went wrong
            e.printStackTrace();
        }

        //The response is empty
        return null;
    }

}
