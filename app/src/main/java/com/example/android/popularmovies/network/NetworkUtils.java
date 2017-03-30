package com.example.android.popularmovies.network;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by banca on 3/27/2017.
 */

public class NetworkUtils {
    static final String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3";
    static final String MOVIE_PATH = "movie";
    private static final String API_KEY = "6ed255df7d148f3b79ed6aa8294812e2";

    public static String getResponseFromWeb(Uri uri) throws IOException{
        HttpURLConnection connection = null;
        URL movieApiUrl = null;
        String result = null;

        movieApiUrl = new URL(uri.toString());

        connection = (HttpURLConnection) movieApiUrl.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        result = response.toString();

        return result;
    }

    public static Uri buildMovieDetailsUri(String movieId){
        Uri movieDetailsUri = Uri.parse(MOVIE_DB_API_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(movieId)
                .appendQueryParameter("api_key", API_KEY)
                .build();

        return movieDetailsUri;
    }

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

    public static Uri buildHighestRatedMoviesUri(){
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

    public static class Images{
        public static final String MOVIE_DB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
//        public static final String POSTER_SIZE_W92 = "w92";
//        public static final String POSTER_SIZE_W154 = "w154";
        public static final String POSTER_SIZE_W500 = "w500";

        Images(){}

        public Uri buildMoviePosterUri(String posterPath){
            Uri movieImageUri = Uri.parse(MOVIE_DB_IMAGE_BASE_URL).buildUpon()
                    .appendPath(POSTER_SIZE_W500)
                    .appendPath(posterPath)
                    .build();

            return movieImageUri;
        }
    }
}
