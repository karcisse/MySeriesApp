package com.karcisse.myseriesappv2.record;

import com.karcisse.myseriesappv2.SeriesStatusSpinnerChoice;
import com.karcisse.myseriesappv2.data.Series;
import com.karcisse.myseriesappv2.data.source.SeriesRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecordSeriesPresenterTest {

    private static final String SERIES_ID = "seriesId";
    private static final String SERIES_TITLE = "title";

    @Mock private SeriesRepository repository;
    @Mock private RecordSeriesContract.View view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPresenterStartWithSeriesToLoad() {
        RecordSeriesContract.Presenter presenter = new RecordSeriesPresenter(view, repository, SERIES_ID);
        Series baseSeries = getSeries();
        when(repository.getSeries(SERIES_ID)).thenReturn(baseSeries);
        presenter.start();

        ArgumentCaptor<List<SeriesStatusSpinnerChoice>> seriesStatusSpinnerCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).setSpinnerChoices(seriesStatusSpinnerCaptor.capture());
        List<SeriesStatusSpinnerChoice> spinnerChoices = seriesStatusSpinnerCaptor.getValue();

        assertThat("There should be 5 spinner statuses", spinnerChoices.size(), is(5));

        ArgumentCaptor<Series> seriesArgumentCaptor = ArgumentCaptor.forClass(Series.class);
        verify(view).showSeriesData(seriesArgumentCaptor.capture());
        Series series = seriesArgumentCaptor.getValue();

        assertThat("Series title should be equal", series.getSeriesTitle(), is(baseSeries.getSeriesTitle()));
        assertThat("Series id should be equal", series.getId(), is(baseSeries.getId()));
        assertThat("Series status should be equal", series.getStatus(), is(baseSeries.getStatus()));
        assertThat("Series episode number should be equal", series.getEpisodeNumber(), is(baseSeries.getEpisodeNumber()));
        assertThat("Series season number should be equal", series.getSeasonNumber(), is(baseSeries.getSeasonNumber()));
    }

    @Test
    public void testPresenterStartWithNewSeries() {
        RecordSeriesContract.Presenter presenter = new RecordSeriesPresenter(view, repository, null);
        presenter.start();

        ArgumentCaptor<List<SeriesStatusSpinnerChoice>> seriesStatusSpinnerCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).setSpinnerChoices(seriesStatusSpinnerCaptor.capture());
        List<SeriesStatusSpinnerChoice> spinnerChoices = seriesStatusSpinnerCaptor.getValue();

        assertThat("There should be 5 spinner statuses", spinnerChoices.size(), is(5));

        verify(view, never()).showSeriesData(any(Series.class));
    }

    @Test
    public void testSaveNewSeriesWithoutError() {
        RecordSeriesContract.Presenter presenter = new RecordSeriesPresenter(view, repository, null);

        presenter.saveSeries(SERIES_TITLE, "2", "3", Series.Status.TO_WATCH);

        verify(view, never()).showErrorMessage();

        ArgumentCaptor<Series> seriesArgumentCaptor = ArgumentCaptor.forClass(Series.class);
        verify(repository).saveSeries(seriesArgumentCaptor.capture());

        Series baseSeries = getSeries();
        Series series = seriesArgumentCaptor.getValue();

        assertThat("Series title should be equal", series.getSeriesTitle(), is(baseSeries.getSeriesTitle()));
        assertThat("Series id should be equal", series.getId(), is(nullValue()));
        assertThat("Series status should be equal", series.getStatus(), is(baseSeries.getStatus()));
        assertThat("Series episode number should be equal", series.getEpisodeNumber(), is(baseSeries.getEpisodeNumber()));
        assertThat("Series season number should be equal", series.getSeasonNumber(), is(baseSeries.getSeasonNumber()));
    }

    @Test
    public void testUpdateSeriesWithoutError() {
        RecordSeriesContract.Presenter presenter = new RecordSeriesPresenter(view, repository, SERIES_ID);

        presenter.saveSeries(SERIES_TITLE, "2", "3", Series.Status.TO_WATCH);

        verify(view, never()).showErrorMessage();

        ArgumentCaptor<Series> seriesArgumentCaptor = ArgumentCaptor.forClass(Series.class);
        verify(repository).saveSeries(seriesArgumentCaptor.capture());

        Series baseSeries = getSeries();
        Series series = seriesArgumentCaptor.getValue();

        assertThat("Series title should be equal", series.getSeriesTitle(), is(baseSeries.getSeriesTitle()));
        assertThat("Series id should be equal", series.getId(), is(baseSeries.getId()));
        assertThat("Series status should be equal", series.getStatus(), is(baseSeries.getStatus()));
        assertThat("Series episode number should be equal", series.getEpisodeNumber(), is(baseSeries.getEpisodeNumber()));
        assertThat("Series season number should be equal", series.getSeasonNumber(), is(baseSeries.getSeasonNumber()));
    }

    @Test
    public void testSaveSeriesWithEmptySeasonError() {
        RecordSeriesContract.Presenter presenter = new RecordSeriesPresenter(view, repository, null);

        presenter.saveSeries(SERIES_TITLE, "", "3", Series.Status.TO_WATCH);
        verify(view).showErrorMessage();
    }

    @Test
    public void testSaveSeriesWithEmptyEpisodeError() {
        RecordSeriesContract.Presenter presenter = new RecordSeriesPresenter(view, repository, null);

        presenter.saveSeries(SERIES_TITLE, "2", "", Series.Status.TO_WATCH);
        verify(view).showErrorMessage();
    }

    @Test
    public void testSaveSeriesWithEmptyTitleError() {
        RecordSeriesContract.Presenter presenter = new RecordSeriesPresenter(view, repository, null);

        presenter.saveSeries("", "2", "3", Series.Status.TO_WATCH);
        verify(view).showErrorMessage();
    }

    private Series getSeries() {
        return new Series(SERIES_ID, SERIES_TITLE, 2, 3, Series.Status.TO_WATCH);
    }
}
