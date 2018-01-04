package com.cardee.data_source.inbox.remote.alert;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.remote.api.AlertApi;
import com.cardee.data_source.inbox.remote.api.model.AlertRemote;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class AlertRemoteDataSource implements RemoteData.AlertListSource {

    private final AlertApi mAlertApi;

    public AlertRemoteDataSource() {
        mAlertApi = CardeeApp.retrofit.create(AlertApi.class);
    }

    @Override
    public Single<List<Alert>> getRemoteAlerts(String attachment) {
        ToAlertMapper mapper = new ToAlertMapper(attachment);
        return mAlertApi.getAlerts(attachment)
                .map(alertListResponse -> mapper.map(alertListResponse.getRemoteList()));
    }

    private class ToAlertMapper implements Mapper<List<AlertRemote>, List<Alert>> {

        private final String attachment;

        ToAlertMapper(String attachment) {
            this.attachment = attachment;
        }

        @Override
        public List<Alert> map(List<AlertRemote> remoteList) {
            List<Alert> localList = new ArrayList<>(100);
            for (AlertRemote alertRemote : remoteList) {
                Alert alert = new Alert.Builder()
                        .withId(alertRemote.getAlertId())
                        .withAttachment(attachment)
                        .withDateCreated(alertRemote.getDateCreated())
                        .withNotificationText(alertRemote.getNotificationText())
                        .withNotificationType(convertNotificationType(alertRemote.getNotificationType()))
                        .withStatus(convertState(alertRemote.getNotificationState()))
                        .build();
                localList.add(alert);
            }
            return localList;
        }

        private boolean convertState(String remoteStatus) {
            return remoteStatus.equals(NotificationType.NEW);
        }

        private int convertNotificationType(String remoteType) {
            int notificationType;

            switch (remoteType) {
                case NotificationType.NEW_REQUEST:
                case NotificationType.ACCEPTED:
                case NotificationType.BOOKING_EXT:
                case NotificationType.OWNER_CHECKLIST_UPD:
                case NotificationType.RENTER_CHECKLIST_UPD:
                case NotificationType.INIT_CHECKLIST:
                    notificationType = Alert.TYPE_BOOKING;
                    break;
                case NotificationType.HANDOVER_REMINDER:
                case NotificationType.RETURN_REMINDER:
                case NotificationType.RENTER_REVIEW_REMINDER:
                case NotificationType.OWNER_REVIEW_REMINDER:
                    notificationType = Alert.TYPE_REMINDER;
                    break;
                case NotificationType.RETURN_OVERDUE:
                case NotificationType.REQUEST_EXPIRED:
                case NotificationType.BOOKING_CANCELLATION:
                    notificationType = Alert.TYPE_OVERDUE;
                    break;
                case NotificationType.OWNER_REVIEW:
                case NotificationType.RENTER_REVIEW:
                    notificationType = Alert.TYPE_REVIEWS;
                    break;
                case NotificationType.SYSTEM_MESSAGES:
                case NotificationType.CAR_VERIFICATION:
                case NotificationType.USER_VERIFICATION:
                case NotificationType.RENTER_STATE_CHANGE:
                case NotificationType.OWNER_STATE_CHANGE:
                case NotificationType.CAR_STATE_CHANGE:
                case NotificationType.BROADCAST:
                    notificationType = Alert.TYPE_SYSTEM;
                    break;
                default:
                    notificationType = Alert.TYPE_SYSTEM;
            }
            return notificationType;
        }
    }

}
