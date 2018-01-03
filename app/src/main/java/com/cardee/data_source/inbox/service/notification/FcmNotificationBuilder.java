package com.cardee.data_source.inbox.service.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cardee.R;
import com.cardee.data_source.inbox.service.controller.FcmMessageMapper;
import com.cardee.data_source.inbox.service.model.BaseNotification;
import com.google.firebase.messaging.RemoteMessage;

public class FcmNotificationBuilder implements NotificationBuilder {

    private static final String TAG = NotificationBuilder.class.getSimpleName();
    private static final String BOOKING = "booking";
    private static final String CHAT = "chat";

    private final static int FCM_NOTIFICATION_REQUEST_CODE = 3341;
    private final static int FCM_NOTIFICATION_NOTIFICATION_CODE = 3342;

    private NotificationCompat.Builder mNotificationBuilder;

    private final Uri mChatNotifySound;

    public FcmNotificationBuilder() {
        mChatNotifySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    @Override
    public void createNotification(Context context, BaseNotification baseNotification) {
        switch (baseNotification.getNotificationType()) {
            case CHAT:
                String channelId = context.getString(R.string.chat_notification_channel_id);
//                Intent intent = new Intent(context, SplashActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                PendingIntent pendingIntent = PendingIntent.getActivity(context, FCM_NOTIFICATION_REQUEST_CODE, intent,
//                        PendingIntent.FLAG_ONE_SHOT);

                mNotificationBuilder =
                        new NotificationCompat.Builder(context, channelId)
                                .setSmallIcon(R.drawable.ic_cardee_icon)
                                .setContentTitle(baseNotification.getContentTitle())
                                .setContentText(baseNotification.getContentText())
                                .setAutoCancel(true)
                                .setSound(mChatNotifySound);
//                        .setContentIntent(pendingIntent);
                break;
            case ALERT:

                break;
        }
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
