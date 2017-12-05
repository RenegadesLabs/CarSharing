package com.cardee.owner_car_details.view.service;

import android.content.Context;
import android.widget.TextView;

import com.cardee.R;

public class SaveAvailabilityTitleDelegate {

    private String[] suffixes;

    public SaveAvailabilityTitleDelegate(Context context) {
        suffixes = context.getResources().getStringArray(R.array.btn_save_title_suffixes);
    }

    public void onTitleChanged(TextView view, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Invalid count value: " + count);
        }
        int index = count > 1 ? 2 : count;
        String title = String.valueOf(count) + " " + suffixes[index];
        view.setText(title);
    }
}
