package com.cardee.data_source.inbox.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cardee.data_source.inbox.local.alert.entity.AlertOwner;
import com.cardee.data_source.inbox.local.alert.entity.AlertRenter;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;

@Database(entities = {Chat.class, ChatMessage.class, AlertRenter.class, AlertOwner.class}, version = 1)
public abstract class LocalInboxDatabase extends RoomDatabase {

    private static final String DB_NAME = "inbox_db";

    private static volatile LocalInboxDatabase INSTANCE;

    public static LocalInboxDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LocalInboxDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalInboxDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ChatDao getChatDao();

    public abstract AlertDao getAlertDao();

    public abstract ChatMessageDao getChatMassageDao();

    public abstract AlertMessageDao getAlertMessageDao();
}
