package com.cardee.data_source.inbox.local.alert;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.local.chat.ChatListLocalSource;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class AlertListLocalSource implements LocalData.AlertListSource {

    private static final String TAG = AlertListLocalSource.class.getSimpleName();
    private final LocalInboxDatabase mDataBase;

    public AlertListLocalSource() {
        mDataBase = LocalInboxDatabase.getInstance(CardeeApp.context);
    }

    @Override
    public Flowable<List<Alert>> getLocalAlerts(String attachment) {
        return mDataBase.getAlertDao().getAlerts(attachment);
    }

    @Override
    public void saveAlerts(List<Alert> inboxAlerts) {
        Completable.fromRunnable(() -> mDataBase.getAlertDao()
                .addAlerts(inboxAlerts))
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .doOnComplete(() -> Log.e(TAG, "All chats persist"))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public Completable addAlert(Alert alert) {
        return null;
    }
}
