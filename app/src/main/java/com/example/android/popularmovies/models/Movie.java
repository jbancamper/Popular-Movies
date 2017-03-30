package com.example.android.popularmovies.models;


import android.net.Uri;

/**
 * Created by Juan on 3/30/17.
 */

public class Movie {
    public long id;
    public double rating;
    public String title, releaseDate, overview;
    public Uri homepageUri;

    public Movie(long id, double rating, String title, String releaseDate,
                 String overview, String homepageUrl){
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.homepageUri = Uri.parse(homepageUrl);
    }

}
