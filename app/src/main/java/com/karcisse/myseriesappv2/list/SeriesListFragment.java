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
        seriesListView.setAdapter(adapter);
        placeholder = (ImageView) root.findViewById(R.id.list_placeholder);

        return root;
    }

    @Override
    public void setPresenter(SeriesListContract.Presenter presenter) {
        this.presenter = presenter;
        adapter = new SeriesAdapter(this.presenter);
    }

    @Override
    public void showSeriesList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showRecordSeries(String seriesId) {
        ((MySeriesActivity) getActivity()).showRecordSeries(seriesId);
    }

    @Override
    public void showEditScreen(String seriesId) {
        ((MySeriesActivity) getActivity()).showRecordSeries(seriesId);
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
}
