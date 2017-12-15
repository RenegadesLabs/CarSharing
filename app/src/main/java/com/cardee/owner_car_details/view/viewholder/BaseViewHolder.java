package com.cardee.owner_car_details.view.viewholder;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class BaseViewHolder<T> {

    private View rootView;
    private FragmentActivity activity;

    public BaseViewHolder(@NonNull View rootView, @NonNull FragmentActivity activity) {
        this.rootView = rootView;
        this.activity = activity;
    }

    protected View getRootView() {
        return rootView;
    }

    protected FragmentActivity getActivity() {
        return activity;
    }

    public abstract void bind(T model);
}
