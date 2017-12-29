package com.cardee.inbox.chat.single.presenter;

import android.os.Bundle;
import android.view.View;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.data_source.inbox.repository.NotificationRepository;
import com.cardee.inbox.chat.single.view.ActivityViewHolder;
import com.cardee.inbox.chat.single.view.ChatViewHolder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ChatPresenter implements ChatContract.Presenter {

    private static final String TAG = ChatPresenter.class.getSimpleName();

    private final NotificationRepository mNotificationRepository;
    private final ChatRepository mRepository;

    private ChatContract.View mView;
    private ActivityViewHolder mViewHolder;
    private Disposable mDisposable;

    private int mChatServerId;
    private int mChatUnreadCount;
    private String mAttachment;
    private boolean isFistChatEntering = true;

    public ChatPresenter(ChatContract.View view) {
        mView = view;
        mRepository = ChatRepository.getInstance();
        mNotificationRepository = NotificationRepository.getInstance();
    }

    @Override
    public void init(Bundle bundle, View activityView) {
        mChatServerId = bundle.getInt(Chat.CHAT_SERVER_ID);
        mAttachment = bundle.getString(Chat.CHAT_ATTACHMENT, "");
        mChatUnreadCount = bundle.getInt(Chat.CHAT_UNREAD_COUNT);
        mViewHolder = new ChatViewHolder(activityView);
    }

    @Override
    public void onChatDataRequest() {
        getLocalChatData();
        getLocalMessageList();
        getRemoteChatList();
    }

    private void getLocalChatData() {
        mRepository.sendChatIdentifier(mChatServerId, mAttachment);
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
                .distinct()
                .filter(messageList -> !messageList.isEmpty())
                .subscribe(this::proceedResponse);
    }

    private void getRemoteChatList() {
        mDisposable = mRepository.getRemoteMessages()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> showProgress(true))
                .doOnComplete(() -> showProgress(false))
                .subscribe(() -> {
                            if (mChatUnreadCount != 0) {
                                mNotificationRepository.updateChatNotificationCount(mChatUnreadCount);
                            }
                        }
                        , throwable -> showProgress(false));

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
