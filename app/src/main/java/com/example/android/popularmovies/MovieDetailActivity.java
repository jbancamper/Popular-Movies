package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.network.MovieDbJsonUtils;
import com.example.android.popularmovies.network.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView mDetailsTitle, mReleaseDate, mRating, mOverview;
    private ImageView mMoviePoster;

    private String[] movieDetails;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mDetailsTitle = (TextView) findViewById(R.id.tv_movie_detail_title);
        mReleaseDate = (TextView) findViewById(R.id.tv_movie_detail_release_date);
        mRating = (TextView) findViewById(R.id.tv_movie_detail_rating);
        mOverview = (TextView) findViewById(R.id.tv_movie_detail_overview);

        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_detail_poster);

        Intent intent = getIntent();

        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            movieDetails = intent.getStringExtra(Intent.EXTRA_TEXT).split("-");
            new FetchDetailsTask().execute(NetworkUtils.buildMovieDetailsUri(movieDetails[0]));
            getSupportActionBar().setTitle(movieDetails[1]);
            Picasso.with(this).load(movieDetails[2]).into(mMoviePoster);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.share:
                ShareCompat.IntentBuilder.from(this)
                        .setChooserTitle("Share " + movieDetails[1])
                        .setType("text/plain")
                        .setText("You gotta watch " + movieDetails[1] + "! Check it out at "
                                + movie.homepageUri)
                        .startChooser();
                break;
            case R.id.homepage:
                Intent intent = new Intent(Intent.ACTION_VIEW, movie.homepageUri);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
                break;
            default:
                break;
        }

        return true;
    }

    class FetchDetailsTask extends AsyncTask<Uri, Void, Movie>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Movie s) {
            if(s != null) {
                mDetailsTitle.setText("");
                mDetailsTitle.setText(s.title);
                mReleaseDate.setText("");
                mReleaseDate.setText(s.releaseDate);
                mRating.setText("");
                mRating.setText(String.valueOf(s.rating));
                mOverview.setText("");
                mOverview.setText(s.overview);
                movie = s;
            }
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected Movie doInBackground(Uri... params) {
            String result = null;
            Movie movie = null;

            try {
                result = NetworkUtils.getResponseFromWeb(params[0]);

                movie = MovieDbJsonUtils.getMovieDetailsFromResponse(result);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return movie;
        }
    }
}
