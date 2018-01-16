package com.cardee.data_source.inbox.remote.alert;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.remote.api.AlertApi;
import com.cardee.data_source.inbox.remote.api.model.AlertRemote;
import com.cardee.data_source.inbox.remote.api.request.AlertsRequest;
import com.cardee.data_source.inbox.remote.chat.RemoteData;
import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class AlertRemoteDataSource implements RemoteData.AlertListSource {

    private final AlertApi mAlertApi;

    public AlertRemoteDataSource() {
        mAlertApi = CardeeApp.retrofit.create(AlertApi.class);
    }

    @Override
    public Single<List<Alert>> getRemoteAlerts(String attachment) {
        ToAlertMapper mapper = new ToAlertMapper(attachment);
        return mAlertApi.getAlerts(attachment)
                .subscribeOn(Schedulers.io())
                .map(alertListResponse -> mapper.map(alertListResponse.getRemoteList()));
    }

    @Override
    public Single<NoDataResponse> markAlertsAsRead(List<Integer> alerts) {
        AlertsRequest request = new AlertsRequest();
        request.setAlerts(alerts);
        return mAlertApi.markAsRead(request).subscribeOn(Schedulers.io());
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
                        .withObject(alertRemote.getObjectId())
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

        private String convertNotificationType(String remoteType) {
            String notificationType;

            switch (remoteType) {
                case NotificationType.NEW_REQUEST:
                    notificationType = Alert.Type.NEW_REQUEST.toString();
                    break;
                case NotificationType.ACCEPTED:
                    notificationType = Alert.Type.ACCEPTED.toString();
                    break;
                case NotificationType.BOOKING_EXT:
                    notificationType = Alert.Type.BOOKING_EXT.toString();
                    break;
                case NotificationType.OWNER_CHECKLIST_UPD:
                    notificationType = Alert.Type.OWNER_CHECKLIST_UPD.toString();
                    break;
                case NotificationType.RENTER_CHECKLIST_UPD:
                    notificationType = Alert.Type.RENTER_CHECKLIST_UPD.toString();
                    break;
                case NotificationType.INIT_CHECKLIST:
                    notificationType = Alert.Type.INIT_CHECKLIST.toString();
                    break;
                case NotificationType.HANDOVER_REMINDER:
                    notificationType = Alert.Type.HANDOVER_REMINDER.toString();
                    break;
                case NotificationType.RETURN_REMINDER:
                    notificationType = Alert.Type.RETURN_REMINDER.toString();
                    break;
                case NotificationType.RENTER_REVIEW_REMINDER:
                    notificationType = Alert.Type.RENTER_REVIEW_REMINDER.toString();
                    break;
                case NotificationType.OWNER_REVIEW_REMINDER:
                    notificationType = Alert.Type.OWNER_REVIEW_REMINDER.toString();
                    break;
                case NotificationType.RETURN_OVERDUE:
                    notificationType = Alert.Type.RETURN_OVERDUE.toString();
                    break;
                case NotificationType.REQUEST_EXPIRED:
                    notificationType = Alert.Type.REQUEST_EXPIRED.toString();
                    break;
                case NotificationType.BOOKING_CANCELLATION:
                    notificationType = Alert.Type.BOOKING_CANCELLATION.toString();
                    break;
                case NotificationType.OWNER_REVIEW:
                    notificationType = Alert.Type.OWNER_REVIEW.toString();
                    break;
                case NotificationType.RENTER_REVIEW:
                    notificationType = Alert.Type.RENTER_REVIEW.toString();
                    break;
                case NotificationType.SYSTEM_MESSAGES:
                    notificationType = Alert.Type.SYSTEM_MESSAGES.toString();
                    break;
                case NotificationType.CAR_VERIFICATION:
                    notificationType = Alert.Type.CAR_VERIFICATION.toString();
                    break;
                case NotificationType.USER_VERIFICATION:
                    notificationType = Alert.Type.USER_VERIFICATION.toString();
                    break;
                case NotificationType.RENTER_STATE_CHANGE:
                    notificationType = Alert.Type.RENTER_STATE_CHANGE.toString();
                    break;
                case NotificationType.OWNER_STATE_CHANGE:
                    notificationType = Alert.Type.OWNER_STATE_CHANGE.toString();
                    break;
                case NotificationType.CAR_STATE_CHANGE:
                    notificationType = Alert.Type.CAR_STATE_CHANGE.toString();
                    break;
                case NotificationType.BROADCAST:
                    notificationType = Alert.Type.BROADCAST.toString();
                    break;
                default:
                    notificationType = Alert.Type.SYSTEM_MESSAGES.toString();
            }
            return notificationType;
        }
    }
}
