package com.cardee.renter_bookings.rate_rental_exp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.custom.CustomRatingBar;

public class FirstRateFragment extends Fragment {

    private static final String CONDITION = "condition";
    private static final String COMFORT = "comfort";
    private static final String OWNER = "owner";
    private static final String OVERALL = "overall";
    private byte mCondition;
    private byte mComfort;
    private byte mOwner;
    private byte mOverall;

    CustomRatingBar conditionBar;
    CustomRatingBar comfortBar;
    CustomRatingBar ownerBar;
    CustomRatingBar overallBar;

    private OnFirstFragmentClickListener mListener;

    public FirstRateFragment() {
    }

    public static FirstRateFragment newInstance(OnFirstFragmentClickListener listener) {
        FirstRateFragment fragment = new FirstRateFragment();

        fragment.setListener(listener);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_renter_rate, container, false);

        conditionBar = root.findViewById(R.id.rating_bar_condition);
        comfortBar = root.findViewById(R.id.rating_bar_comfort);
        ownerBar = root.findViewById(R.id.rating_bar_owner);
        overallBar = root.findViewById(R.id.rating_bar_overall);

        conditionBar.setScore(mCondition);
        comfortBar.setScore(mComfort);
        ownerBar.setScore(mOwner);
        overallBar.setScore(mOverall);

        AppCompatButton submitButton = root.findViewById(R.id.b_submit);
        submitButton.setOnClickListener(view -> {
            mCondition = (byte) conditionBar.getScore();
            mComfort = (byte) comfortBar.getScore();
            mOwner = (byte) ownerBar.getScore();
            mOverall = (byte) overallBar.getScore();

            if (mCondition == 0 || mComfort == 0 || mOwner == 0 || mOverall == 0) {
                return;
            }
            
            mListener.onFirstSubmitClicked(mCondition, mComfort, mOwner, mOverall);
        });
        return root;
    }

    public void setListener(OnFirstFragmentClickListener listener) {
        mListener = listener;
    }

    public interface OnFirstFragmentClickListener {
        void onFirstSubmitClicked(byte condition, byte comfort, byte owner, byte overall);
    }

}
