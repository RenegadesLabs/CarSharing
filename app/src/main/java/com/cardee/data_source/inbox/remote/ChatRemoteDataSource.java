package com.cardee.data_source.inbox.remote;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.ChatDataSource;
import com.cardee.data_source.inbox.remote.api.InboxApi;
import com.cardee.data_source.inbox.remote.api.model.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatRemoteDataSource implements ChatDataSource {

    private static ChatRemoteDataSource INSTANCE;
    private final InboxApi mInboxApi;

    public static ChatRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatRemoteDataSource();
        }
        return INSTANCE;
    }

    private ChatRemoteDataSource() {
        mInboxApi = CardeeApp.retrofit.create(InboxApi.class);
    }

    @Override
    public Single<List<InboxChat>> getRemoteChats() {
        ChatMapper mapper = new ChatMapper();
        return mInboxApi.getChats()
                .observeOn(AndroidSchedulers.mainThread())
                .map(chatListResponse -> mapper.map(chatListResponse.getChats()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<InboxChat>> getLocalChats() {
        return null;
    }

    private static class ChatMapper implements Mapper<Chat[], List<InboxChat>> {

        private List<InboxChat> mRemoteChats;

        ChatMapper() {
            mRemoteChats = new ArrayList<>();
        }

        @Override
        public List<InboxChat> map(Chat[] remoteChats) {
            for (Chat remoteChat : remoteChats) {
                InboxChat chat = new InboxChat.Builder()
                        .withChatId(remoteChat.getChatId())
                        .withName(remoteChat.getRecipient().getName())
                        .withPhotoUrl(remoteChat.getRecipient().getPhoto())
                        .withLastMessage(remoteChat.getLastMessage().getMessage())
                        .withLastMessageTime(remoteChat.getLastMessage().getDateCreated())
                        .withUnreadMessageCount(remoteChat.getNewCount())
                        .withCarPhoto(remoteChat.getCarVersion().getImageUrl())
                        .build();
                mRemoteChats.add(chat);
            }
            return mRemoteChats;
        }
    }
}
