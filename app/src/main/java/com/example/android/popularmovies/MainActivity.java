package com.example.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.network.MovieDbJsonUtils;
import com.example.android.popularmovies.network.NetworkUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private TextView mDisplay, mError;

    private ProgressBar mLodaingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplay = (TextView) findViewById(R.id.tv_display);
        mLodaingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mError = (TextView) findViewById(R.id.error_message);

        new FetchMovieTask().execute(NetworkUtils.buildNowPlayingMoviesUri());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

//        outState.putParcelableArrayList("", null);
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
                new FetchMovieTask().execute(NetworkUtils.buildHighestRatedrMoviesUri());
                break;
            default:
                break;
        }

        return true;
    }

    private void showLoadingIndicator(){
        mDisplay.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mLodaingIndicator.setVisibility(View.VISIBLE);
    }

    private void showResults(){
        mError.setVisibility(View.INVISIBLE);
        mDisplay.setVisibility(View.VISIBLE);
    }

    private void showError(){
        mDisplay.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
    }

    class FetchMovieTask extends AsyncTask<Uri, Void, String[]>{

        @Override
        protected void onPreExecute() {
            showLoadingIndicator();
        }

        @Override
        protected void onPostExecute(String[] result) {
            mLodaingIndicator.setVisibility(View.INVISIBLE);
            if(result != null && !result.equals("")) {
                mDisplay.setText("");

                for(String movie : result)
                    mDisplay.append(movie + "\n\n");

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
            HttpURLConnection connection = null;
            URL movieApiUrl = null;
            String result = null;
            String[] movies = null;

            try {
                movieApiUrl = new URL(params[0].toString());

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

                movies = MovieDbJsonUtils.getJsonFromResponse(result);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return movies;
        }
    }
}
