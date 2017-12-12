package com.cardee.owner_home.view.helper;

import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.cardee.R;

public class BottomNavigationHelper {

    private static final String TAG = BottomNavigationHelper.class.getSimpleName();

    public static AHBottomNavigation prepareForOwner(AHBottomNavigation navView) {
        if (navView == null) {
            Log.e(TAG, "Bottom navigation instance is null");
            return null;
        }
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.menu_item_inbox_title, R.drawable.ic_temp_inbox, 0);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.menu_item_cars_title, R.drawable.ic_temp_cars, 0);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.menu_item_bookings_title, R.drawable.ic_temp_bookings, 0);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.menu_item_more_title, R.drawable.ic_temp_more_h, 0);

        navView.addItem(item1);
        navView.addItem(item2);
        navView.addItem(item3);
        navView.addItem(item4);

        navView.setBehaviorTranslationEnabled(false);
        navView.setDefaultBackgroundResource(R.color.bg_widget);
        navView.setAccentColor(ContextCompat.getColor(navView.getContext(), R.color.main_enabled));
        navView.setInactiveColor(ContextCompat.getColor(navView.getContext(), R.color.text_disabled));

        navView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        return navView;
    }

    public static AHBottomNavigation prepareForRenter(AHBottomNavigation navView) {
        if (navView == null) {
            Log.e(TAG, "Bottom navigation instance is null");
            return null;
        }
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.menu_item_browse_title, R.drawable.ic_question_mark, 0);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.menu_item_bookings_title, R.drawable.ic_temp_bookings, 0);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.menu_item_inbox_title, R.drawable.ic_temp_inbox, 0);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.menu_item_more_title, R.drawable.ic_temp_more_h, 0);

        navView.addItem(item1);
        navView.addItem(item2);
        navView.addItem(item3);
        navView.addItem(item4);

        navView.setBehaviorTranslationEnabled(false);
        navView.setDefaultBackgroundResource(R.color.bg_widget);
        navView.setAccentColor(ContextCompat.getColor(navView.getContext(), R.color.main_enabled));
        navView.setInactiveColor(ContextCompat.getColor(navView.getContext(), R.color.text_disabled));

        navView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        return navView;
    }
}
