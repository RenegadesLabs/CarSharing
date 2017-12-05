package com.cardee.owner_car_details.view.service;

import android.content.Context;
import android.widget.TextView;

import com.cardee.R;

public class SaveAvailabilityButtonTitleDelegate {

    private String[] suffixes;

    public SaveAvailabilityButtonTitleDelegate(Context context) {
        suffixes = context.getResources().getStringArray(R.array.btn_save_title_suffixes);
    }

    public void onTitleChanged(TextView view, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Invalid count value: " + count);
        }
        int index = count > 1 ? 2 : count;
        String prefix = index == 0 ? "" : String.valueOf(count) + " ";
        String title = prefix + suffixes[index];
        view.setText(title);
    }
}
