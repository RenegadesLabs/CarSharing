package com.cardee.calendar.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.calendar.view.config.BodyConfig;

public class MonthViewHolderDelegate {

    private BodyConfig config;
    private final DisplayMetrics metrics;
    private final int paddingVertical;
    private final int paddingHorizontal;
    private final int paddingBottom;

    public MonthViewHolderDelegate(BodyConfig config) {
        this.config = config;
        this.metrics = config.getContext().getResources().getDisplayMetrics();
        paddingVertical = convertDpToPx(BodyConfig.TITLE_VERTICAL_PADDING_DP);
        paddingHorizontal = convertDpToPx(BodyConfig.TITLE_HORIZONTAL_PADDING_DP);
        paddingBottom = convertDpToPx(BodyConfig.BOTTOM_PADDING_DP);
    }

    public MonthHolder onCreateViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(createTitle(context));
        layout.addView(createMonthView(context));
        return new MonthHolder(layout);
    }

    private View createTitle(Context context) {
        TextView titleView = new TextView(context);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        titleView.setId(R.id.month_title);
        titleView.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setTextColor(config.getBodyTextColor());
        titleView.setBackground(config.getTitleBackground());
        titleView.setGravity(convertValueToPlatformRelated(config.getTitleGravity()));
        return titleView;
    }

    private View createMonthView(Context context) {
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        recyclerView.setId(R.id.month_body);
        recyclerView.setLayoutManager(new GridLayoutManager(context, config.getColumnCount(), RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setPadding(0, 0, 0, paddingBottom);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new DaysAdapter(config));
        return recyclerView;
    }

    private int convertValueToPlatformRelated(int gravity) {
        switch (gravity) {
            case BodyConfig.TITLE_START:
                return Gravity.START;
            case BodyConfig.TITLE_CENTER:
                return Gravity.CENTER;
            case BodyConfig.TITLE_END:
                return Gravity.END;
            default:
                throw new IllegalArgumentException("Gravity value: " + gravity);
        }
    }

    private int convertDpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
