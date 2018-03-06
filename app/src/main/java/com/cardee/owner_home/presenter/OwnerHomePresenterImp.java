package com.cardee.owner_home.presenter;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.repository.NotificationRepository;
import com.cardee.data_source.remote.service.AccountManager;

public class OwnerHomePresenterImp implements HomeContract.Presenter {

    private final NotificationRepository mRepository;

    private HomeContract.View mView;
    private AHBottomNavigation mBottomView;
    private int barPosition;

    public OwnerHomePresenterImp() {
        mRepository = NotificationRepository.getInstance();
    }

    @Override
    public void init(HomeContract.View view, AHBottomNavigation bottomView) {
        barPosition = AccountManager.getInstance(CardeeApp.context).getSessionInfo().equals(AccountManager.OWNER_SESSION) ? 0 : 2;
        mView = view;
        mBottomView = bottomView;
    }

    @Override
    public void onSubscribeToNotifications() {
        mRepository.fetchNotifications();
        mRepository.subscribeToNotificationUpdates(inboxCount -> mBottomView.setNotification(inboxCount == 0 ? "" : String.valueOf(inboxCount), barPosition));
    }

    @Override
    public void onDestroy() {
        mRepository.saveSessionData();
        mView = null;
    }
}
