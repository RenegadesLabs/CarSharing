package com.cardee.owner_home.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.CardeeApp;
import com.cardee.owner_home.view.fragment.inbox.AlertsFragment;
import com.cardee.owner_home.view.fragment.inbox.ChatsFragment;

public class InboxPageAdapter extends FragmentPagerAdapter {

    private static final int ALERTS_FRAGMENT = 0;
    private static final int CHATS_FRAGMENT = 1;

    private AlertsFragment mAlertsFragment;
    private ChatsFragment mChatsFragment;

    public InboxPageAdapter(FragmentManager fm) {
        super(fm);
        mAlertsFragment = AlertsFragment.newInstance();
        mChatsFragment = ChatsFragment.newInstance();
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
