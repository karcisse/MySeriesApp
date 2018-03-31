package com.karcisse.myseriesappv2.list;

import com.karcisse.myseriesappv2.data.Series;
import com.karcisse.myseriesappv2.data.source.SeriesRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SeriesListPresenterTest {
    private static final String SERIES_ID_ASSERT_MESSAGE = "Series id should be equal";
    private static final String SERIES_STATUS_WATCHING_ASSERT_MESSAGE = "Series status should equal watching";
    private static final String SERIES_TITLE_ASSERT_MESSAGE = "Series title should be equal";
    private static final String SERIES_TITLE = "title";

    @Mock private SeriesRepository repository;
    @Mock private SeriesListContract.View view;

    private SeriesListContract.Presenter presenter;

    @Before
    public void setUp() {
        initMocks(this);

        presenter = new SeriesListPresenter(view, repository);
    }

    @Test
    public void testStartPresenter() {
        when(repository.getSeriesByStatus(Series.Status.WATCHING)).thenReturn(getSeriesList(0, 3, Series.Status.WATCHING));
        when(repository.getSeriesByStatus(Series.Status.ARRIVING)).thenReturn(getSeriesList(3, 6, Series.Status.ARRIVING));
        when(repository.getSeriesByStatus(Series.Status.TO_WATCH)).thenReturn(getSeriesList(6, 9, Series.Status.TO_WATCH));
        when(repository.getSeriesByStatus(Series.Status.COMPLETE)).thenReturn(getSeriesList(9, 12, Series.Status.COMPLETE));
        when(repository.getSeriesByStatus(Series.Status.DROPPED)).thenReturn(getSeriesList(12, 15, Series.Status.DROPPED));

        presenter.start();

        verify(view).showSeriesList();
        verify(view).onNotEmptyList();
    }

    @Test
    public void testStartPresenterEmptyList() {
        when(repository.getSeriesByStatus(Series.Status.WATCHING)).thenReturn(new ArrayList<Series>());
        when(repository.getSeriesByStatus(Series.Status.ARRIVING)).thenReturn(new ArrayList<Series>());
        when(repository.getSeriesByStatus(Series.Status.TO_WATCH)).thenReturn(new ArrayList<Series>());
        when(repository.getSeriesByStatus(Series.Status.COMPLETE)).thenReturn(new ArrayList<Series>());
        when(repository.getSeriesByStatus(Series.Status.DROPPED)).thenReturn(new ArrayList<Series>());

        presenter.start();

        verify(view).onEmptyList();
    }

    @Test
    public void testIncrementEpisode() {
        Series baseSeries = getSeries();
        when(repository.getSeries("id")).thenReturn(baseSeries);
        presenter.incrementEpisode("id");

        ArgumentCaptor<Series> seriesArgumentCaptor = ArgumentCaptor.forClass(Series.class);

        verify(repository).saveSeries(seriesArgumentCaptor.capture());
        Series series = seriesArgumentCaptor.getValue();

        assertThat(SERIES_ID_ASSERT_MESSAGE, series.getId(), is(baseSeries.getId()));
        assertThat("Series episode should be incremented", series.getEpisodeNumber(), is(3));
        assertThat(SERIES_STATUS_WATCHING_ASSERT_MESSAGE, series.getStatus(), is(Series.Status.WATCHING));
        assertThat("Series season should not change", series.getSeasonNumber(), is(baseSeries.getSeasonNumber()));
        assertThat(SERIES_TITLE_ASSERT_MESSAGE, series.getSeriesTitle(), is(baseSeries.getSeriesTitle()));

    }

    @Test
    public void testDecrementEpisode() {
        Series baseSeries = getSeries();
        when(repository.getSeries("id")).thenReturn(baseSeries);
        presenter.decrementEpisode("id");

        ArgumentCaptor<Series> seriesArgumentCaptor = ArgumentCaptor.forClass(Series.class);

        verify(repository).saveSeries(seriesArgumentCaptor.capture());
        Series series = seriesArgumentCaptor.getValue();

        assertThat(SERIES_ID_ASSERT_MESSAGE, series.getId(), is(baseSeries.getId()));
        assertThat("Series episode should be decremented", series.getEpisodeNumber(), is(1));
        assertThat(SERIES_STATUS_WATCHING_ASSERT_MESSAGE, series.getStatus(), is(Series.Status.WATCHING));
        assertThat("Series season should not change", series.getSeasonNumber(), is(baseSeries.getSeasonNumber()));
        assertThat(SERIES_TITLE_ASSERT_MESSAGE, series.getSeriesTitle(), is(baseSeries.getSeriesTitle()));
    }

    @Test
    public void testIncrementSeason() {
        Series baseSeries = getSeries();
        when(repository.getSeries("id")).thenReturn(baseSeries);
        presenter.incrementSeason("id");

        ArgumentCaptor<Series> seriesArgumentCaptor = ArgumentCaptor.forClass(Series.class);

        verify(repository).saveSeries(seriesArgumentCaptor.capture());
        Series series = seriesArgumentCaptor.getValue();

        assertThat(SERIES_ID_ASSERT_MESSAGE, series.getId(), is(baseSeries.getId()));
        assertThat("Series episode should not change", series.getEpisodeNumber(), is(baseSeries.getEpisodeNumber()));
        assertThat(SERIES_STATUS_WATCHING_ASSERT_MESSAGE, series.getStatus(), is(Series.Status.WATCHING));
        assertThat("Series season should be incremented", series.getSeasonNumber(), is(2));
        assertThat(SERIES_TITLE_ASSERT_MESSAGE, series.getSeriesTitle(), is(baseSeries.getSeriesTitle()));

    }

    @Test
    public void testDecrementSeason() {
        Series baseSeries = getSeries();
        when(repository.getSeries("id")).thenReturn(baseSeries);
        presenter.decrementSeason("id");

        ArgumentCaptor<Series> seriesArgumentCaptor = ArgumentCaptor.forClass(Series.class);

        verify(repository).saveSeries(seriesArgumentCaptor.capture());
        Series series = seriesArgumentCaptor.getValue();

        assertThat(SERIES_ID_ASSERT_MESSAGE, series.getId(), is(baseSeries.getId()));
        assertThat("Series episode should not change", series.getEpisodeNumber(), is(baseSeries.getEpisodeNumber()));
        assertThat(SERIES_STATUS_WATCHING_ASSERT_MESSAGE, series.getStatus(), is(Series.Status.WATCHING));
        assertThat("Series season should be decremented", series.getSeasonNumber(), is(0));
        assertThat(SERIES_TITLE_ASSERT_MESSAGE, series.getSeriesTitle(), is(baseSeries.getSeriesTitle()));
    }

    @Test
    public void testChangeStatus() {
        Series baseSeries = getSeries();
        when(repository.getSeries("id")).thenReturn(baseSeries);
        presenter.changeStatus("id", Series.Status.DROPPED);

        ArgumentCaptor<Series> seriesArgumentCaptor = ArgumentCaptor.forClass(Series.class);

        verify(repository).saveSeries(seriesArgumentCaptor.capture());
        Series series = seriesArgumentCaptor.getValue();

        assertThat(SERIES_ID_ASSERT_MESSAGE, series.getId(), is(baseSeries.getId()));
        assertThat("Series episode should  not change", series.getEpisodeNumber(), is(baseSeries.getEpisodeNumber()));
        assertThat("Series status should be changed", series.getStatus(), is(Series.Status.DROPPED));
        assertThat("Series season should not change", series.getSeasonNumber(), is(baseSeries.getSeasonNumber()));
        assertThat(SERIES_TITLE_ASSERT_MESSAGE, series.getSeriesTitle(), is(baseSeries.getSeriesTitle()));
    }

    @Test
    public void testDeleteSeries() {
        presenter.deleteSeries("id3");
        verify(repository).deleteSeries("id3");
    }

    @Test
    public void testDecrementEpisodeToNegative() {
        Series baseSeries = new Series("id", SERIES_TITLE, 1, 0, Series.Status.ARRIVING);
        when(repository.getSeries("id")).thenReturn(baseSeries);
        presenter.decrementEpisode("id");
        verify(repository, never()).saveSeries(any(Series.class));
    }

    @Test
    public void testDecrementSeasonToNegative() {
        Series baseSeries = new Series("id", SERIES_TITLE, 0, 0, Series.Status.ARRIVING);
        when(repository.getSeries("id")).thenReturn(baseSeries);
        presenter.decrementSeason("id");
        verify(repository, never()).saveSeries(any(Series.class));
    }

    private Series getSeries() {
        return new Series("id", SERIES_TITLE, 1, 2, Series.Status.ARRIVING);
    }

    private List<Series> getSeriesList(int start, int stop, Series.Status status) {
        List<Series> seriesList = new ArrayList<>();

        for (int i = start; i < stop; i++) {
            seriesList.add(new Series("id" + i, SERIES_TITLE + i, 2 * i, i, status));
        }

        return seriesList;
    }
}
