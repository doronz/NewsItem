package com.doronzehavi.newsitem.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.doronzehavi.newsitem.data.NewsItemContract;
import com.doronzehavi.newsitem.utilities.JsonUtils;
import com.doronzehavi.newsitem.utilities.NetworkUtils;


import java.net.URL;

/**
 * Created by doronzehavi on 3/6/17.
 */

public class NewsItemSyncTask {


    /**
     * Performs the network request for updated news items, parses the JSON from that request, and
     * inserts the new weather information into our ContentProvider.
     *
     * @param context Used to access utility methods and the ContentResolver
     */
    synchronized public static void syncNewsItems(Context context) {
        try {
            /*
             * The getUrl method will return the URL that we need to get the forecast JSON for the
             * weather. It will decide whether to create a URL based off of the latitude and
             * longitude or off of a simple location as a String.
             */
            URL newsItemRequestUrl = NetworkUtils.getUrl();

            /* Use the URL to retrieve the JSON */
            String jsonNewsItemResponse = NetworkUtils.getResponseFromHttpUrl(newsItemRequestUrl);


            /* Parse the JSON into a list of news items  */
            ContentValues[] newsItemValues = JsonUtils
                        .getNewsItemContentValuesFromJson(jsonNewsItemResponse);


            if (newsItemValues != null && newsItemValues.length != 0) {
                ContentResolver newsItemContentResolver = context.getContentResolver();

                newsItemContentResolver.delete(
                        NewsItemContract.NewsEntry.CONTENT_URI,
                        null,
                        null);

                /* Insert our new weather data into Sunshine's ContentProvider */
                newsItemContentResolver.bulkInsert(
                        NewsItemContract.NewsEntry.CONTENT_URI,
                        newsItemValues);

            }
        }
        catch (Exception e) {
            /* Server probably invalid */
            e.printStackTrace();
        }
    }
}

