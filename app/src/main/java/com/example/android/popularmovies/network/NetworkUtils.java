package com.example.android.popularmovies.network;

import android.net.Uri;

/**
 * Created by banca on 3/27/2017.
 */

public class NetworkUtils {
    static final String MOVIE_DB_API_URL = "https://api.themoviedb.org/3";

    public static Uri buildLatestMoviesUri(){
        Uri latestMoviesUri = Uri.parse(MOVIE_DB_API_URL).buildUpon()
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key", "6ed255df7d148f3b79ed6aa8294812e2")
                .build();

        return latestMoviesUri;
    }

    public static Uri buildLatestMoviesUri(int page){
        Uri latestMoviesUri = Uri.parse(MOVIE_DB_API_URL).buildUpon()
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key", "6ed255df7d148f3b79ed6aa8294812e2")
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        return latestMoviesUri;
    }
}
