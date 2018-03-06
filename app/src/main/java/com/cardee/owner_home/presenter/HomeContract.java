package com.cardee.owner_home.presenter;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

public interface HomeContract {

    interface Presenter {

        void init(HomeContract.View view, AHBottomNavigation bottomView);

        void onSubscribeToNotifications();

        void onDestroy();
    }

    interface View {

    }

}
