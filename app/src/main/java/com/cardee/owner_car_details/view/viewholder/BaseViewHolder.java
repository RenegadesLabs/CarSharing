package com.cardee.owner_car_details.view.viewholder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class BaseViewHolder <T> {

    private View rootView;
    private Activity activity;

    public BaseViewHolder(@NonNull View rootView, @NonNull Activity activity) {
        this.rootView = rootView;
        this.activity = activity;
    }

    protected View getRootView() {
        return rootView;
    }

    protected Activity getActivity() {
        return activity;
    }

    public abstract void bind(T model);
}
