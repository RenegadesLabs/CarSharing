package com.cardee.owner_home.view.service;

import android.support.v4.app.Fragment;

import com.cardee.owner_home.view.OwnerCarsFragment;

public class FragmentFactory {

    public static Fragment getInstance(Class clazz) {
        if (OwnerCarsFragment.class.getName().equals(clazz.getName())) {
            return OwnerCarsFragment.newInstance();
        }
        return null;
    }
}
