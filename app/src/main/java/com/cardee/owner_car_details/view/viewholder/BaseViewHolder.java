package com.cardee.owner_car_details.view.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseViewHolder <T> {

    private View rootView;
    private AppCompatActivity activity;

    public BaseViewHolder(@NonNull View rootView, @NonNull AppCompatActivity activity) {
        this.rootView = rootView;
        this.activity = activity;
    }

    protected View getRootView() {
        return rootView;
    }

    protected AppCompatActivity getActivity() {
        return activity;
    }

    public abstract void bind(T model);
}
