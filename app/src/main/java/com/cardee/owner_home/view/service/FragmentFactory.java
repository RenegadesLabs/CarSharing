package com.cardee.owner_home.view.service;

import android.support.v4.app.Fragment;

import com.cardee.inbox.InboxFragment;
import com.cardee.owner_bookings.view.BookingListFragment;
import com.cardee.owner_home.view.OwnerCarsFragment;
import com.cardee.owner_home.view.OwnerProfileFragment;
import com.cardee.renter_bookings.view.RenterBookingsListFragment;
import com.cardee.renter_browse_cars.view.RenterBrowseCarsFragment;
import com.cardee.renter_home.view.RenterProfileFragment;

public class FragmentFactory {

    public static Fragment getInstance(Class clazz) {
        if (InboxFragment.class.getName().equals(clazz.getName())) {
            return InboxFragment.newInstance();
        }
        if (OwnerCarsFragment.class.getName().equals(clazz.getName())) {
            return OwnerCarsFragment.newInstance();
        }
        if (OwnerProfileFragment.class.getName().equals(clazz.getName())) {
            return OwnerProfileFragment.newInstance();
        }
        if (RenterProfileFragment.class.getName().equals(clazz.getName())) {
            return RenterProfileFragment.newInstance();
        }
        if (BookingListFragment.class.getName().equals(clazz.getName())) {
            return BookingListFragment.newInstance();
        }
        if (RenterBookingsListFragment.class.getName().equals(clazz.getName())){
            return RenterBookingsListFragment.Companion.newInstance();
        }
        if(RenterBrowseCarsFragment.class.getName().equals(clazz.getName())){
            return RenterBrowseCarsFragment.newInstance();
        }
        return null;
    }
}
