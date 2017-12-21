package com.cardee.inbox;

public class InboxPresenterImp implements InboxContract.Presenter {

    private InboxContract.View mIboxView;

    @Override
    public void init(InboxContract.View view) {
        mIboxView = view;
    }

    @Override
    public void onDestroy() {
        mIboxView = null;
    }
}
