package com.cardee.owner_home.view.service;

import android.support.v4.app.Fragment;

import com.cardee.owner_home.view.fragment.inbox.InboxFragment;
import com.cardee.owner_home.view.OwnerCarsFragment;
import com.cardee.owner_home.view.OwnerProfileFragment;

public class FragmentFactory {

    public static Fragment getInstance(Class clazz) {
        if (InboxFragment.class.getName().equals(clazz.getName())){
            return InboxFragment.newInstance();
        }
        if (OwnerCarsFragment.class.getName().equals(clazz.getName())) {
            return OwnerCarsFragment.newInstance();
        }
        if (OwnerProfileFragment.class.getName().equals(clazz.getName())) {
            return OwnerProfileFragment.newInstance();
        }
        return null;
    }
}
