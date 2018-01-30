package com.cardee.custom.time_picker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.cardee.custom.time_picker.domain.criteria.SelectionState;
import com.cardee.custom.time_picker.model.Hour;

public class HourView extends View {

    private static final float RADIUS_RATIO = .9f;
    private static final float DEFAULT_DISABLED_ALPHA = .25f;
    private static final int DEFAULT_SELECTION_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_SELECTED_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final float DEFAULT_TEXT_SIZE = 14f;

    private Hour hour;
    private String text;

    private int textColor;
    private int selectedTextColor;
    private int width;
    private int height;
    private float textSize;
    private float centerX;
    private float centerY;
    private float centerYText;
    private float radius;
    private RectF markerRect;
    private RectF startMarkerRect;
    private RectF endMarkerRect;
    private Paint selectionPaint;
    private Paint selectionStrokePaint;
    private Paint todayPaint;
    private Paint textPaint;
    private static float[][] checkMarkPath = new float[3][2];

    public HourView(Context context) {
        this(context, null);
    }

    public HourView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HourView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HourView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE, metrics);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextSize(textSize);

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
        centerYText = centerY - ((textPaint.ascent() + textPaint.descent()) / 2);

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
        checkMarkPath[0][1] = centerY + textSize  / 2 + basePoint * 10;
        checkMarkPath[1][0] = checkMarkPath[0][0] + basePoint * 8;
        checkMarkPath[1][1] = checkMarkPath[0][1] + basePoint * 8;
        checkMarkPath[2][0] = checkMarkPath[1][0] + basePoint * 17;
        checkMarkPath[2][1] = checkMarkPath[1][1] - basePoint * 15;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (hour != null && hour.isSelected()) {
            drawSelectionMarker(canvas, hour.getSelectionState());
        }
        if (text != null) {
            drawText(canvas, text);
        }
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

    private Canvas drawText(Canvas canvas, String text) {
        canvas.drawText(text, centerX, centerYText, textPaint);
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

    public void bind(Hour hour) {
        this.hour = hour;
        this.text = hour.getHourTitle();
        if (!hour.isEnabled()) {
            setAlpha(DEFAULT_DISABLED_ALPHA);
        }
        requestLayout();
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
        textPaint.setColor(textColor);
        invalidate();
    }

    public void setSelectedTextColor(int color) {
        selectedTextColor = color;
    }

    public void refresh() {
        int color;
        if (hour != null && hour.isSelected() && hour.getSelectionState() != SelectionState.SELECTED) {
            color = selectedTextColor == 0 ? DEFAULT_SELECTED_TEXT_COLOR : selectedTextColor;
        } else {
            color = textColor == 0 ? DEFAULT_TEXT_COLOR : textColor;
        }
        textPaint.setColor(color);
        invalidate();
    }

    public boolean isSelected() {
        return hour != null && hour.isSelected();
    }

    public Hour getHour() {
        return hour;
    }
}
