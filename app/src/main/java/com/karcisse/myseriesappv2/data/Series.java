package com.karcisse.myseriesappv2.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.karcisse.myseriesappv2.R;

public class Series {
    @Nullable private final String id;
    @NonNull private final String seriesTitle;
    private final int seasonNumber;
    private final int episodeNumber;
    @NonNull private final Status status;

    public Series(@Nullable String id, @NonNull String seriesTitle,
                  int seasonNumber, int episodeNumber, @NonNull Status status) {
        this.id = id;
        this.seriesTitle = seriesTitle;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.status = status;
    }

    public Series(@NonNull String seriesTitle,
                  int seasonNumber, int episodeNumber,
                  @NonNull Status status) {
        this.id = null;
        this.seriesTitle = seriesTitle;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.status = status;
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
    public Status getStatus() {
        return status;
    }

    public enum Status {
        WATCHING,
        ARRIVING,
        TO_WATCH,
        COMPLETE,
        DROPPED;

        public static int getStringResId(@NonNull Status status) {
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
