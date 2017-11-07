package com.cardee.custom;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.cardee.R;

public class CarProgressBar extends View {

    private static final String TAG = CarProgressBar.class.getSimpleName();

    private static final int DEFAULT_HEIGHT = 0;
    private static final int DEFAULT_ICON_MARGIN = 22;
    private static final int DEFAULT_DASH_INTERVAL = 14;
    private static final int DEFAULT_DASH_LENGTH = 16;

    private Bitmap mIcon;
    private int mIconHeight;
    private Paint mLinePaint;
    private Paint mIconPaint;

    private int mLineY;
    private float mIconOffset;
    private float mIconMaxOffset;
    private Path mLinePath;
    private Rect mBitmapRect;
    private RectF mIconRect;

    private float mCurrentProgress;

    public CarProgressBar(Context context) {
        this(context, null);
    }

    public CarProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CarProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CarProgressBar, defStyleAttr, defStyleRes);
        int iconId = typedArray.getResourceId(R.styleable.CarProgressBar_Icon, 0);
        mIconHeight = typedArray.getDimensionPixelSize(R.styleable.CarProgressBar_IconHeight, DEFAULT_HEIGHT);
        int lineWidth = typedArray.getDimensionPixelSize(R.styleable.CarProgressBar_LineWidth, DEFAULT_HEIGHT);
        int lineColor = typedArray.getColor(R.styleable.CarProgressBar_LineColor, Color.WHITE);
        typedArray.recycle();

        mIcon = getBitmapFromVectorDrawable(iconId, context);
        mIconPaint = new Paint();
        mIconPaint.setAntiAlias(true);

        mLinePath = new Path();

        mLinePaint = new Paint();
        mLinePaint.setColor(lineColor);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(lineWidth);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{DEFAULT_DASH_LENGTH, DEFAULT_DASH_INTERVAL}, 0));

        mBitmapRect = new Rect(0, 0, mIcon.getWidth(), mIcon.getHeight());
    }

    private Bitmap getBitmapFromVectorDrawable(@DrawableRes int drawableId, Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        float iconHeight = height > mIconHeight ? mIconHeight : 0;
        mLineY = height / 2;

        float sizeRatio = (float) mBitmapRect.bottom / mBitmapRect.right;
        float iconWidth = mIconHeight / sizeRatio;
        float iconHalfHeight = iconHeight / 2;
        mIconRect = new RectF(0, mLineY - iconHalfHeight, iconWidth, mLineY + iconHalfHeight);
        mIconMaxOffset = width - mIconRect.width();
        mIconOffset = mIconMaxOffset * (mCurrentProgress / 100);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mLinePath, mLinePaint);
        canvas.drawBitmap(mIcon, mBitmapRect, mIconRect, mIconPaint);
    }

    public void setProgress(float percent) {
        if (percent < 0 || percent > 100) {
            Log.e(TAG, "Progress value is out of range of 0 to 100");
        }
        mCurrentProgress = normalizeProgressValue(percent);
        computeNewPositions();
        invalidate();
    }

    private void computeNewPositions() {
        float newIconOffset = mIconMaxOffset * (mCurrentProgress / 100);
        float shift = newIconOffset - mIconOffset;
        mIconOffset = newIconOffset;
        mIconRect.left = mIconRect.left + shift;
        mIconRect.right = mIconRect.right + shift;

        float lineLength = mIconOffset - DEFAULT_ICON_MARGIN;
        mLinePath.moveTo(0, mLineY);
        mLinePath.lineTo(lineLength, mLineY);
    }

    public void recycle() {
        mIcon.recycle();
    }

    private float normalizeProgressValue(float value) {
        return value > 100 ? 100 : value < 0 ? 0 : value;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState state = new SavedState(parcelable);

        state.saveProgress(mCurrentProgress);
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState state = (SavedState) parcelable;
        super.onRestoreInstanceState(state);
        mCurrentProgress = state.getSavedProgress();
    }

    static class SavedState extends BaseSavedState {

        private float mSavedProgress;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            mSavedProgress = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(mSavedProgress);
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel parcel) {
                        return new SavedState(parcel);
                    }

                    @Override
                    public SavedState[] newArray(int i) {
                        return new SavedState[i];
                    }
                };

        public float getSavedProgress() {
            return mSavedProgress;
        }

        public void saveProgress(float progress) {
            mSavedProgress = progress;
        }
    }
}
