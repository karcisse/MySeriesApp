package com.karcisse.myseriesappv2.data.source;

import android.support.annotation.NonNull;

import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

public interface SeriesRepository {
    void saveSeries(Series series);
    Series getSeries(@NonNull String seriesId);
    List<Series> getSeries();
    List<Series> getSeriesByStatus(Series.Status status);
    void deleteSeries(String seriesId);
}
