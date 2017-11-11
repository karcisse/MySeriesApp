package com.karcisse.myseriesappv2.list;

import android.support.annotation.NonNull;

import com.karcisse.myseriesappv2.data.Series;
import com.karcisse.myseriesappv2.data.source.SeriesRepository;

import java.util.ArrayList;
import java.util.List;

public class SeriesListPresenter implements SeriesListContract.Presenter {

    @NonNull private final SeriesRepository repository;
    @NonNull private final SeriesListContract.View view;

    public SeriesListPresenter(@NonNull SeriesListContract.View view,
                               @NonNull SeriesRepository repository) {
        this.repository = repository;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        loadSeries();
    }

    private void loadSeries() {
        List<Series> seriesList = new ArrayList<>();

        seriesList.addAll(repository.getSeriesByStatus(Series.SeriesStatus.WATCHING));
        seriesList.addAll(repository.getSeriesByStatus(Series.SeriesStatus.ARRIVING));
        seriesList.addAll(repository.getSeriesByStatus(Series.SeriesStatus.TO_WATCH));
        seriesList.addAll(repository.getSeriesByStatus(Series.SeriesStatus.COMPLETE));
        seriesList.addAll(repository.getSeriesByStatus(Series.SeriesStatus.DROPPED));

        view.showSeriesList(seriesList);
    }

    @Override
    public void incrementEpisode(String seriesId) {
        Series series = repository.getSeries(seriesId);

        Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                series.getSeasonNumber(), series.getEpisodeNumber() + 1, Series.SeriesStatus.WATCHING);

        repository.saveSeries(newSeries);
    }

    @Override
    public void decrementEpisode(String seriesId) {
        Series series = repository.getSeries(seriesId);

        if (series.getEpisodeNumber() != 0) {
            Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                    series.getSeasonNumber(), series.getEpisodeNumber() - 1, Series.SeriesStatus.WATCHING);

            repository.saveSeries(newSeries);
        }
    }

    @Override
    public void incrementSeason(String seriesId) {
        Series series = repository.getSeries(seriesId);

        Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                series.getSeasonNumber() + 1, series.getEpisodeNumber(), Series.SeriesStatus.WATCHING);

        repository.saveSeries(newSeries);
    }

    @Override
    public void decrementSeason(String seriesId) {
        Series series = repository.getSeries(seriesId);

        if (series.getSeasonNumber() != 0) {
            Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                    series.getSeasonNumber() - 1, series.getEpisodeNumber(), Series.SeriesStatus.WATCHING);

            repository.saveSeries(newSeries);
        }
    }

    @Override
    public void changeStatus(String seriesId, Series.SeriesStatus status) {
        Series series = repository.getSeries(seriesId);

        Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                series.getSeasonNumber(), series.getEpisodeNumber(), status);

        repository.saveSeries(newSeries);
    }

    @Override
    public void deleteSeries(String seriesId) {
        repository.deleteSeries(seriesId);
        loadSeries();
    }

    @Override
    public void refresh() {
        loadSeries();
    }
}
