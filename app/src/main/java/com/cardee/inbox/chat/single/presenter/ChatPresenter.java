package com.cardee.inbox.chat.single.presenter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.data_source.inbox.repository.NotificationRepository;
import com.cardee.inbox.chat.single.view.ActivityViewHolder;
import com.cardee.inbox.chat.single.view.ChatViewHolder;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ChatPresenter implements ChatContract.Presenter {

    private static final String TAG = ChatPresenter.class.getSimpleName();

    private NotificationRepository mNotificationRepository;
    private ChatRepository mChatRepository;

    private ChatContract.View mView;
    private ActivityViewHolder mViewHolder;
    private Disposable mDisposable;

    private int chatId;
    private int chatUnreadCount;
    private String attachment;

    public ChatPresenter(ChatContract.View view) {
        mView = view;
        mChatRepository = ChatRepository.getInstance();
        mNotificationRepository = NotificationRepository.getInstance();
    }

    @Override
    public void init(Bundle bundle, View activityView) {
        mViewHolder = new ChatViewHolder(activityView);
        mViewHolder.initAdapter(activityView.getContext());
        subscribeToUserInput();

        chatId = bundle.getInt(Chat.CHAT_SERVER_ID);
        attachment = bundle.getString(Chat.CHAT_ATTACHMENT, "");
        chatUnreadCount = bundle.getInt(Chat.CHAT_UNREAD_COUNT);
    }

    private void subscribeToUserInput() {
        mViewHolder.subscribeToInput(s -> mChatRepository.sendMessage(s).
                subscribe(realMessageId -> mViewHolder.updateMessagePreview(realMessageId), throwable -> Log.e(TAG, "Connection error")));
    }

    @Override
    public void onChatDataRequest() {
        mNotificationRepository.setCurrentChatSession(chatId);
        getLocalChatData();
        getLocalMessageList();
        getRemoteChatList();
    }

    private void getLocalChatData() {
        mChatRepository.sendChatIdentifier(chatId, attachment);
        mChatRepository.getChatInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chatInfo -> {
                    mViewHolder.setUserData(chatInfo.getRecipientName(), chatInfo.getRecipientPhotoUrl());
                    mViewHolder.setCarData(chatInfo.getCarPhotoUrl(), chatInfo.getCarTitle(), chatInfo.getCarLicenseNumber());
                    mViewHolder.setCarBookingData(chatInfo.getBookingTimeBegin(), chatInfo.getBookingTimeEnd());
                });
    }

    private void getLocalMessageList() {
        mChatRepository.getLocalMessages()
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .filter(messageList -> messageList != null && !messageList.isEmpty())
                .subscribe(this::showAllMessages);
    }

    private void getRemoteChatList() {
        mDisposable = mChatRepository.getRemoteMessages()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateChatUnreadMarkerIfNeeded
                        , throwable -> Log.e(TAG, "Connection error"));

    }

    private void updateChatUnreadMarkerIfNeeded() {
        Log.d(TAG, "Unread count = " + chatUnreadCount);
        if (chatUnreadCount != 0) {
            mNotificationRepository.updateChatUnreadCount(chatUnreadCount);
            mChatRepository.removeChatUnreadStatus(chatId);
            chatUnreadCount = 0;
        }
    }

    private void showProgress(boolean isShown) {
        if (mView != null) {
            mViewHolder.showProgress(isShown);
        }
    }

    private synchronized void showAllMessages(List<ChatMessage> messageList) {
        if (mViewHolder != null) {
            mViewHolder.setMessageList(messageList);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        mViewHolder = null;
        mNotificationRepository.resetCurrentChatSession();
        mNotificationRepository = null;
        mChatRepository = null;

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
