package com.cardee.custom.time_picker.view.config;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.cardee.custom.time_picker.view.HourView;
import com.cardee.custom.time_picker.view.listener.OnViewClickListener;

public class BodyConfig {

    private static final int DEFAULT_COLOR = Color.TRANSPARENT;

    public static final int DEFAULT_COLUMN_COUNT = 6;
    public static final int BOTTOM_PADDING_DP = 8;
    public static final int TITLE_HORIZONTAL_PADDING_DP = 18;
    public static final int TITLE_VERTICAL_PADDING_DP = 10;
    public static final int TITLE_CENTER = 1;
    public static final int TITLE_START = 2;
    public static final int TITLE_END = 3;

    private Context context;
    private Drawable titleBackground;
    private int selectionColor;
    private int bodyTextColor;
    private int currentDateColor;
    private int columnCount;
    private int titleGravity;
    private OnViewClickListener<HourView> listener;

    public BodyConfig(Context context) {
        this.context = context;
    }

    public BodyConfig setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        return this;
    }

    public BodyConfig setMonthTitleBackground(Drawable titleBackground) {
        this.titleBackground = titleBackground;
        return this;
    }

    public BodyConfig setBodyTextColor(int bodyTextColor) {
        this.bodyTextColor = bodyTextColor;
        return this;
    }

    public BodyConfig setCurrentDateColor(int currentMonthColor) {
        this.currentDateColor = currentMonthColor;
        return this;
    }

    public BodyConfig setSelectionColor(int selectionColor) {
        this.selectionColor = selectionColor;
        return this;
    }

    public BodyConfig setTitleGravity(int titleGravity) {
        this.titleGravity = titleGravity;
        return this;
    }

    public BodyConfig setDayClickListener(OnViewClickListener<HourView> listener) {
        this.listener = listener;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public int getColumnCount() {
        return columnCount == 0 ? DEFAULT_COLUMN_COUNT : columnCount;
    }

    public Drawable getTitleBackground() {
        return titleBackground == null ? new ColorDrawable(DEFAULT_COLOR) : titleBackground;
    }

    public int getBodyTextColor() {
        return bodyTextColor;
    }

    public int getCurrentDateColor() {
        return currentDateColor;
    }

    public int getSelectionColor() {
        return selectionColor;
    }

    public int getTitleGravity() {
        return titleGravity;
    }

    public OnViewClickListener<HourView> getDayClickListener() {
        return listener;
    }

}
