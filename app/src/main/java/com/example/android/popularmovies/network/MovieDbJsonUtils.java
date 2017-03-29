package com.example.android.popularmovies.network;

/**
 * Created by Juan on 3/28/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MovieDbJsonUtils {


    public static String[] getJsonFromResponse(String response) throws JSONException{
        if(response == null)
            return null;

        JSONObject responseObj = new JSONObject(response);
        JSONArray movieResults = responseObj.getJSONArray("results");

        String[] movieData = new String[movieResults.length()];

        for (int i = 0; i < movieData.length; i++){
            JSONObject movie = movieResults.getJSONObject(i);

            movieData[i] = String.valueOf(movie.getInt("id")) + "-" + movie.getString("title");
        }

        return movieData;
    }

}
