package com.karcisse.myseriesappv2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EdgeNavDrawer extends ListView implements AdapterView.OnItemClickListener {
    private final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    private final View snapshot = inflater.inflate(R.layout.drawer_list_header, this, false);

    public EdgeNavDrawer(Context context) {
        super(context);
        setOnItemClickListener(this);
        addHeaderView(snapshot);
        setUpList();
    }

    public EdgeNavDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnItemClickListener(this);
        addHeaderView(snapshot);
        setUpList();
    }

    public EdgeNavDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnItemClickListener(this);
        addHeaderView(snapshot);
        setUpList();
    }

    private void setUpList() {
        String[] osArray = {"New series", "Settings"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, osArray);
        setAdapter(mAdapter);
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
            default:
                break;
        }
    }

    private static class DrawerItems {
        public static final int ADD_SERIES = 1;
        public static final int SETTINGS = 2;
    }
}
