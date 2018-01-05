package com.cardee.data_source.inbox.local.alert;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.local.chat.LocalData;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.data_source.inbox.service.model.AlertNotification;
import com.cardee.domain.util.Mapper;

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
                .doOnComplete(() -> Log.e(TAG, "All alerts persist"))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void addAlert(AlertNotification alert) {
        NotificationMapper mapper = new NotificationMapper();
        Completable.fromRunnable(() -> mDataBase.getAlertDao()
                .addAlert(mapper.map(alert)))
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .doOnComplete(() -> Log.e(TAG, "Alert added"))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    class NotificationMapper implements Mapper<AlertNotification, Alert> {

        @Override
        public Alert map(AlertNotification notification) {
            return new Alert.Builder()
                    .withId(notification.getAlertId())
                    .withObject(notification.getObjectId())
                    .withAttachment(notification.getAttachment())
                    .withNotificationText(notification.getAlertMessage())
                    .withDateCreated(notification.getDateCreated())
                    .withStatus(notification.getAlertStatus())
                    .withNotificationType(notification.getAlertType())
                    .build();
        }
    }
}
