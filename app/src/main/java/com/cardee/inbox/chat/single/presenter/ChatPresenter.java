package com.cardee.inbox.chat.single.presenter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.inbox.chat.single.view.ActivityViewHolder;
import com.cardee.inbox.chat.single.view.ChatViewHolder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ChatPresenter implements ChatContract.Presenter {

    private static final int NEW_CHAT = 0;
    private static final String TAG = ChatPresenter.class.getSimpleName();
    private final ChatRepository mRepository;

    private ChatContract.View mView;
    private ActivityViewHolder mViewHolder;
    private Disposable mDisposable;

    private int mChatDatabaseId;
    private int mChatServerId;
    private String mAttachment;
    private boolean isFistChatEntering = true;

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
                //TODO: handling new chat
                break;
            default:
                getLocalChatData();
                getLocalMessageList();
                getRemoteChatList();
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
    }

    private void getLocalMessageList() {
        mRepository.getLocalMessages()
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .filter(messageList -> !messageList.isEmpty())
                .subscribe(this::proceedResponse);
    }

    private void getRemoteChatList() {
        mDisposable = mRepository.getRemoteMessages()
                .doOnSubscribe(disposable -> showProgress(true))
                .doOnEvent((messageList, throwable) -> showProgress(false))
                .doAfterSuccess(this::checkReadStatus)
                .subscribe();
    }

    private void checkReadStatus(List<ChatMessage> messageList) {
        int lastMessagePosition = messageList.size() - 1;
        if (isLastMessageDidNotRead(messageList)) {
            mRepository.markAsRead(messageList.get(lastMessagePosition).getMessageId())
                    .subscribe(
                            this::getRemoteChatList,
                            throwable -> Log.e(TAG, throwable.getMessage()));
        }
    }

    private boolean isLastMessageDidNotRead(List<ChatMessage> messageList) {
        return !messageList.get(messageList.size() - 1).getIsRead();
    }

    private void proceedResponse(List<ChatMessage> messageList) {
        if (isFistChatEntering) {
            showAllMessages(messageList);
            isFistChatEntering = false;
        } else {
            updateAllMessages(messageList);
        }
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

    private void updateAllMessages(List<ChatMessage> messageList) {
        if (mView != null) {
            mView.updateAllMessages(messageList);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
