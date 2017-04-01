package com.doronzehavi.newsitem.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by doronzehavi on 3/6/17.
 */

public class NewsItemContract {

    public static final String CONTENT_AUTHORITY = "com.doronzehavi.newsitem";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NEWS = "news";

    public static final class NewsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_NEWS)
                .build();

        public static final String TABLE_NAME = "news";

        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_URL = "url";



    }
}
