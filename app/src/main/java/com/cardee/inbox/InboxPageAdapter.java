package com.cardee.inbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cardee.inbox.alert.list.view.AlertListFragment;
import com.cardee.inbox.chat.list.view.ChatListFragment;

public class InboxPageAdapter extends FragmentPagerAdapter {

    private static final int ALERTS_FRAGMENT = 0;
    private static final int CHATS_FRAGMENT = 1;

    private AlertListFragment mAlertsFragment;
    private ChatListFragment mChatsFragment;

    InboxPageAdapter(FragmentManager fm) {
        super(fm);
        mAlertsFragment = AlertListFragment.newInstance();
        mChatsFragment = ChatListFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ALERTS_FRAGMENT:
                return mAlertsFragment;
            case CHATS_FRAGMENT:
                return mChatsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
