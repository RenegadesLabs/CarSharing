package com.cardee.owner_home.presenter;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.cardee.CardeeApp;
import com.cardee.data_source.inbox.repository.NotificationRepository;
import com.cardee.data_source.remote.service.AccountManager;

import io.reactivex.functions.Consumer;

public class OwnerHomePresenterImp implements OwnerHomeContract.Presenter {

    private final NotificationRepository mRepository;

    private OwnerHomeContract.View mView;
    private AHBottomNavigation mBottomView;
    private int barPosition;

    public OwnerHomePresenterImp() {
        mRepository = NotificationRepository.getInstance();
    }

    @Override
    public void init(OwnerHomeContract.View view, AHBottomNavigation bottomView) {
        barPosition = AccountManager.getInstance(CardeeApp.context).getSessionInfo().equals(AccountManager.OWNER_SESSION) ? 0 : 2;
        mView = view;
        mBottomView = bottomView;
    }

    @Override
    public void onSubscribeToNotifications() {
        mRepository.fetchNotifications();
        mRepository.subscribe(integer -> mBottomView.setNotification(String.valueOf(integer), barPosition));
    }

    @Override
    public void onDestroy() {
        mRepository.invalidateSession();
    }
}
