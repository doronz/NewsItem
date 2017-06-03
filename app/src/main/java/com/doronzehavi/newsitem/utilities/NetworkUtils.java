package com.doronzehavi.newsitem.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String STATIC_BASE_URL =
            "http://www.myownfeed.com/api/v1/newsItems?page=1";

    private static final String NEWS_BASE_URL = STATIC_BASE_URL;

    public static URL getUrl() {
        return buildUrl();
    }

    private static URL buildUrl() {
        Uri newsQueryUri = Uri.parse(NEWS_BASE_URL).buildUpon().build();

        try {
            URL newsQueryUrl = new URL(newsQueryUri.toString());
            Log.v(TAG, "URL: " + newsQueryUrl);
            return newsQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
