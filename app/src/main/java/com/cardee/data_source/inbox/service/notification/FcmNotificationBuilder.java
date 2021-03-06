package com.cardee.data_source.inbox.service.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.account_details.view.AccountDetailsActivity;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.service.model.AlertNotification;
import com.cardee.data_source.inbox.service.model.Notification;
import com.cardee.data_source.remote.service.AccountManager;
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
                        .setContentTitle(notification.getContentTitle())
                        .setContentText(notification.getContentText())
                        .setAutoCancel(true)
                        .setSound(mChatNotifySound)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getContentText()));

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mNotificationBuilder.setSmallIcon(R.drawable.ic_cardee_icon);
                } else {
                    mNotificationBuilder.setSmallIcon(R.drawable.ic_cardee_icon_png);
                }

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
                        .setContentTitle(notification.getContentTitle())
                        .setContentText(notification.getContentText())
                        .setAutoCancel(true)
                        .setSound(mChatNotifySound)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getContentText()));

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mNotificationBuilder.setSmallIcon(R.drawable.ic_cardee_icon);
                } else {
                    mNotificationBuilder.setSmallIcon(R.drawable.ic_cardee_icon_png);
                }

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

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                Intent previousIntent = new Intent(context, notification.isOwnerSession() ? OwnerHomeActivity.class : RenterHomeActivity.class);
                previousIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                stackBuilder.addNextIntent(previousIntent);

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
                            intent.putExtra(BookingActivity.IS_RENTER, false);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            stackBuilder.addNextIntent(intent);
                            return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
                        } else {
                            Intent intent = new Intent(context, BookingActivity.class);
                            intent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                            intent.putExtra(BookingActivity.IS_RENTER, true);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            stackBuilder.addNextIntent(intent);
                            return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
                        }
                    case USER_VERIFICATION:
                    case RENTER_STATE_CHANGE:
                    case OWNER_STATE_CHANGE:
                        Intent accountIntent = new Intent(context, AccountDetailsActivity.class);
                        accountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        stackBuilder.addNextIntent(accountIntent);
                        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
                    case BROADCAST:
                        //TODO: different UI every time.
                        break;
                    case RENTER_REVIEW_REMINDER:
                    case RENTER_REVIEW:
                        AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.RENTER_SESSION);

                        Intent renterRateIntent = new Intent(context, RateRentalExpActivity.class);
                        renterRateIntent.putExtra("booking_id", objectId);
                        renterRateIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        stackBuilder.addNextIntent(renterRateIntent);
                        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
                    case OWNER_CHECKLIST_UPD:
                        AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.RENTER_SESSION);
                        PendingChecklistStorage.addChecklist(context, objectId);

                        // if in BookingActivity shows CheckList
                        Intent showRenterCheckListIntent = new Intent(BookingActivity.ACTION_CHECKLIST_RENTER);
                        showRenterCheckListIntent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(showRenterCheckListIntent);


                        Intent ownerEditIntent = new Intent(context, BookingActivity.class);
                        ownerEditIntent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                        ownerEditIntent.putExtra(BookingActivity.IS_RENTER, true);
                        ownerEditIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        stackBuilder.addNextIntent(ownerEditIntent);
                        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
                    case RENTER_CHECKLIST_UPD:
                        AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.OWNER_SESSION);
                        PendingChecklistStorage.addChecklist(context, objectId);

                        // if in BookingActivity shows CheckList
                        Intent showCheckListIntent = new Intent(BookingActivity.ACTION_CHECKLIST_OWNER);
                        showCheckListIntent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(showCheckListIntent);

                        Intent renterEditIntent = new Intent(context, BookingActivity.class);
                        renterEditIntent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                        renterEditIntent.putExtra(BookingActivity.IS_RENTER, false);
                        renterEditIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        stackBuilder.addNextIntent(renterEditIntent);
                        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
                    case INIT_CHECKLIST:
                        AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.RENTER_SESSION);
                        PendingChecklistStorage.addChecklist(context, objectId);

                        // if in BookingActivity shows CheckList
                        Intent showInitCheckListIntent = new Intent(BookingActivity.ACTION_CHECKLIST_RENTER);
                        showInitCheckListIntent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(showInitCheckListIntent);

                        Intent checklistIntent = new Intent(context, BookingActivity.class);
                        checklistIntent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                        checklistIntent.putExtra(BookingActivity.IS_RENTER, true);
                        checklistIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        stackBuilder.addNextIntent(checklistIntent);
                        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
                    case OWNER_REVIEW:
                    case OWNER_REVIEW_REMINDER:
                        AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.OWNER_SESSION);

                        Intent ownerRateIntent = new Intent(context, CarReturnedActivity.class);
                        ownerRateIntent.putExtra("booking_id", objectId);
                        ownerRateIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        stackBuilder.addNextIntent(ownerRateIntent);
                        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
                    case CAR_VERIFICATION:
                    case CAR_STATE_CHANGE:
                        AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.OWNER_SESSION);

                        Intent ownerCarIntent = new Intent(context, OwnerCarDetailsActivity.class);
                        ownerCarIntent.putExtra(OwnerCarDetailsContract.CAR_ID, objectId);
                        ownerCarIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        stackBuilder.addNextIntent(ownerCarIntent);
                        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
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
