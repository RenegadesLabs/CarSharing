package com.cardee.custom.time_picker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.cardee.R;
import com.cardee.custom.calendar.view.CalendarView;
import com.cardee.custom.time_picker.model.Day;
import com.cardee.custom.time_picker.view.adapter.DayAdapter;
import com.cardee.custom.time_picker.view.config.BodyConfig;
import com.cardee.custom.time_picker.view.selection.SelectionAdapter;
import com.cardee.custom.time_picker.view.selection.SelectionManager;

import java.util.List;

public class TimePicker extends LinearLayout {

    public static final int MODE_MULTISELECT = 1;
    public static final int MODE_RANGE = 2;

    private static final float AUTO_SCROLLING_LEVEL = .3f;

    private TimePickerPresenter presenter;
    private RecyclerView bodyRecyclerView;
    private DayAdapter adapter;
    private SelectionManager selectionManager;
    private OnReadyListener onReadyListener;

    private float xTarget;
    private float yTarget;

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        presenter = new TimePickerPresenter(this);
        setOrientation(VERTICAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
        BodyConfig bodyConfig = new BodyConfig(context);
        bodyConfig.setColumnCount(6);
        try {
            initBodyConfig(bodyConfig, typedArray);
        } finally {
            typedArray.recycle();
        }
        adapter = new DayAdapter(bodyConfig);
        bodyRecyclerView = createBody(context, adapter);
        selectionManager = new SelectionManager(adapter);
        bodyConfig.setDayClickListener(selectionManager.getDayClickListener());
        addView(bodyRecyclerView);
        presenter.retrieveDays();
    }

    private BodyConfig initBodyConfig(BodyConfig config, TypedArray typedArray) {
        Drawable titleBackground = typedArray.getDrawable(R.styleable.CalendarView_title_background);
        int headerTextColor = typedArray.getColor(R.styleable.CalendarView_header_text_color, Color.BLACK);
        int titleGravity = typedArray.getInt(R.styleable.CalendarView_title_gravity, BodyConfig.TITLE_CENTER);
        int selectionColor = typedArray.getColor(R.styleable.CalendarView_selection_color, Color.RED);
        int bodyTextColor = typedArray.getColor(R.styleable.CalendarView_body_text_color, headerTextColor);
        int currentDayColor = typedArray.getColor(R.styleable.CalendarView_current_date_color, Color.rgb(200, 200, 200));
        return config.setBodyTextColor(bodyTextColor)
                .setCurrentDateColor(currentDayColor)
                .setMonthTitleBackground(titleBackground)
                .setSelectionColor(selectionColor)
                .setTitleGravity(titleGravity);
    }

    private RecyclerView createBody(Context context, final DayAdapter adapter) {
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View child = recyclerView.findChildViewUnder(xTarget, yTarget);
                    int yOffset = (int) child.getY();
                    recyclerView.smoothScrollBy(0, yOffset, new DecelerateInterpolator());
                }
            }
        });
        return recyclerView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        xTarget = (float) width / 2;
        yTarget = (float) height * AUTO_SCROLLING_LEVEL;
        setMeasuredDimension(width, height);
    }

    public void addMonths(List<Day> days) {
        selectionManager.addToPeriod(days);
        if (onReadyListener != null) {
            onReadyListener.onReady();
        }
    }

    public void setIncludeCurrent(boolean include) {
        selectionManager.setIncludeCurrent(include);
    }

    public void reset() {
        selectionManager.reset();
    }

    public void setSelectionAdapter(SelectionAdapter selectionAdapter) {
        selectionManager.setSelectionAdapter(selectionAdapter);
    }

    public void setOnReadyListener(TimePicker.OnReadyListener onReadyListener) {
        this.onReadyListener = onReadyListener;
        if (selectionManager.isReady()) {
            this.onReadyListener.onReady();
        }
    }

    public interface OnReadyListener {

        void onReady();
    }
}
