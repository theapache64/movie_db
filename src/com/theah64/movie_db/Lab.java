package com.theah64.movie_db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by theapache64 on 11/7/17.
 */
public class Lab {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        final String sampleData = "Directed by Alejandro González Iñárritu.  With Leonardo DiCaprio, Tom Hardy, Will Poulter, Domhnall Gleeson. A frontiersman on a fur tra";
        //final String sampleData = "Directed by Jon Favreau. With Robert Downey Jr., Gwyneth Paltrow, Terrence Howard, Jeff Bridges. After being held captive in an Afghan cave, billionaire eng.";

        final String regEx = "Directed by (?<Director>.+)\\.\\s+With (?<stars>.+)\\.";
        final Pattern pattern = Pattern.compile(regEx);
        final Matcher matcher = pattern.matcher(sampleData);
        if (matcher.find()) {
            System.out.println("Director: " + matcher.group("Director"));
            System.out.println("Stars: " + matcher.group("stars"));
        }

    }


}
