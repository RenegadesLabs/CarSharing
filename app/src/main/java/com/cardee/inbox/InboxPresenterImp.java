package com.cardee.inbox;

import static com.cardee.inbox.InboxPageAdapter.ALERTS_FRAGMENT;
import static com.cardee.inbox.InboxPageAdapter.CHATS_FRAGMENT;

public class InboxPresenterImp implements InboxContract.Presenter {

    private InboxContract.View mAlertView;
    private InboxContract.View mChatView;

    @Override
    public void init(InboxContract.View view, int position) {
        switch (position) {
            case ALERTS_FRAGMENT:
                mAlertView = view;
                break;
            case CHATS_FRAGMENT:
                mChatView = view;
                break;
        }
    }

    @Override
    public void onDestroy() {
        mAlertView = null;
        mChatView = null;
    }
}
