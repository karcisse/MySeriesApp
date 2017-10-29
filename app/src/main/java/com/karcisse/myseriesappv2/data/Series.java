package com.karcisse.myseriesappv2.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.R;

public class Series {
    @Nullable private final String id;
    @NonNull private final String seriesTitle;
    private final int seasonNumber;
    private final int episodeNumber;
    @NonNull private final SeriesStatus seriesStatus;

    public Series(@Nullable String id, @NonNull String seriesTitle,
                  int seasonNumber, int episodeNumber, @NonNull SeriesStatus seriesStatus) {
        this.id = id;
        this.seriesTitle = seriesTitle;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.seriesStatus = seriesStatus;
    }

    public Series(@NonNull String seriesTitle,
                  int seasonNumber, int episodeNumber,
                  @NonNull SeriesStatus seriesStatus) {
        this.id = null;
        this.seriesTitle = seriesTitle;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.seriesStatus = seriesStatus;
    }

    public Series() {
        this.id = null;
        this.seriesTitle = "";
        this.seasonNumber = 1;
        this.episodeNumber = 1;
        this.seriesStatus = null;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @NonNull
    public String getSeriesTitle() {
        return seriesTitle;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    @NonNull
    public SeriesStatus getSeriesStatus() {
        return seriesStatus;
    }

    public enum SeriesStatus {
        WATCHING,
        ARRIVING,
        TO_WATCH,
        COMPLETE,
        DROPPED;

        public static int getStringResId(@NonNull SeriesStatus status) {
            switch (status) {
                case TO_WATCH:
                    return R.string.to_watch;
                case WATCHING:
                    return R.string.watching;
                case COMPLETE:
                    return R.string.complete;
                case DROPPED:
                    return R.string.dropped;
                case ARRIVING:
                    return R.string.arriving;
                default:
                    return 0;
            }
        }
    }
}
