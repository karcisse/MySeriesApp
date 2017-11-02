package com.karcisse.myseriesappv2.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.data.Series;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SeriesLocalDataSource implements SeriesDataSource {

    private static SeriesLocalDataSource instance;

    private SeriesDbHelper dbHelper;

    private SeriesLocalDataSource(@NonNull Context context) {
        this.dbHelper = new SeriesDbHelper(context);
    }

    public static SeriesLocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new SeriesLocalDataSource(context);
        }
        return instance;
    }

    @NonNull
    public List<Series> getSeries() {
        List<Series> seriesList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(SeriesPersistenceContract.SeriesEntry.TABLE_NAME,
                null, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                seriesList.add(getSeriesFromCursor(c));
            }
        }

        if (c != null) {
            c.close();
        }

        db.close();

        return seriesList;
    }

    @Override
    public List<Series> getSeriesByStatus(Series.SeriesStatus status) {
        List<Series> seriesList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = SeriesPersistenceContract.SeriesEntry.SERIES_STATUS + "=?";
        String[] selectionArgs = {status.name()};

        Cursor c = db.query(SeriesPersistenceContract.SeriesEntry.TABLE_NAME,
                null, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                seriesList.add(getSeriesFromCursor(c));
            }
        }

        if (c != null) {
            c.close();
        }

        db.close();

        return seriesList;
    }

    @Nullable
    public Series getSeries(@NonNull String seriesId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Series series = null;

        String selection = SeriesPersistenceContract.SeriesEntry.SERIES_ID + "=?";
        String[] selectionArgs = {seriesId};

        Cursor c = db.query(SeriesPersistenceContract.SeriesEntry.TABLE_NAME,
                null, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() == 1) {
            c.moveToFirst();
            series = getSeriesFromCursor(c);
        }

        if (c != null) {
            c.close();
        }

        db.close();

        return series;
    }

    public void saveSeries(@NonNull Series series) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        String id = series.getId();
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        values.put(SeriesPersistenceContract.SeriesEntry.SERIES_ID, id);
        values.put(SeriesPersistenceContract.SeriesEntry.SERIES_TITLE, series.getSeriesTitle());
        values.put(SeriesPersistenceContract.SeriesEntry.SERIES_SEASON, series.getSeasonNumber());
        values.put(SeriesPersistenceContract.SeriesEntry.SERIES_EPISODE, series.getEpisodeNumber());
        values.put(SeriesPersistenceContract.SeriesEntry.SERIES_STATUS, series.getSeriesStatus().name());

        // TODO: 11.10.17 update
        db.insertWithOnConflict(SeriesPersistenceContract.SeriesEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    @Override
    public void deleteSeries(@NonNull String seriesId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = SeriesPersistenceContract.SeriesEntry.SERIES_ID + "=?";
        String[] selectionArgs = {seriesId};

        db.delete(SeriesPersistenceContract.SeriesEntry.TABLE_NAME, selection, selectionArgs);
    }

    private Series getSeriesFromCursor(Cursor c) {
        String seriesId = c.getString(c.getColumnIndexOrThrow(SeriesPersistenceContract.SeriesEntry.SERIES_ID));
        String title = c.getString(c.getColumnIndexOrThrow(SeriesPersistenceContract.SeriesEntry.SERIES_TITLE));
        Integer season = c.getInt(c.getColumnIndexOrThrow(SeriesPersistenceContract.SeriesEntry.SERIES_SEASON));
        Integer episode = c.getInt(c.getColumnIndexOrThrow(SeriesPersistenceContract.SeriesEntry.SERIES_EPISODE));
        Series.SeriesStatus status = Series.SeriesStatus.valueOf(
                c.getString(c.getColumnIndexOrThrow(SeriesPersistenceContract.SeriesEntry.SERIES_STATUS)));

        return new Series(seriesId, title, season, episode, status);
    }
}
