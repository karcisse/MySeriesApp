package com.karcisse.myseriesappv2;

import android.support.annotation.NonNull;

import com.karcisse.myseriesappv2.data.Series;

public class SeriesStatusSpinnerChoice {
    @NonNull private final Series.Status status;
    @NonNull private String displayText;

    public SeriesStatusSpinnerChoice(@NonNull Series.Status status) {
        this.status = status;
        this.displayText = "";
    }

    @NonNull
    public Series.Status getStatus() {
        return status;
    }

    public void setDisplayText(@NonNull String displayText) {
        this.displayText = displayText;
    }

    /**
     * Method overridden to display text in spinner
     * @return
     */
    @Override
    public String toString() {
        return displayText;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SeriesStatusSpinnerChoice)) {
            return false;
        }
        SeriesStatusSpinnerChoice choice = (SeriesStatusSpinnerChoice) obj;

        return status == choice.getStatus();
    }
}
