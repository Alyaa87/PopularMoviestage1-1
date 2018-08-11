package com.example.android.popularmoviestage1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Adapter mMovieAdapter;
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String POSTER_IMAGE = "poster_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }
//AsyncTask to fetch movie Data.
    private class AsynTaskMethod extends AsyncTask<String, String, String> {
    ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
    HttpURLConnection conn;
    URL url ;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            url = new URL("https://api.themoviedb.org/3/movie/550?api_key=0baa0dbb0893e2930c6885d76a3d4d66");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        }
        try {
            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");
            // setDoOutput to true as we recieve data from json file
            conn.setDoOutput(true);
        } catch (IOException e1) {
            e1.printStackTrace();
            return e1.toString();
        }
        try {
            int response_code = conn.getResponseCode();
            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {
                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                // Pass data to onPostExecute method
                return (result.toString());
            } else {
                return ("unsuccessful");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //this method will be running on UI thread
        pdLoading.dismiss();
        List<MovieData> data = new ArrayList<>();
            pdLoading.dismiss();
        // Extract data from json and store into ArrayList as class objects

            try {
                JSONObject movieJson = new JSONObject(result);
                for (int i = 1; i<movieJson.length(); i++) {
                    JSONObject jObject =movieJson.getJSONObject(String.valueOf(i));
                    MovieData movieData = new MovieData();
                    movieData.original_title = movieJson.getString("original_title");
                    movieData.poster_image = movieJson.getString("poster_path");
                    data.add(movieData);
                }
                // Setup and Handover data to recyclerview and the adapter
                mMovieAdapter = (Adapter) new MovieAdapter(MainActivity.this, data);
                mRecyclerView.setAdapter((RecyclerView.Adapter) mMovieAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}