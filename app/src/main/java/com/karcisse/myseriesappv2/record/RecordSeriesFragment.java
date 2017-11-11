package com.karcisse.myseriesappv2.record;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karcisse.myseriesappv2.R;
import com.karcisse.myseriesappv2.SeriesStatusSpinnerChoice;
import com.karcisse.myseriesappv2.data.Series;

import java.util.List;

// TODO: 02.11.17 Think about it
@SuppressWarnings("PMD.AccessorMethodGeneration")
public class RecordSeriesFragment extends Fragment implements RecordSeriesContract.View {

    private RecordSeriesContract.Presenter presenter;

    private TextView titleTextView;
    private TextView seasonTextView;
    private TextView episodeTextView;
    private Spinner statusSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record_series, container, false);

        titleTextView = (TextView) root.findViewById(R.id.series_name);
        seasonTextView = (TextView) root.findViewById(R.id.season_number);
        episodeTextView = (TextView) root.findViewById(R.id.episode_number);
        statusSpinner = (Spinner) root.findViewById(R.id.status_menu);

        root.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveSeries(titleTextView.getText().toString(),
                        seasonTextView.getText().toString(),
                        episodeTextView.getText().toString(),
                        ((SeriesStatusSpinnerChoice) statusSpinner.getSelectedItem()).getStatus());
            }
        });

        return root;
    }

    @Override
    public void setPresenter(RecordSeriesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showSeriesData(Series series) {
        titleTextView.setText(series.getSeriesTitle());
        seasonTextView.setText(String.valueOf(series.getSeasonNumber()));
        episodeTextView.setText(String.valueOf(series.getEpisodeNumber()));
        ArrayAdapter<SeriesStatusSpinnerChoice> arrayAdapter =
                (ArrayAdapter<SeriesStatusSpinnerChoice>) statusSpinner.getAdapter();
        statusSpinner.setSelection(arrayAdapter.getPosition(new SeriesStatusSpinnerChoice(series.getSeriesStatus())));
    }

    @Override
    public void setSpinnerChoices(List<SeriesStatusSpinnerChoice> spinnerChoices) {
        for (SeriesStatusSpinnerChoice spinnerChoice : spinnerChoices) {
            spinnerChoice.setDisplayText(getString(Series.SeriesStatus.getStringResId(
                    spinnerChoice.getStatus()
            )));
        }

        ArrayAdapter<SeriesStatusSpinnerChoice> arrayAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                spinnerChoices);

        statusSpinner.setAdapter(arrayAdapter);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeFragment() {
        closeKeyboard();
        popFragment();
    }

    private void popFragment() {
        getFragmentManager().popBackStackImmediate();
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
