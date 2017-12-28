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
import android.widget.ImageView;

import com.cardee.R;

public class InboxFragment extends Fragment implements InboxContract.View {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private InboxPageAdapter mPageAdapter;
    private ImageView mAlertDot;
    private ImageView mChatDot;

    private InboxContract.Presenter mInboxPresenterImp;

    public static InboxFragment newInstance() {
        Bundle args = new Bundle();
        InboxFragment fragment = new InboxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageAdapter = new InboxPageAdapter(getChildFragmentManager());
        mInboxPresenterImp = new InboxPresenterImp();
        mInboxPresenterImp.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        mViewPager = rootView.findViewById(R.id.inbox_fragment_viewpager);
        mViewPager.setAdapter(mPageAdapter);

        mTabLayout = rootView.findViewById(R.id.inbox_fragment_tabs);

        mAlertDot = mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_notification);
        mChatDot = mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tab_notification);

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
    public void setAlertTabState(boolean isUnread) {
        mAlertDot.setVisibility(isUnread ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setChatTabState(boolean isUnread) {
        mChatDot.setVisibility(isUnread ? View.VISIBLE : View.INVISIBLE);
    }
}
