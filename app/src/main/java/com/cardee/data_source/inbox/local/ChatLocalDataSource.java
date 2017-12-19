package com.cardee.data_source.inbox.local;

import android.arch.persistence.room.Room;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.ChatDataSource;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

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
    public Observable<List<InboxChat>> getLocalChats(String attachment) {
        ChatMapper mapper = new ChatMapper();
        return mDataBase.getChatDao()
                .getChats(attachment)
                .map(mapper::map)
                .toObservable();
    }

    @Override
    public void saveDataToDb(List<Chat> inboxChats) {

    }

    @Override
    public Observable<List<InboxChat>> getRemoteChats(String attachment) {
        return null;
    }

    private class ChatMapper implements Mapper<List<Chat>, List<InboxChat>> {

        private List<InboxChat> mChats;

        ChatMapper() {
            mChats = new ArrayList<>();
        }

        @Override
        public List<InboxChat> map(List<Chat> localChats) {
            for (Chat localChat : localChats) {
                InboxChat chat = new InboxChat.Builder()
                        .withChatId(localChat.getChatId())
                        .withName(localChat.getRecipientName())
                        .withPhotoUrl(localChat.getPhotoUrl())
                        .withLastMessage(localChat.getLastMessageText())
                        .withLastMessageTime(localChat.getLastMessageTime())
                        .withCarPhoto(localChat.getCarPhotoUrl())
                        .build();
                mChats.add(chat);
            }
            return mChats;
        }
    }
}
