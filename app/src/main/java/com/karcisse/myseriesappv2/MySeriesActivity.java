package com.karcisse.myseriesappv2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.karcisse.myseriesappv2.data.source.SeriesRepositoryImpl;
import com.karcisse.myseriesappv2.data.source.local.SeriesDataSource;
import com.karcisse.myseriesappv2.data.source.local.SeriesLocalDataSource;
import com.karcisse.myseriesappv2.list.SeriesListFragment;
import com.karcisse.myseriesappv2.list.SeriesListPresenter;
import com.karcisse.myseriesappv2.record.RecordSeriesFragment;
import com.karcisse.myseriesappv2.record.RecordSeriesPresenter;
import com.karcisse.myseriesappv2.utils.ActivityUtils;

public class MySeriesActivity extends AppCompatActivity {

    private SeriesDataSource seriesDataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        seriesDataSource = new SeriesLocalDataSource(this);
        showSeriesList();
    }

    private void showSeriesList() {
        SeriesListFragment seriesListFragment = (SeriesListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (seriesListFragment == null) {
            seriesListFragment = new SeriesListFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), seriesListFragment);
        }

        new SeriesListPresenter(seriesListFragment,
                new SeriesRepositoryImpl(seriesDataSource));
    }

    public void showRecordSeries(@Nullable String seriesId) {
        RecordSeriesFragment fragment = new RecordSeriesFragment();
        ActivityUtils.replaceFragmentAndAddToBackstack(getSupportFragmentManager(), fragment);

        new RecordSeriesPresenter(fragment,
                new SeriesRepositoryImpl(seriesDataSource), seriesId);


        closeDrawer();
    }

    private void closeDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(Gravity.START);
    }
}
