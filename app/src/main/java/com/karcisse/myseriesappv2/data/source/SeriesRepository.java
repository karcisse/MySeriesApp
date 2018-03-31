package com.karcisse.myseriesappv2.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

public interface SeriesRepository {
    void saveSeries(Series series);

    @Nullable
    Series getSeries(@NonNull String seriesId);

    @NonNull
    List<Series> getSeriesByStatus(@NonNull Series.Status status);

    void deleteSeries(@NonNull String seriesId);

    @NonNull
    List<Series> searchForSeries(@NonNull String searchQuery);
}
