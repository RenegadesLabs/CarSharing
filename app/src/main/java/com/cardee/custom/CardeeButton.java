package com.cardee.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.cardee.R;


public class CardeeButton extends AppCompatButton {

    public CardeeButton(Context context) {
        super(context);
    }

    public CardeeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CardeeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CardeeButton);

            Drawable drawableStart = null;
            Drawable drawableEnd = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableStart = attributeArray.getDrawable(R.styleable.CardeeButton_drawableStartCompat);
                drawableEnd = attributeArray.getDrawable(R.styleable.CardeeButton_drawableEndCompat);
            } else {
                final int drawableLeftId = attributeArray.getResourceId(R.styleable.CardeeButton_drawableStartCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(R.styleable.CardeeButton_drawableEndCompat, -1);
                if (drawableLeftId != -1)
                    drawableStart = AppCompatResources.getDrawable(context, drawableLeftId);

                if (drawableRightId != -1)
                    drawableEnd = AppCompatResources.getDrawable(context, drawableRightId);
            }
            setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null);
            attributeArray.recycle();
        }
    }
}
