package com.cardee.data_source.inbox.local;

import android.arch.persistence.room.Room;
import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ChatLocalDataSource implements LocalDataSource {

    private static final String DB_NAME = "inbox_db";
    private static final String TAG = ChatLocalDataSource.class.getSimpleName();
    private static ChatLocalDataSource INSTANCE;

    private final LocalInboxDatabase mDataBase;
    private final ToInboxChatMapper mInboxChatMapper;
    private final ToChatMapper mChatMapper;

    public static ChatLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatLocalDataSource();
        }
        return INSTANCE;
    }

    private ChatLocalDataSource() {
        mDataBase = Room.databaseBuilder(CardeeApp.context, LocalInboxDatabase.class, DB_NAME).build();
        mInboxChatMapper = new ToInboxChatMapper();
        mChatMapper = new ToChatMapper();
    }

    @Override
    public Observable<List<InboxChat>> getLocalChats(String attachment) {
        return mDataBase.getChatDao()
                .getChats(attachment)
                .toObservable()
                .map(mInboxChatMapper::map);
    }

    @Override
    public Completable addChat(Chat chat) {
        return Completable.fromRunnable(() -> mDataBase.getChatDao().addChat(chat));
    }

    @Override
    public Completable updateChats(List<InboxChat> inboxChats) {
        return Completable.fromRunnable(() -> mDataBase.getChatDao().updateCars(mChatMapper.map(inboxChats)));
    }

    @Override
    public Single<Chat> getChat(Chat chat) {
        return mDataBase.getChatDao().
                getChat(chat.getChatId(), chat.getChatAttachment());
    }

    @Override
    public void fetchUpdates(List<InboxChat> oldChatList, List<InboxChat> newChatList) {
        List<InboxChat> listToUpdate = new ArrayList<>();
        for (InboxChat remoteChat : newChatList) {
            if (!oldChatList.contains(remoteChat)) {
                listToUpdate.add(remoteChat);
            }
        }
        if (!listToUpdate.isEmpty()) {
            saveChats(listToUpdate);
        }
    }

    @Override
    public void saveChats(List<InboxChat> inboxChats) {
        new Thread(() -> {
            mDataBase.getChatDao().addChats(mChatMapper.map(inboxChats));
            Log.e(TAG, "All chats persist");
        }).start();
    }

    private class ToInboxChatMapper implements Mapper<List<Chat>, List<InboxChat>> {

        private List<InboxChat> mChats;

        ToInboxChatMapper() {
            mChats = new ArrayList<>();
        }

        @Override
        public List<InboxChat> map(List<Chat> localChats) {
            mChats.clear();
            for (Chat localChat : localChats) {
                InboxChat chat = new InboxChat.Builder()
                        .withDatabaseId(localChat.getId())
                        .withChatId(localChat.getChatId())
                        .withChatAttachment(localChat.getChatAttachment())
                        .withName(localChat.getRecipientName())
                        .withPhotoUrl(localChat.getPhotoUrl())
                        .withLastMessage(localChat.getLastMessageText())
                        .withLastMessageTime(localChat.getLastMessageTime())
                        .withCarPhoto(localChat.getCarPhotoUrl())
                        .withUnreadMessageCount(localChat.getUnreadMessageCount())
                        .build();
                mChats.add(chat);
            }
            return mChats;
        }
    }

    private class ToChatMapper implements Mapper<List<InboxChat>, List<Chat>> {

        private List<Chat> mChats;

        ToChatMapper() {
            mChats = new ArrayList<>();
        }

        @Override
        public List<Chat> map(List<InboxChat> inboxChatList) {
            mChats.clear();
            for (InboxChat inboxChat : inboxChatList) {
                Chat chat = new Chat();
                chat.setChatId(inboxChat.getChatId());
                chat.setChatAttachment(inboxChat.getChatAttachment());
                chat.setUnreadMessageCount(inboxChat.getUnreadMessageCount());
                chat.setRecipientName(inboxChat.getRecipientName());
                chat.setPhotoUrl(inboxChat.getRecipientPhotoUrl());
                chat.setCarPhotoUrl(inboxChat.getCarPhotoUrl());
                chat.setLastMessageText(inboxChat.getLastMessageText());
                chat.setLastMessageTime(inboxChat.getLastMessageTime());
                mChats.add(chat);
            }
            return mChats;
        }
    }
}
