package com.cardee.inbox.chat.single.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.data_source.inbox.repository.InboxRepository;
import com.cardee.data_source.inbox.repository.NotificationRepository;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.bookings.entity.Booking;
import com.cardee.domain.bookings.usecase.GetBooking;
import com.cardee.domain.inbox.usecase.entity.ChatInfo;
import com.cardee.inbox.chat.single.view.ActivityViewHolder;
import com.cardee.inbox.chat.single.view.ChatViewHolder;
import com.cardee.util.DateRepresentationDelegate;

import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ChatPresenter implements ChatContract.Presenter {

    private static final String TAG = ChatPresenter.class.getSimpleName();

    private NotificationRepository mNotificationRepository;
    private ChatRepository mChatRepository;
    private InboxRepository mInboxRepository;

    private ChatContract.View mView;
    private ActivityViewHolder mViewHolder;
    private Disposable mDisposable;

    private final UseCaseExecutor executor;
    private final GetBooking getBooking;
    private DateRepresentationDelegate dateDelegate;

    private int chatId;
    private int chatUnreadCount;
    private String attachment;
    private boolean chatFromBooking;
    private boolean chatNew;
    private Booking mBooking;

    public ChatPresenter(ChatContract.View view) {
        mView = view;
        mChatRepository = ChatRepository.getInstance();
        mInboxRepository = InboxRepository.getInstance();
        mNotificationRepository = NotificationRepository.getInstance();
        dateDelegate = new DateRepresentationDelegate((Context) mView);
        executor = UseCaseExecutor.getInstance();
        getBooking = new GetBooking();
    }

    @Override
    public void init(Bundle bundle, View activityView) {
        chatNew = false;
        mViewHolder = new ChatViewHolder(activityView, this);
        mViewHolder.initAdapter(activityView.getContext());
        subscribeToUserInput();

        chatId = bundle.getInt(Chat.CHAT_SERVER_ID, -1);
        attachment = bundle.getString(Chat.CHAT_ATTACHMENT, "");
        chatUnreadCount = bundle.getInt(Chat.CHAT_UNREAD_COUNT, 0);
        chatFromBooking = bundle.getBoolean(Chat.CHAT_FROM_BOOKING, false);
    }

    private void subscribeToUserInput() {
        mViewHolder.subscribeToInput(s -> mChatRepository
                .sendMessage(s)
                .subscribe(realMessageId -> {
                            mViewHolder.updateMessagePreview(realMessageId);

                            if (chatFromBooking && chatNew) {
                                Chat newChat = new Chat();
                                newChat.setChatId(chatId);
                                newChat.setChatAttachment(attachment);
                                newChat.setActive(true);
                                newChat.setUnreadMessageCount(0);
                                if (attachment.equals(AccountManager.RENTER_SESSION)) {
                                    newChat.setRecipientId(mBooking.getOwnerId());
                                    newChat.setRecipientName(mBooking.getOwnerName());
                                    newChat.setPhotoUrl(mBooking.getOwnerPhoto());
                                } else {
                                    newChat.setRecipientId(mBooking.getRenterId());
                                    newChat.setRecipientName(mBooking.getRenterName());
                                    newChat.setPhotoUrl(mBooking.getRenterPhoto());
                                }
                                newChat.setCarPhotoUrl(mBooking.getPrimaryImage().getLink());
                                newChat.setLastMessageText(s);
                                newChat.setLastMessageTime(dateDelegate.formatAsIsoDate(new Date()));
                                newChat.setCarTitle(mBooking.getCarTitle());
                                newChat.setCarLicenseNumber(mBooking.getPlateNumber());
                                newChat.setBookingTimeEnd(dateDelegate.formatAsIsoBooking(mBooking.getTimeEnd()));
                                newChat.setBookingTimeBegin(dateDelegate.formatAsIsoBooking(mBooking.getTimeBegin()));

                                mInboxRepository.addNewChat(newChat);
                            }

                            mChatRepository.saveMessageLocally();
                        }, throwable -> Log.e(TAG, "Connection error")

                ));
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

        if (chatFromBooking) {
            getDataFromBooking();
        } else {
            mChatRepository.getChatInfo()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((ChatInfo chatInfo) -> {
                        mViewHolder.setUserData(chatInfo.getRecipientName(), chatInfo.getRecipientPhotoUrl(), chatInfo.getRecipientId());
                        mViewHolder.setCarData(chatInfo.getCarPhotoUrl(), chatInfo.getCarTitle(), chatInfo.getCarLicenseNumber());
                        mViewHolder.setCarBookingData(chatInfo.getBookingTimeBegin(), chatInfo.getBookingTimeEnd());
                    });
        }
    }

    private void getDataFromBooking() {
        GetBooking.RequestValues request = new GetBooking.RequestValues(chatId);
        executor.execute(getBooking, request, new UseCase.Callback<GetBooking.ResponseValues>() {
            @Override
            public void onSuccess(GetBooking.ResponseValues response) {
                Booking booking = response.getBooking();
                mBooking = booking;
                setChatData(booking);
            }

            @Override
            public void onError(Error error) {
                mView.showMessage(error.getMessage());
            }
        });
    }

    private void setChatData(Booking booking) {
        if (attachment.equals(AccountManager.RENTER_SESSION)) {
            mViewHolder.setUserData(booking.getOwnerName(), booking.getOwnerPhoto(), booking.getOwnerId());
        } else {
            mViewHolder.setUserData(booking.getRenterName(), booking.getRenterPhoto(), booking.getRenterId());
        }
        mViewHolder.setCarData(booking.getPrimaryImage().getLink(), booking.getCarTitle(), booking.getPlateNumber());
        mViewHolder.setFormattedCarBookingData(booking.getTimeBegin(), booking.getTimeEnd());
    }

    private void getLocalMessageList() {
        mChatRepository.getLocalMessages()
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
//                .filter(messageList -> messageList != null && !messageList.isEmpty())
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

    private void showAllMessages(List<ChatMessage> messageList) {
        if (messageList == null || messageList.isEmpty()) {
            chatNew = true;
            return;
        }
        if (mViewHolder != null) {
            mViewHolder.setMessageList(messageList);
        }
    }

    @Override
    public void setOnClickListener(ImageView recipientPhoto, Integer recipientId) {
        recipientPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentSession = AccountManager.getInstance(CardeeApp.context).getSessionInfo();
                if (currentSession.equals(AccountManager.RENTER_SESSION)) {
                    mView.openOwnerAccount(recipientId);
                } else if (currentSession.equals(AccountManager.OWNER_SESSION)) {
                    mView.openRenterAccount(recipientId);
                }
            }
        });
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
