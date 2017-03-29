package com.example.android.popularmovies.network;

import android.net.Uri;

/**
 * Created by banca on 3/27/2017.
 */

public class NetworkUtils {
    static final String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3";
    static final String MOVIE_PATH = "movie";
    private static final String API_KEY = "";

    public static Uri buildNowPlayingMoviesUri(){
        Uri latestMoviesUri = Uri.parse(MOVIE_DB_API_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath("now_playing")
                .appendQueryParameter("api_key", API_KEY)
                .build();

        return latestMoviesUri;
    }

    public static Uri buildNowPlayingMoviesUri(int page){
        Uri latestMoviesUri = Uri.parse(MOVIE_DB_API_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath("now_playing")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        return latestMoviesUri;
    }

    public static Uri buildMostPopularMoviesUri(){
        Uri mostPopularMoviesUri = Uri.parse(MOVIE_DB_API_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath("popular")
                .appendQueryParameter("api_key", API_KEY)
                .build();

        return mostPopularMoviesUri;
    }

    public static Uri buildMostPopularMoviesUri(int page){
        Uri mostPopularMoviesUri = Uri.parse(MOVIE_DB_API_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath("popular")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        return mostPopularMoviesUri;
    }

    public static Uri buildHighestRatedrMoviesUri(){
        Uri mostPopularMoviesUri = Uri.parse(MOVIE_DB_API_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath("top_rated")
                .appendQueryParameter("api_key", API_KEY)
                .build();

        return mostPopularMoviesUri;
    }

    public static Uri buildHighestRatedMoviesUri(int page){
        Uri mostPopularMoviesUri = Uri.parse(MOVIE_DB_API_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath("top_rated")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

        return mostPopularMoviesUri;
    }
}
