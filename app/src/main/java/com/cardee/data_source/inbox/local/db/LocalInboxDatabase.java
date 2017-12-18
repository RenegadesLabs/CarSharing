package com.cardee.data_source.inbox.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cardee.data_source.inbox.local.entity.AlertOwner;
import com.cardee.data_source.inbox.local.entity.AlertRenter;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.data_source.inbox.local.entity.ChatMessage;

@Database(entities = {Chat.class, ChatMessage.class, AlertRenter.class, AlertOwner.class}, version = 1)
public abstract class LocalInboxDatabase extends RoomDatabase {

    public abstract ChatDao getChatDao();

    public abstract AlertDao getAlertDao();

    public abstract ChatMessageDao getChatMassageDao();

    public abstract AlertMessageDao getAlertMessageDao();
}
