package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.popularmovies.network.NetworkUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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

        new FetchMovieTask().execute();
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
                Toast.makeText(this, "Sort by Popularity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort_by_rating:
                Toast.makeText(this, "Sort by Rating", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }

    private void showLoadingIndicator(){
        mDisplay.setVisibility(View.INVISIBLE);
        mLodaingIndicator.setVisibility(View.VISIBLE);
    }

    private void showResults(){
        mLodaingIndicator.setVisibility(View.INVISIBLE);
        mDisplay.setVisibility(View.VISIBLE);
    }

    private void showError(){
        mDisplay.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
    }

    class FetchMovieTask extends AsyncTask<Void, Void, String>{


        @Override
        protected void onPreExecute() {
            showLoadingIndicator();
        }

        @Override
        protected void onPostExecute(String result) {
            if(result != null && !result.equals("")) {
                mDisplay.setText(result);
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
        protected String doInBackground(Void... params) {
            HttpURLConnection connection = null;
            URL movieApi = null;
            String result = null;

            try {
                movieApi = new URL(NetworkUtils.buildLatestMoviesUri().toString());

                connection = (HttpURLConnection) movieApi.openConnection();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                result = response.toString();
            }
            catch (MalformedURLException ex){
                ex.printStackTrace();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            return result;
        }
    }
}
