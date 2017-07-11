package com.theah64.movie_db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by theapache64 on 11/7/17.
 */
public class Lab {

    private static final String MOVIE_YEAR_REGEX_FORMAT = "<meta property='og:title' content=\"%s \\((.+)\\)\"";
    private static String imdbHtml = "app_id' content='115109575169727' /><meta property='og:title' content=\"Iron Man (2008)\" /><meta property='og:site_name";

    public static void main(String[] args) {
        System.out.println(getYear("Iron Man"));
    }

    public static String getYear(final String movieName) {
        final Pattern yearPattern = Pattern.compile(String.format(MOVIE_YEAR_REGEX_FORMAT, movieName));
        final Matcher matcher = yearPattern.matcher(imdbHtml);
        String year = null;
        if (matcher.find()) {
            year = matcher.group(1);
        }
        return year;
    }

}
