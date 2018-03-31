package com.karcisse.myseriesappv2.list;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void setPresenter(SeriesListContract.Presenter presenter) {
        this.presenter = presenter;
        adapter = new SeriesAdapter(this.presenter);
        if (seriesListView != null) {
            seriesListView.setAdapter(adapter);
        }
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
    public void showRecordSeries(@NonNull String seriesId) {
        ((MySeriesActivity) getActivity()).showRecordSeries(seriesId);
    }

    @Override
    public void showEditScreen(@NonNull String seriesId) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        MySeriesActivity activity = (MySeriesActivity) getActivity();

        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        if (searchManager == null) {
            return;
        }
        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                presenter.start();
                return true;
            }
        });
        SearchView searchView = (SearchView) searchItem.getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchForSeries(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }


}
