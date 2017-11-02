package com.karcisse.myseriesappv2.data.source.local;

import android.provider.BaseColumns;

public final class SeriesPersistenceContract {

    private SeriesPersistenceContract() { }

    public abstract static class SeriesEntry implements BaseColumns {
        public static final String TABLE_NAME = "series";
        public static final String SERIES_ID = "seriesId";
        public static final String SERIES_TITLE = "seriesTitle";
        public static final String SERIES_SEASON = "seriesSeason";
        public static final String SERIES_EPISODE = "seriesEpisode";
        public static final String SERIES_STATUS = "seriesStatus";
    }
}
