package com.cardee.data_source.inbox.service.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.cardee.R;
import com.cardee.account_details.view.AccountDetailsActivity;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.service.model.AlertNotification;
import com.cardee.data_source.inbox.service.model.Notification;
import com.cardee.inbox.chat.single.view.ChatActivity;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.car_checklist.service.PendingChecklistStorage;
import com.cardee.owner_bookings.car_returned.view.CarReturnedActivity;
import com.cardee.owner_bookings.view.BookingActivity;
import com.cardee.owner_car_details.OwnerCarDetailsContract;
import com.cardee.owner_car_details.view.OwnerCarDetailsActivity;
import com.cardee.owner_home.view.OwnerHomeActivity;
import com.cardee.renter_bookings.rate_rental_exp.view.RateRentalExpActivity;
import com.cardee.renter_home.view.RenterHomeActivity;

import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_ATTACHMENT;
import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_SERVER_ID;
import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_UNREAD_COUNT;

public class FcmNotificationBuilder implements NotificationBuilder {

    private static final String TAG = NotificationBuilder.class.getSimpleName();

    private final static int FCM_NOTIFICATION_REQUEST_CODE = 3341;
    private final static int FCM_NOTIFICATION_NOTIFICATION_CODE = 3342;

    private NotificationCompat.Builder mNotificationBuilder;
    private final Uri mChatNotifySound;

    public FcmNotificationBuilder() {
        mChatNotifySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    @Override
    public void createNotification(Context context, Notification notification) {
        PendingIntent pendingIntent = null;

        switch (notification.getNotificationType()) {
            case CHAT:
                String channelChatId = context.getString(R.string.chat_notification_channel_id);
                if (notification.isCurrentSessionNeedToNotify()) {
                    pendingIntent = createChatPendingIntent(context, notification);
                }
                mNotificationBuilder = new NotificationCompat.Builder(context, channelChatId)
                        .setSmallIcon(R.drawable.ic_cardee_icon)
                        .setContentTitle(notification.getContentTitle())
                        .setContentText(notification.getContentText())
                        .setAutoCancel(true)
                        .setSound(mChatNotifySound);
                if (pendingIntent != null) {
                    mNotificationBuilder.setContentIntent(pendingIntent);
                }
                break;
            case ALERT:
                String channelAlertId = context.getString(R.string.alert_notification_channel_id);
                if (notification.isCurrentSessionNeedToNotify()) {
                    pendingIntent = createAlertPendingIntent(context, notification);
                }
                mNotificationBuilder = new NotificationCompat.Builder(context, channelAlertId)
//                        .setSmallIcon(getValidAlertImage(notification.getType()))
                        .setSmallIcon(R.drawable.ic_cardee_icon)
                        .setContentTitle(notification.getContentTitle())
                        .setContentText(notification.getContentText())
                        .setAutoCancel(true)
                        .setSound(mChatNotifySound);

                if (pendingIntent != null) {
                    mNotificationBuilder.setContentIntent(pendingIntent);
                }
                break;
        }
    }

    private PendingIntent createChatPendingIntent(Context context, Notification notification) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        Intent topIntent = new Intent(context, ChatActivity.class);
        topIntent.putExtras(createChatBundle(notification));
        topIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent previousIntent = new Intent(context, notification.isOwnerSession() ? OwnerHomeActivity.class : RenterHomeActivity.class);
        previousIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        stackBuilder.addNextIntent(previousIntent);
        stackBuilder.addNextIntent(topIntent);

        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
    }

    private Bundle createChatBundle(Notification notification) {
        Bundle args = new Bundle();
        args.putInt(CHAT_SERVER_ID, notification.getId());
        args.putString(CHAT_ATTACHMENT, notification.getAttachment());
        args.putInt(CHAT_UNREAD_COUNT, notification.getUnreadCount());
        return args;
    }

