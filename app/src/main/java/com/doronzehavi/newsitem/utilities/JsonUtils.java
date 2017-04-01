package com.doronzehavi.newsitem.utilities;

import android.content.ContentValues;
import android.content.Context;

import com.doronzehavi.newsitem.data.NewsItemContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;


public class JsonUtils {


    private static final String ERROR__CODE = "error_code";
    private static final String NEWS_LIST = "articles";

    private static final String NEWS_API_TITLE = "title";
    private static final String NEWS_API_AUTHOR = "author";
    private static final String NEWS_API_SUMMARY = "description";
    private static final String NEWS_API_URL = "url";
    private static final String NEWS_API_DATE = "publishedAt";

    public static ContentValues[] getNewsItemContentValuesFromJson(String newsItemJsonStr)
            throws JSONException {

        JSONObject newsItemJson = new JSONObject(newsItemJsonStr);

        /* Is there an error? */
        if (newsItemJson.has(ERROR__CODE)) {
            int errorCode = newsItemJson.getInt(ERROR__CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray jsonNewsItemArray = newsItemJson.getJSONArray(NEWS_LIST);

        ContentValues[] newsItemContentValues = new ContentValues[jsonNewsItemArray.length()];

        for (int i = 0; i < jsonNewsItemArray.length(); i++){
            JSONObject newsItemObject = jsonNewsItemArray.getJSONObject(i);

            String date = newsItemObject.getString(NEWS_API_DATE);
            String title = newsItemObject.getString(NEWS_API_TITLE);
            String author = newsItemObject.getString(NEWS_API_AUTHOR);
            String summary = newsItemObject.getString(NEWS_API_SUMMARY);
            String url = newsItemObject.getString(NEWS_API_URL);

            ContentValues newsItemValues = new ContentValues();
            newsItemValues.put(NewsItemContract.NewsEntry.COLUMN_DATE, date);
            newsItemValues.put(NewsItemContract.NewsEntry.COLUMN_TITLE, title);
            newsItemValues.put(NewsItemContract.NewsEntry.COLUMN_AUTHOR, author);
            newsItemValues.put(NewsItemContract.NewsEntry.COLUMN_SUMMARY, summary);
            newsItemValues.put(NewsItemContract.NewsEntry.COLUMN_URL, url);


            newsItemContentValues[i] = newsItemValues;
        }

        // Populate columns here.


        return newsItemContentValues;
    }
}
