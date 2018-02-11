package com.theah64.movie_db.utils;

import java.text.NumberFormat;

/**
 * Created by theapache64 on 11/2/18.
 */
public class StringUtils {
    public static String toIndianNumber(long totalHits) {
        StringBuilder number = new StringBuilder(String.valueOf(totalHits));
        if (totalHits != -1) {

            final int totalLength = number.length();

            if (totalLength >= 4) {
                //Format it
                switch (totalLength) {

                    case 4:
                        return number.insert(1, ",").toString();

                    case 5:
                        return number.insert(2, ",").toString();


                    case 6:
                        return number.insert(1, ",").insert(4, ",").toString();


                    case 7:
                        return number.insert(2, ",").insert(6, ",").toString();


                    case 8:
                        return number.insert(1, ",").insert(4, ",").insert(7, ",").toString();


                    case 9:
                        return number.insert(2, ",").insert(5, ",").insert(8, ",").toString();

                    default:
                        return NumberFormat.getInstance().format(totalHits);

                }
            }
        }
        return number.toString();
    }
}
