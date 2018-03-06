package com.cardee.custom.calendar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.custom.ChangeStrategy;
import com.cardee.custom.calendar.model.Month;
import com.cardee.custom.calendar.view.adapter.MonthAdapter;
import com.cardee.custom.calendar.view.config.BodyConfig;
import com.cardee.custom.calendar.view.config.HeaderConfig;
import com.cardee.custom.calendar.view.selection.SelectionAdapter;
import com.cardee.custom.calendar.view.selection.SelectionManager;

import java.util.Date;
import java.util.List;

public class CalendarView extends LinearLayout {

    public static final int MODE_MULTISELECT = 1;
    public static final int MODE_RANGE = 2;

    private static final float AUTO_SCROLLING_LEVEL = .3f;

    private CalendarPresenter presenter;
    private RecyclerView bodyRecyclerView;
    private MonthAdapter adapter;
    private SelectionManager selectionManager;
    private OnReadyListener onReadyListener;

    private float xTarget;
    private float yTarget;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        presenter = new CalendarPresenter(this);
        setOrientation(VERTICAL);
        String[] dayz = context.getResources().getStringArray(R.array.days_of_week);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
        HeaderConfig headerConfig = new HeaderConfig(context, dayz);
        BodyConfig bodyConfig = new BodyConfig(context);
        bodyConfig.setColumnCount(dayz.length);
        boolean selectCurrent = typedArray.getBoolean(R.styleable.CalendarView_select_current, true);
        try {
            initHeaderConfig(headerConfig, typedArray);
            initBodyConfig(bodyConfig, typedArray);
        } finally {
            typedArray.recycle();
        }
        adapter = new MonthAdapter(bodyConfig);
        bodyRecyclerView = createBody(context, adapter);
        selectionManager = new SelectionManager(adapter);
        selectionManager.setMessageListener(message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
        bodyConfig.setDayClickListener(selectionManager.getDayClickListener());
        addView(createHeader(headerConfig));
        addView(bodyRecyclerView);
        presenter.retrieveMonthList(selectCurrent);
    }

    private HeaderConfig initHeaderConfig(HeaderConfig config, TypedArray typedArray) {
        Drawable headerBackground = typedArray.getDrawable(R.styleable.CalendarView_header_background);
        int headerTextColor = typedArray.getColor(R.styleable.CalendarView_header_text_color, Color.BLACK);
        int highlightedTextColor = typedArray.getColor(R.styleable.CalendarView_header_highlighted_text_color, headerTextColor);
        int height = typedArray.getDimensionPixelSize(R.styleable.CalendarView_header_height, LayoutParams.WRAP_CONTENT);
        return config.setHeaderBackground(headerBackground)
                .setHeaderHeight(height)
                .setTextColor(headerTextColor)
                .setHighlightedTextColor(highlightedTextColor);
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

    private View createHeader(final HeaderConfig config) {
        GridView gridView = new GridView(config.getContext());
        gridView.setBackground(config.getHeaderBackground());
        gridView.setNumColumns(config.getTitleArray().length);
        gridView.setAdapter(new ArrayAdapter<String>(config.getContext(), 0, config.getTitleArray()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = new TextView(parent.getContext());
                if (position == 0) {
                    textView.setTextColor(config.getHighlightedTextColor());
                } else {
                    textView.setTextColor(config.getTextColor());
                }
                textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, config.getHeaderHeight()));
                textView.setGravity(Gravity.CENTER);
                textView.setText(getItem(position));
                return textView;
            }
        });
        gridView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return gridView;
    }

    private RecyclerView createBody(Context context, final MonthAdapter adapter) {
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

    public void addMonths(List<Month> months) {
        selectionManager.addToPeriod(months);
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

    public void setOnReadyListener(OnReadyListener onReadyListener) {
        this.onReadyListener = onReadyListener;
        if (selectionManager.isReady()) {
            this.onReadyListener.onReady();
        }
    }

    public void setChangeStrategy(ChangeStrategy strategy, @Nullable Date fixedDate) {
        selectionManager.setChangeStrategy(strategy, fixedDate);
    }

    public interface OnMessageListener {
        void onMessage(String message);
    }

    public interface OnReadyListener {

        void onReady();
    }
}
