package com.cardee.data_source.inbox.local.chat;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.db.LocalInboxDatabase;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;
import com.cardee.domain.util.Mapper;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ChatItemDataSource implements LocalData.ChatSingleSource {

    private final LocalInboxDatabase mDataBase;

    public ChatItemDataSource() {
        mDataBase = LocalInboxDatabase.getInstance(CardeeApp.context);
    }

    @Override
    public Single<ChatInfo> getChatInfo(Integer databaseId, Integer serverId) {
        ToChatInfoMapper chatInfoMapper = new ToChatInfoMapper();
        return mDataBase.getChatDao()
                .getChatInfo(databaseId, serverId)
                .subscribeOn(Schedulers.io())
                .flatMap(chat -> Single.just(chatInfoMapper.map(chat)));

    }

    private static class ToChatInfoMapper implements Mapper<Chat, ChatInfo> {

        @Override
        public ChatInfo map(Chat localChat) {
            return new ChatInfo.Builder()
                    .withRecipientName(localChat.getRecipientName())
                    .withRecipientPhoto(localChat.getPhotoUrl())
                    .withCarTitle(localChat.getCarTitle())
                    .withLicenseNumber(localChat.getCarLicenseNumber())
                    .withCarPhoto(localChat.getCarPhotoUrl())
                    .withBookingBegin(localChat.getBookingTimeBegin())
                    .withBookingEnd(localChat.getBookingTimeEnd())
                    .build();
        }
    }
}
