package com.example.android.popularmoviestage1;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtilis {
    private static final String TAG = NetworkUtilis.class.getSimpleName();
    private final static String MOVIE_URL ="https://api.themoviedb.org/3/movie/550?api_key=0baa0dbb0893e2930c6885d76a3d4d66";
    private static final String MOVIE_BASE_URL = MOVIE_URL;


    private static final String format = "json";

    final static String QUERY_PARAM = "q";
    final static String FORMAT_PARAM = "mode";
    //build URL method
    public static URL buildUrl (String URL){
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(FORMAT_PARAM , format)
                .appendQueryParameter(QUERY_PARAM , URL)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
//return statement
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getResponseFromHttpUrl() {
        return null ;
    }
}
