package com.cardee.custom.calendar.view.config;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

public class HeaderConfig {
    private static final int DEFAULT_COLOR = Color.TRANSPARENT;

    private Context context;
    private String[] titleArray;
    private int headerHeight;
    private Drawable headerBackground;
    private int textColor;
    private int highlightedTextColor;

    public HeaderConfig(Context context, String[] titleArray) {
        this.context = context;
        this.titleArray = titleArray;
    }

    public HeaderConfig setHeaderHeight(int headerHeight) {
        this.headerHeight = headerHeight;
        return this;
    }

    public HeaderConfig setHeaderBackground(Drawable headerBackground) {
        this.headerBackground = headerBackground;
        return this;
    }

    public HeaderConfig setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public HeaderConfig setHighlightedTextColor(int highlightedTextColor) {
        this.highlightedTextColor = highlightedTextColor;
        return this;
    }

    public int getHeaderHeight() {
        return headerHeight;
    }

    public Drawable getHeaderBackground() {
        return headerBackground == null ? new ColorDrawable(DEFAULT_COLOR) : headerBackground;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getHighlightedTextColor() {
        return highlightedTextColor;
    }

    public String[] getTitleArray() {
        return titleArray;
    }

    public Context getContext() {
        return context;
    }
}
