package com.karcisse.myseriesappv2.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SeriesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Series.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SeriesPersistenceContract.SeriesEntry.TABLE_NAME + " ("
                    + SeriesPersistenceContract.SeriesEntry.SERIES_ID + TEXT_TYPE + " PRIMARY KEY,"
                    + SeriesPersistenceContract.SeriesEntry.SERIES_TITLE + TEXT_TYPE + COMMA_SEP
                    + SeriesPersistenceContract.SeriesEntry.SERIES_SEASON + TEXT_TYPE + COMMA_SEP
                    + SeriesPersistenceContract.SeriesEntry.SERIES_EPISODE + TEXT_TYPE + COMMA_SEP
                    + SeriesPersistenceContract.SeriesEntry.SERIES_STATUS + TEXT_TYPE
                    + " )";

    public SeriesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: 11.10.17 implement if needed
    }
}
