package com.cardee.data_source.inbox.local.alert;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.local.chat.ChatListLocalSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class AlertListLocalSource implements LocalData.AlertListSource {

    private static final String TAG = AlertListLocalSource.class.getSimpleName();
    private final LocalInboxDatabase mDataBase;

    public AlertListLocalSource() {
        mDataBase = LocalInboxDatabase.getInstance(CardeeApp.context);
    }

    @Override
    public Flowable<List<Alert>> getLocalAlerts(String attachment) {
        return null;
    }

    @Override
    public void saveAlerts(List<Alert> inboxAlerts) {

    }

    @Override
    public Completable addAlert(Alert alert) {
        return null;
    }
}
