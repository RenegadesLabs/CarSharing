package com.cardee.data_source.inbox.repository;

import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.remote.api.NotificationApi;
import com.cardee.data_source.inbox.remote.api.model.entity.NotificationData;
import com.cardee.data_source.inbox.remote.api.response.NotificationResponse;
import com.cardee.data_source.remote.service.AccountManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class NotificationRepository implements NotificationContract {

    private String TAG = "Cardee " + NotificationRepository.class.getName();
    private static NotificationRepository INSTANCE;

    private BehaviorSubject<Integer> mInboxSubject;
    private BehaviorSubject<Boolean> mAlertSubject;
    private BehaviorSubject<Boolean> mChatSubject;

    private AccountManager mAccountManager;
    private NotificationApi mNotificationApi;
    private NotificationData mNotificationData;

    private Integer mCurrentChatId;

    public static NotificationRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationRepository();
        }
        return INSTANCE;
    }

    private NotificationRepository() {
        initSessionData();
        mNotificationApi = CardeeApp.retrofit.create(NotificationApi.class);
        mInboxSubject = BehaviorSubject.createDefault(mNotificationData.getTotalNotifications());
        mAlertSubject = BehaviorSubject.createDefault(mNotificationData.isMissingAlertsExist());
        mChatSubject = BehaviorSubject.createDefault(mNotificationData.isMissingChatsExist());
    }

    private void initSessionData() {
        mAccountManager = AccountManager.getInstance(CardeeApp.context);
        mNotificationData = mAccountManager.getNotificationData();
    }

    @Override
    public void fetchNotifications() {
        mNotificationApi.getNotificationCount()
                .subscribeOn(Schedulers.io())
                .filter(notificationResponse -> notificationResponse != null && notificationResponse.isSuccessful())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::proceedResponse, throwable -> Log.d(TAG, "Connection error while obtaining notification data"));
    }

    private void proceedResponse(NotificationResponse notificationResponse) {
        NotificationData data = notificationResponse.getNotificationData();
        if (data != null) {
            mNotificationData.setOwnerAlertMessages(data.getOwnerAlertMessages());
            mNotificationData.setOwnerChatMessages(data.getOwnerChatMessages());
            mNotificationData.setRenterAlertMessages(data.getRenterAlertMessages());
            mNotificationData.setRenterChatMessages(data.getRenterChatMessages());
        }
        saveSessionData();
        publishAllData();
    }

    private void publishAllData() {
        mInboxSubject.onNext(mNotificationData.getTotalNotifications());
        mAlertSubject.onNext(mNotificationData.isMissingAlertsExist());
        mChatSubject.onNext(mNotificationData.isMissingChatsExist());
    }

    @Override
    public void setRelevantChatUnreadCount(Integer readCount) {
        mNotificationData.setRelevantChatCount(readCount);
        mInboxSubject.onNext(mNotificationData.getTotalNotifications());
        mChatSubject.onNext(mNotificationData.isMissingChatsExist());
    }

    @Override
    public void setRelevantAlertUnreadCount(Integer readCount) {
        mNotificationData.setRelevantAlertCount(readCount);
        mInboxSubject.onNext(mNotificationData.getTotalNotifications());
        mAlertSubject.onNext(mNotificationData.isMissingAlertsExist());
    }

    @Override
    public void updateChatUnreadCount(Integer count) {
        mNotificationData.updateChatUnreadCount(count);
        publishAllData();
    }

    @Override
    public void updateInboxUnreadCount() {
        mNotificationData.updateAlertUnreadCount();
        publishAllData();
    }

    @Override
    public boolean isCurrentSessionNeedToNotify(String attachment) {
        return mNotificationData.getCurrentAttachment().equals(attachment);
    }

    @Override
    public void subscribeToNotificationUpdates(Consumer<Integer> consumer) {
        mInboxSubject
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    @Override
    public void subscribeToAlertUpdates(Consumer<Boolean> consumer) {
        mAlertSubject
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    @Override
    public void subscribeToChatUpdates(Consumer<Boolean> consumer) {
        mChatSubject
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    @Override
    public void setCurrentChatSession(int chatId) {
        mCurrentChatId = chatId;
    }

    @Override
    public void resetCurrentChatSession() {
        mCurrentChatId = null;
    }

    @Override
    public boolean isCurrentMessagingSession(int chatId) {
        return mCurrentChatId != null && mCurrentChatId == chatId;
    }

    @Override
    public void saveSessionData() {
        mAccountManager.saveNotificationData(mNotificationData);
    }
}
