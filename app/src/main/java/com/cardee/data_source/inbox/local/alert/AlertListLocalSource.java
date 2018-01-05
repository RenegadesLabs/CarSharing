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
                    .withStatus(convertState(notification.getAlertState()))
                    .withNotificationType(convertNotificationType(notification.getAlertType()))
                    .build();
        }

        private boolean convertState(String remoteStatus) {
            return remoteStatus.equals(RemoteData.NotificationType.NEW);
        }

        private int convertNotificationType(String remoteType) {
            int notificationType;

            switch (remoteType) {
                case RemoteData.NotificationType.NEW_REQUEST:
                case RemoteData.NotificationType.ACCEPTED:
                case RemoteData.NotificationType.BOOKING_EXT:
                case RemoteData.NotificationType.OWNER_CHECKLIST_UPD:
                case RemoteData.NotificationType.RENTER_CHECKLIST_UPD:
                case RemoteData.NotificationType.INIT_CHECKLIST:
                    notificationType = Alert.TYPE_BOOKING;
                    break;
                case RemoteData.NotificationType.HANDOVER_REMINDER:
                case RemoteData.NotificationType.RETURN_REMINDER:
                case RemoteData.NotificationType.RENTER_REVIEW_REMINDER:
                case RemoteData.NotificationType.OWNER_REVIEW_REMINDER:
                    notificationType = Alert.TYPE_REMINDER;
                    break;
                case RemoteData.NotificationType.RETURN_OVERDUE:
                case RemoteData.NotificationType.REQUEST_EXPIRED:
                case RemoteData.NotificationType.BOOKING_CANCELLATION:
                    notificationType = Alert.TYPE_OVERDUE;
                    break;
                case RemoteData.NotificationType.OWNER_REVIEW:
                case RemoteData.NotificationType.RENTER_REVIEW:
                    notificationType = Alert.TYPE_REVIEWS;
                    break;
                case RemoteData.NotificationType.SYSTEM_MESSAGES:
                case RemoteData.NotificationType.CAR_VERIFICATION:
                case RemoteData.NotificationType.USER_VERIFICATION:
                case RemoteData.NotificationType.RENTER_STATE_CHANGE:
                case RemoteData.NotificationType.OWNER_STATE_CHANGE:
                case RemoteData.NotificationType.CAR_STATE_CHANGE:
                case RemoteData.NotificationType.BROADCAST:
                    notificationType = Alert.TYPE_SYSTEM;
                    break;
                default:
                    notificationType = Alert.TYPE_SYSTEM;
            }
            return notificationType;
        }
    }
}
