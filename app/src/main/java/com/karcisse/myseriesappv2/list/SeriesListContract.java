package com.karcisse.myseriesappv2.list;

import android.support.annotation.NonNull;

import com.karcisse.myseriesappv2.BasePresenter;
import com.karcisse.myseriesappv2.BaseView;
import com.karcisse.myseriesappv2.data.Series;

public interface SeriesListContract {

    interface View extends BaseView<Presenter> {
        void showSeriesList();
        void showRecordSeries(String seriesId);
        void showEditScreen(String seriesId);
        void onEmptyList();
        void onNotEmptyList();
    }

    interface Presenter extends BasePresenter {
        void incrementEpisode(String seriesId);
        void decrementEpisode(String seriesId);
        void incrementSeason(String seriesId);
        void decrementSeason(String seriesId);
        void changeStatus(String seriesId, Series.SeriesStatus status);
        void deleteSeries(String seriesId);
        void closeItem(String seriesId);
        void openItem(String seriesId);
        void showRecordSeries(@NonNull String seriesId);
        boolean isRowEdited(@NonNull String id);
        int getDataSize();
        void showEditScreen(String seriesId);
        Series getItemAt(int position);
    }
}
