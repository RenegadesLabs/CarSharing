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
import com.cardee.data_source.inbox.service.model.BaseNotification;
import com.cardee.inbox.chat.single.view.ChatActivity;
import com.cardee.owner_home.view.OwnerHomeActivity;
import com.cardee.renter_home.view.RenterHomeActivity;

import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_ATTACHMENT;
import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_SERVER_ID;
import static com.cardee.data_source.inbox.local.chat.entity.Chat.CHAT_UNREAD_COUNT;

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
//                Intent intent = new Intent(context, ChatActivity.class);
//                intent.putExtras(createBundle(baseNotification));
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = null;
                if (baseNotification.isCurrentSessionNeedToNotify()) {
                    pendingIntent = createPendingIntent(context, baseNotification);
                }

//                PendingIntent pendingIntent = PendingIntent.getActivity(context, FCM_NOTIFICATION_REQUEST_CODE, intent,
//                        PendingIntent.FLAG_ONE_SHOT);

                mNotificationBuilder =
                        new NotificationCompat.Builder(context, channelId)
                                .setSmallIcon(R.drawable.ic_cardee_icon)
                                .setContentTitle(baseNotification.getContentTitle())
                                .setContentText(baseNotification.getContentText())
                                .setAutoCancel(true)
                                .setSound(mChatNotifySound);
                if (pendingIntent != null) {
                    mNotificationBuilder.setContentIntent(pendingIntent);
                }
                break;
            case ALERT:

                break;
        }
    }

    private PendingIntent createPendingIntent(Context context, BaseNotification baseNotification) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        Intent topIntent = new Intent(context, ChatActivity.class);
        topIntent.putExtras(createBundle(baseNotification));
        topIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent previousIntent = new Intent(context, baseNotification.isOwnerSession() ? OwnerHomeActivity.class : RenterHomeActivity.class);
        previousIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        stackBuilder.addNextIntent(previousIntent);
        stackBuilder.addNextIntent(topIntent);
        return stackBuilder.getPendingIntent(FCM_NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_ONE_SHOT);
    }

    private Bundle createBundle(BaseNotification baseNotification) {
        Bundle args = new Bundle();
        args.putInt(CHAT_SERVER_ID, baseNotification.getId());
        args.putString(CHAT_ATTACHMENT, baseNotification.getAttachment());
        args.putInt(CHAT_UNREAD_COUNT, baseNotification.getUnreadCount());
        return args;
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
