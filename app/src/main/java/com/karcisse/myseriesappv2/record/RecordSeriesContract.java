package com.karcisse.myseriesappv2.record;

import android.support.annotation.NonNull;

import com.karcisse.myseriesappv2.BasePresenter;
import com.karcisse.myseriesappv2.BaseView;
import com.karcisse.myseriesappv2.SeriesStatusSpinnerChoice;
import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

public interface RecordSeriesContract {

    interface View extends BaseView<Presenter> {
        void showSeriesData(@NonNull Series series);
        void setSpinnerChoices(@NonNull List<SeriesStatusSpinnerChoice> spinnerChoices);
        void showErrorMessage();
        void closeFragment();
    }

    interface Presenter extends BasePresenter {
        void saveSeries(@NonNull String title,
                        @NonNull String season,
                        @NonNull String episode,
                        @NonNull Series.Status status);
    }
}
