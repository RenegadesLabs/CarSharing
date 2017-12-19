package com.cardee.data_source.inbox.local;

import android.arch.persistence.room.Room;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.ChatDataSource;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class ChatLocalDataSource implements ChatDataSource {

    private static final String DB_NAME = "inbox_db";

    private static ChatLocalDataSource INSTANCE;
    private final LocalInboxDatabase mDataBase;

    public static ChatLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatLocalDataSource();
        }
        return INSTANCE;
    }

    private ChatLocalDataSource() {
        mDataBase = Room.databaseBuilder(CardeeApp.context, LocalInboxDatabase.class, DB_NAME).build();
    }

    @Override
    public Maybe<List<InboxChat>> getLocalChats() {
        return null;
    }

    @Override
    public Single<List<InboxChat>> getRemoteChats() {
        return null;
    }
}
