package com.cardee.owner_bookings.car_checklist.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cardee.R;


public class PetrolView extends ConstraintLayout implements View.OnClickListener {

    private static final String[] LEVELS = {"1/8", "2/8", "3/8", "1/2",
            "5/8", "6/8", "7/8", "Full"};

    private TextView mPetrolValue;

    private AppCompatEditText mMileageValue;

    private boolean isByMileage;

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
        mPetrolValue = findViewById(R.id.tv_handoverValue);
        mMileageValue = findViewById(R.id.et_mileage);
        findViewById(R.id.iv_handoverPetrolLvlMinus).setOnClickListener(this);
        findViewById(R.id.iv_handoverPetrolLvlPlus).setOnClickListener(this);
    }

    public void setPetrolValue(String val) {
        mPetrolValue.setText(val);
    }

    public void setMileageValue(String val) {
        mMileageValue.setText(val);
    }

    public void setButtonsVisibility(int visibility) {
        findViewById(R.id.iv_handoverPetrolLvlMinus).setVisibility(visibility);
        findViewById(R.id.iv_handoverPetrolLvlPlus).setVisibility(visibility);
    }

    public void switchMileageVisibility(boolean showMileage) {
        if (showMileage) {
            findViewById(R.id.tv_handoverValue).setVisibility(GONE);
            findViewById(R.id.ll_mileageTextContainer).setVisibility(VISIBLE);
            return;
        }
        findViewById(R.id.tv_handoverValue).setVisibility(VISIBLE);
        findViewById(R.id.ll_mileageTextContainer).setVisibility(GONE);
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
            if (mPetrolValue.getText().toString()
                    .split(" ")[0].equals(LEVELS[i])) {

                if (i == 0) {
                    return;
                }
                String val = LEVELS[i - 1] + " Tank";
                mPetrolValue.setText(val);
                break;
            }
        }
    }

    private void onPlusClicked() {
        for (int i = 0; i < LEVELS.length; i++) {
            if (mPetrolValue.getText().toString()
                    .split(" ")[0].equals(LEVELS[i])) {

                if (i == LEVELS.length - 1) {
                    return;
                }
                String val = LEVELS[i + 1] + " Tank";
                mPetrolValue.setText(val);
                break;
            }
        }
    }

    public float getTankFullness() {
        float fullness = 0;
        if (mPetrolValue.getText().toString().split(" ")[0]
                .equals(LEVELS[0])) {
            fullness = 0.125f;
        } else if (mPetrolValue.getText().toString().split(" ")[0]
                .equals(LEVELS[1])) {
            fullness = 0.25f;
        } else if (mPetrolValue.getText().toString().split(" ")[0]
                .equals(LEVELS[2])) {
            fullness = 0.375f;
        } else if (mPetrolValue.getText().toString().split(" ")[0]
                .equals(LEVELS[3])) {
            fullness = 0.5f;
        } else if (mPetrolValue.getText().toString().split(" ")[0]
                .equals(LEVELS[4])) {
            fullness = 0.625f;
        } else if (mPetrolValue.getText().toString().split(" ")[0]
                .equals(LEVELS[5])) {
            fullness = 0.75f;
        } else if (mPetrolValue.getText().toString().split(" ")[0]
                .equals(LEVELS[6])) {
            fullness = 0.875f;
        } else if (mPetrolValue.getText().toString().split(" ")[0]
                .equals(LEVELS[7])) {
            fullness = 1.f;
        } else {
//            isByMileage = true;
        }
        return fullness;
    }

    public int getMileage() {
        return Integer.parseInt(!mMileageValue.getText().toString().equals("") ? mMileageValue.getText().toString() : "0");
    }
}
