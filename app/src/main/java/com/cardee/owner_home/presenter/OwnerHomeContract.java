package com.cardee.owner_home.presenter;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

public interface OwnerHomeContract {

    interface Presenter {

        void init(OwnerHomeContract.View view, AHBottomNavigation bottomView);

    }

    interface View {

    }

}
