package com.cardee.data_source.inbox.service.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.cardee.R;
import com.cardee.data_source.inbox.service.model.Notification;
import com.cardee.inbox.chat.single.view.ChatActivity;
import com.cardee.owner_home.view.OwnerHomeActivity;
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
        //TODO: implement pending intent destination based on alert type
        return null;
    }

    @Override
    public void showNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null && mNotificationBuilder != null) {
            manager.notify(FCM_NOTIFICATION_NOTIFICATION_CODE, mNotificationBuilder.build());
        } else {
            Log.e(TAG, "Notification manager is null");
        }
    }
}
