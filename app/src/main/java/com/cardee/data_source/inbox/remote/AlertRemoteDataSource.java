package com.cardee.data_source.inbox.remote;

import com.cardee.data_source.inbox.AlertDataSource;

public class AlertRemoteDataSource implements AlertDataSource {

    private static AlertRemoteDataSource INSTANCE;

    public static AlertRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AlertRemoteDataSource();
        }
        return INSTANCE;
    }

    private AlertRemoteDataSource() {
    }

}
