package com.karcisse.myseriesappv2.record;

import com.karcisse.myseriesappv2.BasePresenter;
import com.karcisse.myseriesappv2.BaseView;
import com.karcisse.myseriesappv2.SeriesStatusSpinnerChoice;
import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

public interface RecordSeriesContract {

    interface View extends BaseView<Presenter> {
        void showSeriesData(Series series);
        void setSpinnerChoices(List<SeriesStatusSpinnerChoice> spinnerChoices);
        void showErrorMessage();
        void closeFragment();
    }

    interface Presenter extends BasePresenter {
        void saveSeries(String title, String season, String episode, Series.SeriesStatus status);
    }
}
