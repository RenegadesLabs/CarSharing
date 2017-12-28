package com.cardee.inbox.chat.single.presenter;

import android.os.Bundle;
import android.view.View;

import com.cardee.data_source.Error;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.inbox.usecase.chat.GetChatInfo;
import com.cardee.domain.inbox.usecase.chat.GetChatMessages;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;
import com.cardee.inbox.chat.single.view.ActivityViewHolder;
import com.cardee.inbox.chat.single.view.ChatViewHolder;

import java.util.List;

public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View mView;
    private ActivityViewHolder mViewHolder;
    private final GetChatInfo mGetInfoUseCase;
    private final GetChatMessages mGetChatMessages;
    private final UseCaseExecutor mExecutor;

    private int mChatDatabaseId;
    private int mChatServerId;
    private String mAttachment;

    public ChatPresenter(ChatContract.View view) {
        mView = view;
        mGetInfoUseCase = new GetChatInfo();
        mGetChatMessages = new GetChatMessages();
        mExecutor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init(Bundle bundle, View activityView) {
        mChatDatabaseId = bundle.getInt(Chat.CHAT_DB_ID);
        mChatServerId = bundle.getInt(Chat.CHAT_SERVER_ID);
        mAttachment = bundle.getString(Chat.CHAT_ATTACHMENT, "");
        mViewHolder = new ChatViewHolder(activityView);
    }

    @Override
    public void onChatDataRequest() {
        mExecutor.execute(mGetInfoUseCase,
                new GetChatInfo.RequestValues(mChatDatabaseId, mChatServerId, mAttachment),
                new UseCase.Callback<GetChatInfo.ResponseValues>() {
                    @Override
                    public void onSuccess(GetChatInfo.ResponseValues response) {
                        mView.notifyAboutInboxDataObtained();
                        ChatInfo chatInfo = response.getChatInfo();
                        mViewHolder.setUserData(chatInfo.getRecipientName(), chatInfo.getRecipientPhotoUrl());
                        mViewHolder.setCarData(chatInfo.getCarPhotoUrl(), chatInfo.getCarTitle(), chatInfo.getCarLicenseNumber());
                        mViewHolder.setCarBookingData(chatInfo.getBookingTimeBegin(), chatInfo.getBookingTimeEnd());
                    }

                    @Override
                    public void onError(Error error) {
                        //TODO: implement error handling;
                    }
                });
    }

    @Override
    public void onGetMessagesRequest() {
        mExecutor.execute(mGetChatMessages,
                new GetChatMessages.RequestValues(),
                new UseCase.Callback<GetChatMessages.ResponseValues>() {
                    @Override
                    public void onSuccess(GetChatMessages.ResponseValues response) {
                        showAllMessages(response.getMessageList());
                    }

                    @Override
                    public void onError(Error error) {
                        //TODO: implement error handling;
                    }
                });
    }

    private void showAllMessages(List<ChatMessage> messageList) {
        if (mView != null) {
            mView.setMessageList(messageList);
        }
    }

    @Override
    public void onDestroy() {
        mGetChatMessages.dispose();
        mView = null;
    }
}