    private PendingIntent createAlertPendingIntent(Context context, Notification notification) {
        AlertNotification alertNotification = (AlertNotification) notification;
        if (alertNotification != null) {
            Alert.Type type = alertNotification.getType();
            if (type != null) {
                int objectId = alertNotification.getObjectId();
                switch (type) {
                    case ACCEPTED:
                    case REQUEST_EXPIRED:
                    case HANDOVER_REMINDER:
                    case RETURN_OVERDUE:
                    case NEW_REQUEST:
                    case BOOKING_CANCELLATION:
                    case BOOKING_EXT:
                    case RETURN_REMINDER:
                        if (alertNotification.isOwnerSession()) {
                            Intent intent = new Intent(context, BookingActivity.class);
                            intent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                            return PendingIntent.getActivity(
                                    context,
                                    FCM_NOTIFICATION_REQUEST_CODE,
                                    intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                        } else {
                            //TODO: implement for Renter
                        }
                        break;
                    case USER_VERIFICATION:
                    case RENTER_STATE_CHANGE:
                    case OWNER_STATE_CHANGE:
                        Intent accountIntent = new Intent(context, AccountDetailsActivity.class);
                        return PendingIntent.getActivity(
                                context,
                                FCM_NOTIFICATION_REQUEST_CODE,
                                accountIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                    case BROADCAST:
                        //TODO: different UI every time.
                        break;
                    case RENTER_REVIEW_REMINDER:
                    case RENTER_REVIEW:
                        Intent renterRateIntent = new Intent(context, RateRentalExpActivity.class);
                        renterRateIntent.putExtra("booking_id", objectId);
                        return PendingIntent.getActivity(
                                context,
                                FCM_NOTIFICATION_REQUEST_CODE,
                                renterRateIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                    case OWNER_CHECKLIST_UPD:
                        //TODO:  editedCheckList();
                        break;
                    case RENTER_CHECKLIST_UPD:
                        PendingChecklistStorage.addChecklist(context, objectId);

                        // if in BookingActivity shows CheckList
                        Intent showCheckListIntent = new Intent(BookingActivity.ACTION_CHECKLIST);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(showCheckListIntent);
                        //TODO:  editedCheckList();
                        break;
                    case INIT_CHECKLIST:
                        //TODO: checkList();
                        break;
                    case OWNER_REVIEW:
                    case OWNER_REVIEW_REMINDER:
                        Intent ownerRateIntent = new Intent(context, CarReturnedActivity.class);
                        ownerRateIntent.putExtra("booking_id", objectId);
                        return PendingIntent.getActivity(
                                context,
                                FCM_NOTIFICATION_REQUEST_CODE,
                                ownerRateIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                    case CAR_VERIFICATION:
                    case CAR_STATE_CHANGE:
                        Intent ownerCarIntent = new Intent(context, OwnerCarDetailsActivity.class);
                        ownerCarIntent.putExtra(OwnerCarDetailsContract.CAR_ID, objectId);
                        return PendingIntent.getActivity(
                                context,
                                FCM_NOTIFICATION_REQUEST_CODE,
                                ownerCarIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                    case SYSTEM_MESSAGES:
                        // ignore;
                        break;
                }
            }
        }
        return null;
    }

    // do not use colored images!
    private int getValidAlertImage(Alert.Type alertType) {
        int drawableResource = R.drawable.ic_notification_system;
        switch (alertType) {
            case NEW_REQUEST:
            case ACCEPTED:
            case BOOKING_EXT:
            case OWNER_CHECKLIST_UPD:
            case RENTER_CHECKLIST_UPD:
            case INIT_CHECKLIST:
                drawableResource = R.drawable.ic_alert_booking;
                break;
            case RETURN_OVERDUE:
            case REQUEST_EXPIRED:
            case BOOKING_CANCELLATION:
                drawableResource = R.drawable.ic_alert_overdue;
                break;
            case HANDOVER_REMINDER:
            case RETURN_REMINDER:
            case RENTER_REVIEW_REMINDER:
            case OWNER_REVIEW_REMINDER:
                drawableResource = R.drawable.ic_alert_reminder;
                break;
            case OWNER_REVIEW:
            case RENTER_REVIEW:
                drawableResource = R.drawable.ic_alert_review;
                break;
            case SYSTEM_MESSAGES:
            case CAR_VERIFICATION:
            case USER_VERIFICATION:
            case RENTER_STATE_CHANGE:
            case OWNER_STATE_CHANGE:
            case CAR_STATE_CHANGE:
            case BROADCAST:
                drawableResource = R.drawable.ic_alert_system;
                break;
        }
        return drawableResource;
    }

    @Override
    public void showNotification(Context context, Notification notification) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null && mNotificationBuilder != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String channelId = null;
                CharSequence channelName = null;
                switch (notification.getNotificationType()) {
                    case CHAT:
                        channelId = context.getString(R.string.chat_notification_channel_id);
                        channelName = context.getString(R.string.chat_notification_channel_name);
                        break;
                    case ALERT:
                        channelId = context.getString(R.string.alert_notification_channel_id);
                        channelName = context.getString(R.string.alert_notification_channel_name);
                        break;
                }
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                manager.createNotificationChannel(notificationChannel);
            }

            manager.notify(FCM_NOTIFICATION_NOTIFICATION_CODE, mNotificationBuilder.build());
        } else {
            Log.e(TAG, "Notification manager is null");
        }
    }
}
