package com.karcisse.myseriesappv2.record;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.SeriesStatusSpinnerChoice;
import com.karcisse.myseriesappv2.data.Series;
import com.karcisse.myseriesappv2.data.source.SeriesRepository;

import java.util.ArrayList;
import java.util.List;

public class RecordSeriesPresenter implements RecordSeriesContract.Presenter {

    @NonNull private final SeriesRepository repository;
    @NonNull private final RecordSeriesContract.View view;
    @Nullable private final String seriesId;

    public RecordSeriesPresenter(@NonNull RecordSeriesContract.View view, @NonNull SeriesRepository repository,
                                 @Nullable String seriesId) {
        this.repository = repository;
        this.view = view;
        this.seriesId = seriesId;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {
        setSeriesStatusesSpinnerChoices();
        loadSeriesData();
    }

    @Override
    public void saveSeries(@NonNull String title, @NonNull String season, @NonNull String episode, @NonNull Series.Status status) {

        if (title.isEmpty() || season.isEmpty() || episode.isEmpty()) {
            view.showErrorMessage();
            return;
        }

        repository.saveSeries(new Series(seriesId,
                title,
                Integer.parseInt(season),
                Integer.parseInt(episode),
                status));

        view.closeFragment();
    }

    private void loadSeriesData() {
        if (seriesId != null) {
            Series series = repository.getSeries(seriesId);
            if (series != null) {
                view.showSeriesData(series);
            }
        }
    }

    private void setSeriesStatusesSpinnerChoices() {
        List<SeriesStatusSpinnerChoice> spinnerChoices = new ArrayList<>();
        for (Series.Status status : Series.Status.values()) {
            spinnerChoices.add(new SeriesStatusSpinnerChoice(status));
        }
        view.setSpinnerChoices(spinnerChoices);
    }
}
