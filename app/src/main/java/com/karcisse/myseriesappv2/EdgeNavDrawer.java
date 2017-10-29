package com.karcisse.myseriesappv2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// TODO: 11.10.17 is it necessary? For sure improve
public class EdgeNavDrawer extends ListView implements AdapterView.OnItemClickListener{
    private ArrayAdapter<String> mAdapter;
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View snapshot = inflater.inflate(R.layout.drawer_list_header, this, false);

    public EdgeNavDrawer(Context context) {
        super(context);
        setUpList();
        setOnItemClickListener(this);
        addHeaderView(snapshot);
    }

    public EdgeNavDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpList();
        setOnItemClickListener(this);
        addHeaderView(snapshot);
    }

    public EdgeNavDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpList();
        setOnItemClickListener(this);
        addHeaderView(snapshot);
    }

    public void setUpList() {
        String[] osArray = { "New series", "Settings"};
        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, osArray);
        setAdapter(mAdapter);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case DrawerItems.ADD_SERIES:
                ((MySeriesActivity) getContext()).showRecordSeries(null);
                break;
            case DrawerItems.SETTINGS:
//                intent = new Intent(view.getContext(), SettingsActivity.class);
//                activity = (SeriesActivity) getContext();
//                activity.startActivity(intent);
                break;
        }
    }

    private static class DrawerItems {
        public static final int ADD_SERIES = 1;
        public static final int SETTINGS = 2;
    }
}
