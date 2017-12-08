package com.cardee.inbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cardee.inbox.alert.AlertFragment;
import com.cardee.inbox.chat.ChatFragment;

public class InboxPageAdapter extends FragmentPagerAdapter {

    private static final int ALERTS_FRAGMENT = 0;
    private static final int CHATS_FRAGMENT = 1;

    private AlertFragment mAlertsFragment;
    private ChatFragment mChatsFragment;

    public InboxPageAdapter(FragmentManager fm) {
        super(fm);
        mAlertsFragment = AlertFragment.newInstance();
        mChatsFragment = ChatFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case ALERTS_FRAGMENT:
                fragment = mAlertsFragment;
                break;
            case CHATS_FRAGMENT:
                fragment = mChatsFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
