package com.cardee.owner_home.presenter;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

public class OwnerHomePresenterImp implements OwnerHomeContract.Presenter {

    private OwnerHomeContract.View mView;
    private AHBottomNavigation mBottomView;

    @Override
    public void init(OwnerHomeContract.View view, AHBottomNavigation bottomView) {
        mView =view;
        mBottomView = bottomView;
    }
}
