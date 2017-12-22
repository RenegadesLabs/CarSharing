package com.cardee.data_source.inbox.local;

import android.arch.persistence.room.Room;
import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ChatLocalDataSource implements LocalDataSource {

    private static final String DB_NAME = "inbox_db";
    private static final String TAG = ChatLocalDataSource.class.getSimpleName();
    private static ChatLocalDataSource INSTANCE;

    private final LocalInboxDatabase mDataBase;
    private final DBChatToInboxChat mInboxChatMapper;
    private final InboxChatToDBChat mChatMapper;

    public static ChatLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatLocalDataSource();
        }
        return INSTANCE;
    }

    private ChatLocalDataSource() {
        mDataBase = Room.databaseBuilder(CardeeApp.context, LocalInboxDatabase.class, DB_NAME).build();
        mInboxChatMapper = new DBChatToInboxChat();
        mChatMapper = new InboxChatToDBChat();
    }

    @Override
    public Single<List<InboxChat>> getLocalChats(String attachment) {
        return mDataBase.getChatDao()
                .getChats(attachment)
                .map(mInboxChatMapper::map);
    }

    @Override
    public Observable<InboxChat> subscribeToDb(String attachment) {
        return Observable.create(emitter -> mDataBase.getChatDao()
                .subscribe(attachment)
                .map(mInboxChatMapper::map)
                .doOnNext(chats -> Observable
                        .fromIterable(chats)
                        .distinct()
                        .subscribe(emitter::onNext)));
    }

    @Override
    public void addChat(Chat chat) {
        Completable
                .fromRunnable(() -> mDataBase.getChatDao().addChat(chat))
                .doOnComplete(() -> Log.d(TAG, "Chat added: " + chat.getRecipientName() + " " + chat.getLastMessageText()))
                .doOnError(throwable -> Log.e(TAG, throwable.toString()))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public Single<Chat> getChat(Chat chat) {
        return mDataBase.getChatDao().getChat(chat.getChatId(), chat.getChatAttachment());
    }

    @Override
    public void fetchUpdates(List<InboxChat> oldChatList, List<InboxChat> newChatList) {
        Completable.fromRunnable(() -> {
            List<InboxChat> listToUpdate = new ArrayList<>();
            for (InboxChat remoteChat : newChatList) {
                if (!oldChatList.contains(remoteChat)) {
                    int position = oldChatList.indexOf(remoteChat);
                    if (isExistChat(position)) {
                        remoteChat.setDatabaseId(oldChatList.get(position).getDatabaseId());
                    }
                    listToUpdate.add(remoteChat);
                }
            }
            if (!listToUpdate.isEmpty()) {
                saveChats(listToUpdate);
            }
        }).subscribeOn(Schedulers.io()).subscribe(() -> Log.e(TAG, "Starting fetch data..."), throwable -> Log.e(TAG, throwable.getMessage()));
    }

    private boolean isExistChat(int position) {
        return position != -1;
    }

    @Override
    public void saveChats(List<InboxChat> inboxChats) {
        Completable
                .fromRunnable(() -> mDataBase.getChatDao().addChats(mChatMapper.map(inboxChats)))
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .doOnComplete(() -> Log.e(TAG, "All chats persist"))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void updateChats(List<InboxChat> inboxChats) {
        Completable
                .fromRunnable(() -> mDataBase.getChatDao().updateChats(mChatMapper.map(inboxChats)))
                .doOnComplete(() -> Log.d(TAG, "Chat updated"))
                .doOnError(throwable -> Log.e(TAG, throwable.toString()))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private class DBChatToInboxChat implements Mapper<List<Chat>, List<InboxChat>> {

        private List<InboxChat> mChats;

        DBChatToInboxChat() {
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
            Collections.sort(mChats);
            return mChats;
        }
    }

    private class InboxChatToDBChat implements Mapper<List<InboxChat>, List<Chat>> {

        private List<Chat> mChats;

        InboxChatToDBChat() {
            mChats = new ArrayList<>();
        }

        @Override
        public List<Chat> map(List<InboxChat> inboxChatList) {
            mChats.clear();
            for (InboxChat inboxChat : inboxChatList) {
                Chat chat = new Chat();
                chat.setId(inboxChat.getDatabaseId());
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
