package com.karcisse.myseriesappv2.data.source.local;

import android.support.annotation.NonNull;

import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

public interface SeriesDataSource {
    List<Series> getSeries();
    List<Series> getSeriesByStatus(Series.Status status);
    Series getSeries(@NonNull String seriesId);
    void saveSeries(@NonNull Series series);
    void deleteSeries(@NonNull String seriesId);

    @NonNull
    List<Series> searchForSeries(@NonNull String searchQuery);
}
