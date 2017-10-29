package com.karcisse.myseriesappv2.list;

import com.karcisse.myseriesappv2.BasePresenter;
import com.karcisse.myseriesappv2.BaseView;
import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

public interface SeriesListContract {

    interface View extends BaseView<Presenter> {
        void showSeriesList(List<Series> seriesList);
    }

    interface Presenter extends BasePresenter {
        void incrementEpisode(String seriesId);
        void decrementEpisode(String seriesId);
        void incrementSeason(String seriesId);
        void decrementSeason(String seriesId);
        void changeStatus(String seriesId, Series.SeriesStatus status);
        void deleteSeries(String seriesId);
        void refresh();
    }
}
