package com.doronzehavi.newsitem.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.doronzehavi.newsitem.data.NewsItemContract.*;


public class NewsItemDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "newsitem.db";

    private static final int DATABASE_VERSION = 1;

    public NewsItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + NewsEntry.TABLE_NAME + " (" +

                /*
                 * WeatherEntry did not explicitly declare a column called "_ID". However,
                 * WeatherEntry implements the interface, "BaseColumns", which does have a field
                 * named "_ID". We use that here to designate our table's primary key.
                 */
                        NewsEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        NewsEntry.COLUMN_DATE       + " INTEGER NOT NULL, "                  +

                        NewsEntry.COLUMN_TITLE      + " CHAR NOT NULL, "                     +

                        NewsEntry.COLUMN_AUTHOR     + " CHAR NOT NULL, "                     +

                        NewsEntry.COLUMN_SUMMARY    + " CHAR NOT NULL, "                     +

                        NewsEntry.COLUMN_URL        + " CHAR NOT NULL "                     +

                                                                                            ");";


        db.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NewsEntry.TABLE_NAME);
        onCreate(db);
    }
}
