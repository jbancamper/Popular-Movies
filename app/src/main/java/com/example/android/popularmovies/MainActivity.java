package com.example.android.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.network.MovieDbJsonUtils;
import com.example.android.popularmovies.network.NetworkUtils;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickListener {
    private TextView mError;

    private ProgressBar mLoadingIndicator;

    private RecyclerView mMovieList;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieList = (RecyclerView) findViewById(R.id.rv_movie_list);
        movieAdapter = new MovieAdapter(this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mMovieList.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            mMovieList.setLayoutManager(new GridLayoutManager(this, 4));
        }

        mMovieList.setAdapter(movieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mError = (TextView) findViewById(R.id.error_message);

        new FetchMovieTask().execute(NetworkUtils.buildNowPlayingMoviesUri());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.sort_by_popularity:
                //Toast.makeText(this, "Sort by Popularity", Toast.LENGTH_SHORT).show();
                new FetchMovieTask().execute(NetworkUtils.buildMostPopularMoviesUri());
                break;
            case R.id.sort_by_rating:
                //Toast.makeText(this, "Sort by Rating", Toast.LENGTH_SHORT).show();
                new FetchMovieTask().execute(NetworkUtils.buildHighestRatedMoviesUri());
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onMovieClick(String movieId){
        //Toast.makeText(this, movieId, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, movieId);

        startActivity(intent);
    }

    private void showLoadingIndicator(){
        mMovieList.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showResults(){
        mError.setVisibility(View.INVISIBLE);
        mMovieList.setVisibility(View.VISIBLE);
    }

    private void showError(){
        mMovieList.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
    }

    class FetchMovieTask extends AsyncTask<Uri, Void, String[]>{

        @Override
        protected void onPreExecute() {
            showLoadingIndicator();
        }

        @Override
        protected void onPostExecute(String[] result) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(result != null && !result.equals("")) {
                movieAdapter.setMovieData(result);
                showResults();
            }
            else
                showError();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String[] doInBackground(Uri... params) {
            String result = null;
            String[] movies = null;

            try {
                result = NetworkUtils.getResponseFromWeb(params[0]);

                movies = MovieDbJsonUtils.getJsonFromResponse(result);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return movies;
        }
    }
}
