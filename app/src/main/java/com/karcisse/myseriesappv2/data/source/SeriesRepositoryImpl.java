package com.karcisse.myseriesappv2.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.data.Series;
import com.karcisse.myseriesappv2.data.source.local.SeriesDataSource;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SeriesRepositoryImpl implements SeriesRepository {

    @NonNull private final SeriesDataSource seriesDataSource;

    public SeriesRepositoryImpl(@NonNull SeriesDataSource seriesDataSource) {
        this.seriesDataSource = seriesDataSource;
    }

    @Override
    public void saveSeries(Series series) {
        seriesDataSource.saveSeries(series);
    }

    @Nullable
    @Override
    public Series getSeries(@NonNull String seriesId) {
        return seriesDataSource.getSeries(seriesId);
    }

    @NonNull
    @Override
    public List<Series> getSeriesByStatus(@NonNull Series.Status status) {
        List<Series> seriesList = seriesDataSource.getSeriesByStatus(status);

        Collections.sort(seriesList, new Comparator<Series>() {
            @Override
            public int compare(Series o1, Series o2) {
                return o1.getSeriesTitle().compareTo(o2.getSeriesTitle());
            }
        });

        return seriesList;
    }

    @Override
    public void deleteSeries(@NonNull String seriesId) {
        seriesDataSource.deleteSeries(seriesId);
    }

    @NonNull
    @Override
    public List<Series> searchForSeries(@NonNull String searchQuery) {
        return seriesDataSource.searchForSeries(searchQuery);
    }
}
