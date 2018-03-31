package com.karcisse.myseriesappv2.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

public interface SeriesDataSource {

    @NonNull
    List<Series> getSeriesByStatus(@NonNull Series.Status status);

    @Nullable
    Series getSeries(@NonNull String seriesId);

    void saveSeries(@NonNull Series series);

    void deleteSeries(@NonNull String seriesId);

    @NonNull
    List<Series> searchForSeries(@NonNull String searchQuery);
}
