package com.karcisse.myseriesappv2.list;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.karcisse.myseriesappv2.R;
import com.karcisse.myseriesappv2.SeriesStatusSpinnerChoice;
import com.karcisse.myseriesappv2.data.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesAdapter extends BaseAdapter {

    private static final String EDIT_TAG = "editing";

    private final SeriesListContract.Presenter presenter;

    public SeriesAdapter(@NonNull SeriesListContract.Presenter presenter) {
        super();
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return presenter.getDataSize();
    }

    @Override
    public Series getItem(int position) {
        return presenter.getItemAt(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Series series = getItem(position);
        if (series.getId() == null) {
            return null; // This should never happen
        }

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            row = inflater.inflate(R.layout.series_row, parent, false);
        }

        // Find fields to populate in inflated template
        TextView seriesTitle = (TextView) row.findViewById(R.id.series_title_field);
        TextView seasonNumber = (TextView) row.findViewById(R.id.season_number_field);
        TextView episodeNumber = (TextView) row.findViewById(R.id.episode_number_field);
        TextView seriesStatus = (TextView) row.findViewById(R.id.series_status_field);

        // Populate fields with extracted properties
        seriesTitle.setText(series.getSeriesTitle());
        seasonNumber.setText(String.valueOf(series.getSeasonNumber()));
        episodeNumber.setText(String.valueOf(series.getEpisodeNumber()));
        setSeriesStatus(seriesStatus, series.getSeriesStatus());

        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String seriesId = series.getId();
                if (seriesId != null) {
                    presenter.showRecordSeries(series.getId());
                }
                return true;
            }
        });
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openItem(getItem(position).getId());
            }
        });

        if (presenter.isRowEdited(series.getId())) {
            row.findViewById(R.id.edit_series_view).setVisibility(View.VISIBLE);
            setUpEditSeriesRow(row, parent, series.getId(), series.getSeriesStatus(),
                    episodeNumber, seasonNumber, seriesStatus);
        } else {
            row.findViewById(R.id.edit_series_view).setVisibility(View.GONE);
        }

        return row;
    }

    private int getColorForStatus(Series.SeriesStatus status) {
        switch (status) {
            case TO_WATCH:
                return Color.BLUE;
            case WATCHING:
                return Color.YELLOW;
            case COMPLETE:
                return Color.GREEN;
            case DROPPED:
                return Color.RED;
            case ARRIVING:
                return Color.DKGRAY;
            default:
                return 0;
        }
    }

    private void setUpEditSeriesRow(View editRow, ViewGroup parent, final String seriesId, Series.SeriesStatus status,
                                    final TextView episode, final TextView season, final TextView seriesStatus) {

        setUpEpisodeChangeListener(editRow, seriesId, episode);
        setUpSeasonChangeListener(editRow, seriesId, season);
        setUpRowControlListener(editRow, seriesId);
        setUpStatusSpinner(editRow, parent, seriesId, status, seriesStatus);

        editRow.setTag(EDIT_TAG);
    }

    private List<SeriesStatusSpinnerChoice> setSeriesStatusesSpinnerChoices(Context context) {
        List<SeriesStatusSpinnerChoice> spinnerChoices = new ArrayList<>();
        for (Series.SeriesStatus status : Series.SeriesStatus.values()) {
            SeriesStatusSpinnerChoice spinnerChoice = new SeriesStatusSpinnerChoice(status);
            spinnerChoice.setDisplayText(context.getString(Series.SeriesStatus.getStringResId(
                    spinnerChoice.getStatus()
            )));
            spinnerChoices.add(spinnerChoice);
        }

        return spinnerChoices;
    }

    private void incrementNumberValue(TextView textView) {
        String text = (String) textView.getText();
        Integer integer = Integer.valueOf(text);
        if (integer != null) {
            integer++;
            textView.setText(String.valueOf(integer));
        }
    }

    private void decrementNumberValue(TextView textView) {
        String text = (String) textView.getText();
        Integer integer = Integer.valueOf(text);
        if (integer != null) {
            integer--;
            textView.setText(String.valueOf(integer));
        }
    }

    private void setSeriesStatus(TextView seriesStatus, Series.SeriesStatus status) {
        seriesStatus.setText(Series.SeriesStatus.getStringResId(status));
        seriesStatus.setTextColor(getColorForStatus(status));
    }

    private void setUpEpisodeChangeListener(View editRow, final String seriesId, final TextView episode) {
        editRow.findViewById(R.id.dec_episode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.decrementEpisode(seriesId);
                decrementNumberValue(episode);
            }
        });

        editRow.findViewById(R.id.inc_episode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.incrementEpisode(seriesId);
                incrementNumberValue(episode);
            }
        });
    }

    private void setUpSeasonChangeListener(View editRow, final String seriesId, final TextView season) {
        editRow.findViewById(R.id.dec_season).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.decrementSeason(seriesId);
                decrementNumberValue(season);
            }

        });

        editRow.findViewById(R.id.inc_season).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.incrementSeason(seriesId);
                incrementNumberValue(season);
            }
        });
    }

    private void setUpRowControlListener(View editRow, final String seriesId) {
        editRow.findViewById(R.id.edit_series).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showEditScreen(seriesId);
            }
        });

        editRow.findViewById(R.id.delete_series).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteSeries(seriesId);
            }
        });

        editRow.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.closeItem(seriesId);
            }
        });
    }

    private void setUpStatusSpinner(View editRow, ViewGroup parent, final String seriesId, Series.SeriesStatus status,
                                    final TextView seriesStatus) {
        Spinner statusSpinner = (Spinner) editRow.findViewById(R.id.status_menu);

        final ArrayAdapter<SeriesStatusSpinnerChoice> arrayAdapter =
                new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_item,
                        setSeriesStatusesSpinnerChoices(parent.getContext()));

        statusSpinner.setAdapter(arrayAdapter);

        statusSpinner.setSelection(arrayAdapter.getPosition(new SeriesStatusSpinnerChoice(status)));

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SeriesStatusSpinnerChoice spinnerChoice = arrayAdapter.getItem(position);
                if (spinnerChoice != null) {
                    SeriesAdapter.this.presenter.changeStatus(seriesId, spinnerChoice.getStatus());
                    setSeriesStatus(seriesStatus, spinnerChoice.getStatus());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing to do
            }
        });
    }
}
