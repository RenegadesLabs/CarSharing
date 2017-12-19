package com.cardee.inbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cardee.inbox.alert.AlertFragment;
import com.cardee.inbox.chat.ChatFragment;

public class InboxPageAdapter extends FragmentPagerAdapter {

    public static final int ALERTS_FRAGMENT = 0;
    public static final int CHATS_FRAGMENT = 1;

    private AlertFragment mAlertsFragment;
    private ChatFragment mChatsFragment;

    InboxPageAdapter(FragmentManager fm) {
        super(fm);
        mAlertsFragment = AlertFragment.newInstance();
        mChatsFragment = ChatFragment.newInstance();
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
