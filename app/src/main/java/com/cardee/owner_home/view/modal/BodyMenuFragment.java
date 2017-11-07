package com.cardee.owner_home.view.modal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.View;
import android.view.ViewParent;

import com.cardee.R;

public class BodyMenuFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    AppCompatCheckedTextView[] mTvs;

    AppCompatCheckedTextView mSelected;

    public interface MenuListeners {
        void onDoneClicked(AppCompatCheckedTextView selected, CharSequence value);
        void onConvertibleClicked();
    }

    public static BodyMenuFragment getInstance(AppCompatCheckedTextView selected) {
        BodyMenuFragment fragment = new BodyMenuFragment();
        fragment.mSelected = selected;
        return fragment;
    }

    private MenuListeners mListener;

    public void setListeners(MenuListeners listener) {
        mListener = listener;
    }


    private BottomSheetBehavior.BottomSheetCallback mCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View rootView = View.inflate(getContext(), R.layout.modal_dialog_body_type, null);
        dialog.setContentView(rootView);

        AppCompatCheckedTextView sedan = rootView.findViewById(R.id.body_sedan);
        sedan.setOnClickListener(this);

        AppCompatCheckedTextView hatchback = rootView.findViewById(R.id.body_hatchback);
        hatchback.setOnClickListener(this);

        AppCompatCheckedTextView suv = rootView.findViewById(R.id.body_suv);
        suv.setOnClickListener(this);

        AppCompatCheckedTextView sports = rootView.findViewById(R.id.body_sports);
        sports.setOnClickListener(this);

        mTvs = new AppCompatCheckedTextView []{sedan, hatchback, suv, sports};

        if (mSelected != null) {
            for (AppCompatCheckedTextView tv : mTvs) {
                if (mSelected.getId() == tv.getId()) {
                    tv.setChecked(true);
                    tv.setCheckMarkDrawable(R.drawable.ic_check);
                    tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
                    break;
                }
            }
        } else {
            sedan.setChecked(true);
            sedan.setCheckMarkDrawable(R.drawable.ic_check);
            sedan.setTypeface(sedan.getTypeface(), Typeface.BOLD);
        }


        rootView.findViewById(R.id.body_convertible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener == null) {
                    return;
                }
                dismiss();
                mListener.onConvertibleClicked();
            }
        });

        rootView.findViewById(R.id.body_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener == null) {
                    return;
                }
                dismiss();
                mListener.onDoneClicked(getSelected(), getSelectedValue());
            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) rootView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mCallback);
        }
        ViewParent parent = rootView.getParent();
        if (parent != null) {
            ((View) parent).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View view) {
        for (AppCompatCheckedTextView tv : mTvs) {
            if (view.getId() == tv.getId()) {
                tv.setChecked(true);
                tv.setCheckMarkDrawable(R.drawable.ic_check);
                tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
            } else {
                tv.setChecked(false);
                tv.setCheckMarkDrawable(null);
                tv.setTypeface(null);
            }
        }
    }

    private CharSequence getSelectedValue() {
        CharSequence selected = null;
        for (AppCompatCheckedTextView tv : mTvs) {
            if (tv.isChecked()) {
                selected = tv.getText();
                break;
            }
        }
        return selected;
    }

    private AppCompatCheckedTextView getSelected() {
        AppCompatCheckedTextView selected = null;
        for (AppCompatCheckedTextView tv : mTvs) {
            if (tv.isChecked()) {
                selected = tv;
                break;
            }
        }
        return selected;
    }
}
