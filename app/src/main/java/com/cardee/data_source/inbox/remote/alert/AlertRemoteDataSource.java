package com.cardee.data_source.inbox.remote.alert;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.remote.chat.RemoteData;

import java.util.List;

import io.reactivex.Single;

public class AlertRemoteDataSource implements RemoteData.AlertListSource {

    public AlertRemoteDataSource() {
    }

    @Override
    public Single<List<Alert>> getRemoteAlerts(String attachment) {
        return null;
    }

    @Override
    public Single<Alert> getSingleAlert(int serverId, String attachment) {
        return null;
    }
}
