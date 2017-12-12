package com.cardee.data_source.inbox.remote;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.ChatDataSource;
import com.cardee.data_source.inbox.remote.api.InboxApi;
import com.cardee.data_source.inbox.remote.api.model.ChatRemote;
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
                .map(chatListResponse -> mapper.map(chatListResponse.getChatRemotes()))
                .subscribeOn(Schedulers.io());
    }

    private static class ChatMapper implements Mapper<ChatRemote[], List<InboxChat>> {

        private List<InboxChat> mRemoteChats;

        ChatMapper() {
            mRemoteChats = new ArrayList<>();
        }

        @Override
        public List<InboxChat> map(ChatRemote[] remoteChatRemotes) {
            for (ChatRemote remoteChatRemote : remoteChatRemotes) {
                InboxChat chat = new InboxChat.Builder()
                        .withChatId(remoteChatRemote.getChatId())
                        .withName(remoteChatRemote.getRecipient().getName())
                        .withPhotoUrl(remoteChatRemote.getRecipient().getPhoto())
                        .withLastMessage(remoteChatRemote.getLastMessage().getMessage())
                        .withLastMessageTime(remoteChatRemote.getLastMessage().getDateCreated())
                        .withUnreadMessageCount(remoteChatRemote.getNewCount())
                        .withCarPhoto(remoteChatRemote.getCarVersion().getImageUrl())
                        .build();
                mRemoteChats.add(chat);
            }
            return mRemoteChats;
        }
    }

    @Override
    public Maybe<List<InboxChat>> getLocalChats() {
        return null;
    }

    @Override
    public void saveDataToDb(List<com.cardee.data_source.inbox.local.entity.Chat> inboxChats) {

    }
}
