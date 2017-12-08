package com.cardee.inbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;

public class InboxFragment extends Fragment implements InboxContract.View {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private InboxPageAdapter mPageAdapter;
    private TabItem mAlertsTab;
    private TabItem mChatsTab;

    public static InboxFragment newInstance() {
        Bundle args = new Bundle();
        InboxFragment fragment = new InboxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        mPageAdapter = new InboxPageAdapter(getChildFragmentManager());
        mViewPager = rootView.findViewById(R.id.inbox_fragment_viewpager);
        mViewPager.setAdapter(mPageAdapter);

        mAlertsTab = rootView.findViewById(R.id.inbox_fragment_tab_alerts);
        mChatsTab = rootView.findViewById(R.id.inbox_fragment_tab_chats);

        mTabLayout = rootView.findViewById(R.id.inbox_fragment_tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getCustomView() != null) {
                    tab.getCustomView().setAlpha(1.f);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    tab.getCustomView().setAlpha(0.4f);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }
}
