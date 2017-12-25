package com.cardee.data_source.inbox.remote;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.data_source.inbox.remote.api.InboxApi;
import com.cardee.data_source.inbox.remote.api.model.ChatRemote;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatRemoteDataSource implements RemoteDataSource {

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
    public Single<List<Chat>> getRemoteChats(String attachment) {
        ChatMapper mapper = new ChatMapper(attachment);
        return mInboxApi.getChats(attachment)
                .observeOn(AndroidSchedulers.mainThread())
                .map(chatListResponse -> mapper.map(chatListResponse.getChatRemotes()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Chat> getChat(Chat chat) {
        return null;
    }

    private static class ChatMapper implements Mapper<ChatRemote[], List<Chat>> {

        private List<Chat> mChats;
        private String mAttachment;

        ChatMapper(String attachment) {
            mAttachment = attachment;
            mChats = new ArrayList<>();
        }

        @Override
        public List<Chat> map(ChatRemote[] remoteChatRemotes) {
            for (ChatRemote remoteChatRemote : remoteChatRemotes) {
                Chat chat = new Chat.Builder()
                        .withChatId(remoteChatRemote.getChatId())
                        .withChatAttachment(mAttachment)
                        .withName(remoteChatRemote.getRecipient().getName())
                        .withPhotoUrl(remoteChatRemote.getRecipient().getPhoto())
                        .withLastMessage(remoteChatRemote.getLastMessage().getMessage())
                        .withLastMessageTime(remoteChatRemote.getLastMessage().getDateCreated())
                        .withUnreadMessageCount(remoteChatRemote.getNewCount())
                        .withCarTitle(remoteChatRemote.getCarVersion().getCarTitle())
                        .withCarPhoto(remoteChatRemote.getCarVersion().getImageUrl())
                        .withLicenseNumber(remoteChatRemote.getCarVersion().getCarNumber())
                        .withBookingBegin(remoteChatRemote.getBooking().getTimeBegin())
                        .withBookingEnd(remoteChatRemote.getBooking().getTimeEnd())
                        .build();
                mChats.add(chat);
            }
            return mChats;
        }
    }
}
