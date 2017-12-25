package com.cardee.inbox.chat.item.presenter;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.cardee.data_source.Error;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.inbox.usecase.chat.GetChatInfo;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;
import com.cardee.inbox.chat.item.view.ActivityViewHolder;
import com.cardee.inbox.chat.item.view.ChatViewHolder;

public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View mView;
    private ActivityViewHolder mViewHolder;
    private final GetChatInfo mGetInfoUseCase;
    private final UseCaseExecutor mExecutor;

    private int mChatDatabaseId;
    private int mChatServerId;

    public ChatPresenter(ChatContract.View view) {
        mView = view;
        mGetInfoUseCase = new GetChatInfo();
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init(Bundle bundle, View activityView) {
        mChatDatabaseId = bundle.getInt(Chat.CHAT_DB_ID);
        mChatServerId = bundle.getInt(Chat.CHAT_SERVER_ID);
        mViewHolder = new ChatViewHolder(activityView);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onChatDataRequest() {
        mExecutor.execute(mGetInfoUseCase, new GetChatInfo.RequestValues(mChatDatabaseId, mChatServerId), new UseCase.Callback<GetChatInfo.ResponseValues>() {
            @Override
            public void onSuccess(GetChatInfo.ResponseValues response) {
                ChatInfo chatInfo = response.getChatInfo();
                mViewHolder.setUserData(chatInfo.getRecipientName(), chatInfo.getRecipientPhotoUrl());
                mViewHolder.setCarData(chatInfo.getCarPhotoUrl(), chatInfo.getCarTitle(), chatInfo.getCarLicenseNumber());
                mViewHolder.setCarBookingData(chatInfo.getBookingTimeBegin(), chatInfo.getBookingTimeEnd());
            }

            @Override
            public void onError(Error error) {

            }
        });
    }
}
