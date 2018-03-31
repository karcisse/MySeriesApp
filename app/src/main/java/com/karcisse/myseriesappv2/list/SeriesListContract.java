package com.karcisse.myseriesappv2.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.BasePresenter;
import com.karcisse.myseriesappv2.BaseView;
import com.karcisse.myseriesappv2.data.Series;

public interface SeriesListContract {

    interface View extends BaseView<Presenter> {
        void showSeriesList();
        void showRecordSeries(@NonNull String seriesId);
        void showEditScreen(@NonNull String seriesId);
        void onEmptyList();
        void onNotEmptyList();
    }

    interface Presenter extends BasePresenter {
        void incrementEpisode(@NonNull String seriesId);
        void decrementEpisode(@NonNull String seriesId);
        void incrementSeason(@NonNull String seriesId);
        void decrementSeason(@NonNull String seriesId);
        void changeStatus(@NonNull String seriesId, @NonNull Series.Status status);
        void deleteSeries(@NonNull String seriesId);
        void closeItem(@NonNull String seriesId);
        void openItem(@Nullable String seriesId);
        void showRecordSeries(@NonNull String seriesId);
        boolean isRowEdited(@Nullable String id);
        int getDataSize();
        void showEditScreen(@NonNull String seriesId);
        @Nullable
        Series getItemAt(int position);
        void searchForSeries(@NonNull String searchQuery);
    }
}
