package com.karcisse.myseriesappv2.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.data.Series;
import com.karcisse.myseriesappv2.data.source.SeriesRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeriesListPresenter implements SeriesListContract.Presenter {

    @NonNull private final SeriesRepository repository;
    @NonNull private final SeriesListContract.View view;
    @NonNull private final List<Series> data;
    @NonNull private final Set<String> openedEdits;

    public SeriesListPresenter(@NonNull SeriesListContract.View view,
                               @NonNull SeriesRepository repository) {
        this.repository = repository;
        this.view = view;
        this.data = new ArrayList<>();
        openedEdits = new HashSet<>();
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        loadSeries();
    }

    @Override
    public void incrementEpisode(String seriesId) {
        Series series = repository.getSeries(seriesId);

        Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                series.getSeasonNumber(), series.getEpisodeNumber() + 1, Series.Status.WATCHING);

        repository.saveSeries(newSeries);
    }

    @Override
    public void decrementEpisode(String seriesId) {
        Series series = repository.getSeries(seriesId);

        if (series.getEpisodeNumber() != 0) {
            Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                    series.getSeasonNumber(), series.getEpisodeNumber() - 1, Series.Status.WATCHING);

            repository.saveSeries(newSeries);
        }
    }

    @Override
    public void incrementSeason(String seriesId) {
        Series series = repository.getSeries(seriesId);

        Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                series.getSeasonNumber() + 1, series.getEpisodeNumber(), Series.Status.WATCHING);

        repository.saveSeries(newSeries);
    }

    @Override
    public void decrementSeason(String seriesId) {
        Series series = repository.getSeries(seriesId);

        if (series.getSeasonNumber() != 0) {
            Series newSeries = new Series(seriesId, series.getSeriesTitle(),
                    series.getSeasonNumber() - 1, series.getEpisodeNumber(), Series.Status.WATCHING);

            repository.saveSeries(newSeries);
        }
    }

    @Override
    public void changeStatus(String seriesId, Series.Status status) {
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
    public void closeItem(String seriesId) {
        if (isRowEdited(seriesId)) {
            openedEdits.remove(seriesId);
            refresh();
        }
    }

    @Override
    public void openItem(String seriesId) {
        if (!isRowEdited(seriesId)) {
            openedEdits.add(seriesId);
            refresh();
        }
    }

    @Override
    public void showRecordSeries(@NonNull String seriesId) {
        view.showRecordSeries(seriesId);
    }

    @Override
    public boolean isRowEdited(@Nullable String id) {
        return id != null && openedEdits.contains(id);
    }

    @Override
    public int getDataSize() {
        return data.size();
    }

    @Override
    public void showEditScreen(String seriesId) {
        view.showEditScreen(seriesId);
    }

    @Override
    public Series getItemAt(int position) {
        return data.get(position);
    }

    @Override
    public void searchForSeries(@NonNull String searchQuery) {
        data.clear();
        data.addAll(repository.searchForSeries(searchQuery));
        showSeriesList();
    }

    private void loadSeries() {
        data.clear();
        data.addAll(repository.getSeriesByStatus(Series.Status.WATCHING));
        data.addAll(repository.getSeriesByStatus(Series.Status.ARRIVING));
        data.addAll(repository.getSeriesByStatus(Series.Status.TO_WATCH));
        data.addAll(repository.getSeriesByStatus(Series.Status.COMPLETE));
        data.addAll(repository.getSeriesByStatus(Series.Status.DROPPED));

        showSeriesList();
    }

    private void refresh() {
        loadSeries();
    }

    private void showSeriesList() {
        if (data.isEmpty()) {
            view.onEmptyList();
        } else {
            view.showSeriesList();
            view.onNotEmptyList();
        }
    }
}
