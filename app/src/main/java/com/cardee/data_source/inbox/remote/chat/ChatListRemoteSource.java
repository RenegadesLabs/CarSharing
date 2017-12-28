package com.cardee.data_source.inbox.remote.chat;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.remote.api.ChatApi;
import com.cardee.data_source.inbox.remote.api.model.ChatRemote;
import com.cardee.data_source.inbox.remote.api.response.ChatSingleResponse;
import com.cardee.domain.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatListRemoteSource implements RemoteData.ChatListSource {

    private final ChatApi mChatApi;

    public ChatListRemoteSource() {
        mChatApi = CardeeApp.retrofit.create(ChatApi.class);
    }

    @Override
    public Single<List<Chat>> getRemoteChats(String attachment) {
        ChatListMapper mapper = new ChatListMapper(attachment);
        return mChatApi.getChats(attachment)
                .observeOn(AndroidSchedulers.mainThread())
                .map(chatListResponse -> mapper.map(chatListResponse.getChatRemotes()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Chat> getSingleChat(int serverId, String attachment) {
        ChatSingleMapper mapper = new ChatSingleMapper(attachment);
        return mChatApi.getSingleChat(serverId)
                .subscribeOn(Schedulers.io())
                .map(chatSingleResponse -> mapper.map(chatSingleResponse.getChatRemote()));
    }

    private static class ChatListMapper implements Mapper<ChatRemote[], List<Chat>> {

        private List<Chat> mChats;
        private String mAttachment;

        ChatListMapper(String attachment) {
            mAttachment = attachment;
            mChats = new ArrayList<>();
        }

        @Override
        public List<Chat> map(ChatRemote[] remoteChatRemotes) {
            for (ChatRemote remoteChatRemote : remoteChatRemotes) {
                Chat chat = new Chat.Builder()
                        .withChatId(remoteChatRemote.getChatId())
                        .withChatAttachment(mAttachment)
                        .withActive(remoteChatRemote.getActive())
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

    private static class ChatSingleMapper implements Mapper<ChatRemote, Chat> {

        private String mAttachment;

        ChatSingleMapper(String attachment) {
            mAttachment = attachment;
        }

        @Override
        public Chat map(ChatRemote chatRemote) {
            return new Chat.Builder()
                    .withChatId(chatRemote.getChatId())
                    .withChatAttachment(mAttachment)
                    .withActive(chatRemote.getActive())
                    .withName(chatRemote.getRecipient().getName())
                    .withPhotoUrl(chatRemote.getRecipient().getPhoto())
                    .withLastMessage(chatRemote.getLastMessage().getMessage())
                    .withLastMessageTime(chatRemote.getLastMessage().getDateCreated())
                    .withUnreadMessageCount(chatRemote.getNewCount())
                    .withCarTitle(chatRemote.getCarVersion().getCarTitle())
                    .withCarPhoto(chatRemote.getCarVersion().getImageUrl())
                    .withLicenseNumber(chatRemote.getCarVersion().getCarNumber())
                    .withBookingBegin(chatRemote.getBooking().getTimeBegin())
                    .withBookingEnd(chatRemote.getBooking().getTimeEnd())
                    .build();
        }
    }
}
