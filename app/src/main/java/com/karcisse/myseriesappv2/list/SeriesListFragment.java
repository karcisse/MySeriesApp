package com.karcisse.myseriesappv2.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.karcisse.myseriesappv2.MySeriesActivity;
import com.karcisse.myseriesappv2.R;
import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

public class SeriesListFragment extends Fragment implements SeriesListContract.View {

    private SeriesListContract.Presenter presenter;

    private SeriesAdapter adapter;

    private ListView seriesListView;
    private ImageView placeholder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_series_list, container, false);

        seriesListView = (ListView) root.findViewById(R.id.series_list_view);
        adapter = new SeriesAdapter(setUpCallback());
        seriesListView.setAdapter(adapter);
        placeholder = (ImageView) root.findViewById(R.id.list_placeholder);

        return root;
    }

    @Override
    public void setPresenter(SeriesListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showSeriesList(List<Series> seriesList) {
        adapter.setData(seriesList);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    private Callback setUpCallback() {
        return new Callback() {
            @Override
            public void onLongClick(String seriesId) {
                ((MySeriesActivity) getActivity()).showRecordSeries(seriesId);
            }

            @Override
            public void onClick(int position) {
                adapter.openItem(adapter.getItem(position).getId());
                presenter.refresh();
            }

            @Override
            public void incrementEpisode(String seriesId) {
                presenter.incrementEpisode(seriesId);
            }

            @Override
            public void decrementEpisode(String seriesId) {
                presenter.decrementEpisode(seriesId);
            }

            @Override
            public void incrementSeason(String seriesId) {
                presenter.incrementSeason(seriesId);
            }

            @Override
            public void decrementSeason(String seriesId) {
                presenter.decrementSeason(seriesId);
            }

            @Override
            public void showEditScreen(String seriesId) {
                ((MySeriesActivity) getActivity()).showRecordSeries(seriesId);
            }

            @Override
            public void changeStatus(String seriesId, Series.SeriesStatus status) {
                presenter.changeStatus(seriesId, status);
            }

            @Override
            public void deleteSeries(String seriesId) {
                presenter.deleteSeries(seriesId);
                adapter.closeItem(seriesId);
                presenter.refresh();
            }

            @Override
            public void close(String seriesId) {
                adapter.closeItem(seriesId);
                presenter.refresh();
            }

            @Override
            public void onEmptyList() {
                seriesListView.setVisibility(View.GONE);
                placeholder.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNotEmptyList() {
                seriesListView.setVisibility(View.VISIBLE);
                placeholder.setVisibility(View.GONE);
            }
        };
    }

    public interface Callback {
        void onLongClick(String seriesId);
        void onClick(int pos);

        void incrementEpisode(String seriesId);
        void decrementEpisode(String seriesId);
        void incrementSeason(String seriesId);
        void decrementSeason(String seriesId);
        void showEditScreen(String seriesId);
        void changeStatus(String seriesId, Series.SeriesStatus status);
        void deleteSeries(String seriesId);
        void close(String seriesId);
        void onEmptyList();
        void onNotEmptyList();
    }
}
