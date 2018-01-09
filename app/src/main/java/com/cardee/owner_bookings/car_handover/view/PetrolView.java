package com.cardee.owner_bookings.car_handover.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cardee.R;


public class PetrolView extends ConstraintLayout implements View.OnClickListener {

    private static final String[] LEVELS = {"1/8", "2/8", "3/8", "1/2",
            "5/8", "6/8", "7/8", "Full"};

    private TextView mValue;

    public PetrolView(Context context) {
        super(context);
    }

    public PetrolView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PetrolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mValue = findViewById(R.id.tv_handoverValue);
        findViewById(R.id.iv_handoverPetrolLvlMinus).setOnClickListener(this);
        findViewById(R.id.iv_handoverPetrolLvlPlus).setOnClickListener(this);
    }

    public void setValue(String val) {
        mValue.setText(val);
    }

    public void setButtonsVisibility(int visibility) {
        findViewById(R.id.iv_handoverPetrolLvlMinus).setVisibility(visibility);
        findViewById(R.id.iv_handoverPetrolLvlPlus).setVisibility(visibility);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_handoverPetrolLvlPlus:
                onPlusClicked();
                break;
            case R.id.iv_handoverPetrolLvlMinus:
                onMinusClicked();
                break;
        }
    }

    private void onMinusClicked() {
        for (int i = 0; i < LEVELS.length; i++) {
            if (mValue.getText().toString()
                    .split(" ")[0].equals(LEVELS[i])) {

                if (i == 0) {
                    return;
                }
                String val = LEVELS[i - 1] + " Tank";
                mValue.setText(val);
                break;
            }
        }
    }

    private void onPlusClicked() {
        for (int i = 0; i < LEVELS.length; i++) {
            if (mValue.getText().toString()
                    .split(" ")[0].equals(LEVELS[i])) {

                if (i == LEVELS.length - 1) {
                    return;
                }
                String val = LEVELS[i + 1] + " Tank";
                mValue.setText(val);
                break;
            }
        }
    }
}
