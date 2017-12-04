package com.cardee.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.cardee.calendar.domain.criteria.SelectionState;
import com.cardee.calendar.model.Day;

public class DayView extends AppCompatTextView {

    private static final float RADIUS_RATIO = .9f;
    private static final float DEFAULT_DISABLED_ALPHA = .4f;
    private static final int DEFAULT_SELECTION_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_SELECTED_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    private Day day;

    private int textColor;
    private int selectedTextColor;
    private int width;
    private int height;
    private float centerX;
    private float centerY;
    private float radius;
    private RectF markerRect;
    private RectF startMarkerRect;
    private RectF endMarkerRect;
    private Paint selectionPaint;
    private Paint selectionStrokePaint;
    private Paint todayPaint;
    private static float[][] checkMarkPath = new float[3][2];

    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);

        selectionPaint = new Paint();
        selectionPaint.setColor(DEFAULT_SELECTION_COLOR);
        selectionPaint.setAntiAlias(true);

        todayPaint = new Paint();
        todayPaint.setColor(DEFAULT_SELECTION_COLOR);
        todayPaint.setAntiAlias(true);
        todayPaint.setStyle(Paint.Style.STROKE);
        todayPaint.setStrokeWidth(2);

        selectionStrokePaint = new Paint();
        selectionStrokePaint.setColor(DEFAULT_SELECTION_COLOR);
        selectionStrokePaint.setAntiAlias(true);
        selectionStrokePaint.setStyle(Paint.Style.STROKE);
        selectionStrokePaint.setStrokeWidth(9);
        selectionStrokePaint.setStrokeCap(Paint.Cap.ROUND);

        markerRect = new RectF();
        startMarkerRect = new RectF();
        endMarkerRect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = height = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, height);
        initSelectionMeasures();
    }


    private void initSelectionMeasures() {
        float basePoint = width / 100;
        centerX = (float) width / 2;
        centerY = (float) height / 2;
        radius = centerX * RADIUS_RATIO;

        float offset = centerY - radius;

        markerRect.left = 0;
        markerRect.top = offset;
        markerRect.right = width;
        markerRect.bottom = height - offset;

        startMarkerRect.left = centerX;
        startMarkerRect.top = offset;
        startMarkerRect.right = width;
        startMarkerRect.bottom = height - offset;

        endMarkerRect.left = 0;
        endMarkerRect.top = offset;
        endMarkerRect.right = centerX;
        endMarkerRect.bottom = height - offset;

        checkMarkPath[0][0] = centerX + basePoint * 10;
        checkMarkPath[0][1] = getBaseline() + basePoint * 10;
        checkMarkPath[1][0] = checkMarkPath[0][0] + basePoint * 8;
        checkMarkPath[1][1] = checkMarkPath[0][1] + basePoint * 8;
        checkMarkPath[2][0] = checkMarkPath[1][0] + basePoint * 17;
        checkMarkPath[2][1] = checkMarkPath[1][1] - basePoint * 15;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (day != null && day.isSelected()) {
            drawSelectionMarker(canvas, day.getSelectionState());
        }
        if (day != null && day.isCurrent()) {
            drawTodayMarker(canvas);
        }
        super.onDraw(canvas);
    }

    private Canvas drawTodayMarker(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, todayPaint);
        return canvas;
    }

    private Canvas drawSelectionMarker(Canvas canvas, SelectionState state) {
        if (state == SelectionState.SELECTED) {
            return drawCheckMark(canvas);
        }
        if (state == SelectionState.RANGE_DAY) {
            canvas.drawRect(markerRect, selectionPaint);
            return canvas;
        }
        switch (state) {
            case RANGE_START_DAY:
                canvas.drawRect(startMarkerRect, selectionPaint);
                break;
            case RANGE_END_DAY:
                canvas.drawRect(endMarkerRect, selectionPaint);
                break;
        }
        canvas.drawCircle(centerX, centerY, radius, selectionPaint);
        return canvas;
    }

    private Canvas drawCheckMark(Canvas canvas) {
        canvas.drawLine(
                checkMarkPath[0][0],
                checkMarkPath[0][1],
                checkMarkPath[1][0],
                checkMarkPath[1][1],
                selectionStrokePaint);
        canvas.drawLine(
                checkMarkPath[1][0],
                checkMarkPath[1][1],
                checkMarkPath[2][0],
                checkMarkPath[2][1],
                selectionStrokePaint);
        return canvas;
    }

    public void bind(Day day) {
        this.day = day;
        setText(day.getTitle());
        if (!day.isEnabled()) {
            setAlpha(DEFAULT_DISABLED_ALPHA);
        }
    }

    public void setSelectionColor(int color) {
        selectionPaint.setColor(color);
        selectionStrokePaint.setColor(color);
    }

    public void setTodayColor(int color) {
        todayPaint.setColor(color);
    }

    public void setIdleTextColor(int color) {
        textColor = color;
        super.setTextColor(color);
    }

    public void setSelectedTextColor(int color) {
        selectedTextColor = color;
    }

    public void refresh() {
        int color;
        if (day != null && day.isSelected() && day.getSelectionState() != SelectionState.SELECTED) {
            color = selectedTextColor == 0 ? DEFAULT_SELECTED_TEXT_COLOR : selectedTextColor;
        } else {
            color = textColor == 0 ? DEFAULT_TEXT_COLOR : textColor;
        }
        setTextColor(color);
        invalidate();
    }

    public boolean isSelected() {
        return day != null && day.isSelected();
    }

    public Day getDay() {
        return day;
    }
}
