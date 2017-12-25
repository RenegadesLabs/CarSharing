package com.cardee.data_source.inbox.remote.alert;

public class AlertRemoteDataSource {

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
