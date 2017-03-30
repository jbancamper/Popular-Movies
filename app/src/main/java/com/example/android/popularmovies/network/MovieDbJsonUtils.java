package com.example.android.popularmovies.network;

/**
 * Created by Juan on 3/28/17.
 */

import com.example.android.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MovieDbJsonUtils {


    @org.jetbrains.annotations.Contract("null -> null")
    public static String[] getJsonFromResponse(String response) throws JSONException{
        if(response == null)
            return null;

        JSONObject responseObj = new JSONObject(response);
        JSONArray movieResults = responseObj.getJSONArray("results");

        String[] movieData = new String[movieResults.length()];

        NetworkUtils.Images imageHelper = new NetworkUtils.Images();

        for (int i = 0; i < movieData.length; i++){
            JSONObject movie = movieResults.getJSONObject(i);

            String imageUrl = imageHelper.buildMoviePosterUri(movie.getString("poster_path").substring(1))
                    .toString();

            movieData[i] = String.valueOf(movie.getInt("id")) + "-" + movie.getString("title")
                    + "-" + imageUrl;
        }

        return movieData;
    }

    public static Movie getMovieDetailsFromResponse(String response) throws JSONException{
        if (response == null) {
            return null;
        }

        JSONObject movie = new JSONObject(response);

        long id = movie.getLong("id");
        double rating = movie.getDouble("vote_average");
        String title = movie.getString("title"), releaseDate = movie.getString("release_date"),
                overview = movie.getString("overview"), homepageUrl = movie.getString("homepage");

        Movie m = new Movie(id, rating, title, releaseDate, overview, homepageUrl);

        return m;
    }

}
