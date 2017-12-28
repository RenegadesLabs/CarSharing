package com.cardee.inbox.chat.single.presenter;

import android.os.Bundle;
import android.view.View;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.inbox.chat.single.view.ActivityViewHolder;
import com.cardee.inbox.chat.single.view.ChatViewHolder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class ChatPresenter implements ChatContract.Presenter {

    private static final int NEW_CHAT = 0;
    private final ChatRepository mRepository;

    private ChatContract.View mView;
    private ActivityViewHolder mViewHolder;
    private CompositeDisposable mCompositeDisposable;

    private int mChatDatabaseId;
    private int mChatServerId;
    private String mAttachment;

    public ChatPresenter(ChatContract.View view) {
        mView = view;
        mRepository = ChatRepository.getInstance();
    }

    @Override
    public void init(Bundle bundle, View activityView) {
        mChatDatabaseId = bundle.getInt(Chat.CHAT_DB_ID, 0);
        mChatServerId = bundle.getInt(Chat.CHAT_SERVER_ID);
        mAttachment = bundle.getString(Chat.CHAT_ATTACHMENT, "");
        mViewHolder = new ChatViewHolder(activityView);
    }

    @Override
    public void onChatDataRequest() {
        switch (mChatDatabaseId) {
            case NEW_CHAT:
                getRemoteChatData();
                break;
            default:
                getLocalChatData();
        }
    }

    private void getLocalChatData() {
        mRepository.sendChatIdentifier(mChatServerId, mChatDatabaseId, mAttachment);
        mRepository.getChatInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chatInfo -> {
                    mViewHolder.setUserData(chatInfo.getRecipientName(), chatInfo.getRecipientPhotoUrl());
                    mViewHolder.setCarData(chatInfo.getCarPhotoUrl(), chatInfo.getCarTitle(), chatInfo.getCarLicenseNumber());
                    mViewHolder.setCarBookingData(chatInfo.getBookingTimeBegin(), chatInfo.getBookingTimeEnd());
                });
        mRepository.getLocalMessages()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(messageList -> {
                    if (messageList.isEmpty()) getRemoteChatData();
                })
                .subscribe(this::showAllMessages);
    }

    private void getRemoteChatData() {
        mRepository.getRemoteMessages()
                .doOnSubscribe(disposable -> {
                    showProgress(true);
                })
                .doOnEvent(throwable -> showProgress(false))
                .subscribe();
    }

    private void showProgress(boolean isShown) {
        if (mView != null) {
            mView.showProgress(isShown);
        }
    }

    private void showAllMessages(List<ChatMessage> messageList) {
        if (mView != null) {
            mView.setMessageList(messageList);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
