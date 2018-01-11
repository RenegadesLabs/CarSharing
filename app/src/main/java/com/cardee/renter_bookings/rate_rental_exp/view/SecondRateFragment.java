package com.cardee.renter_bookings.rate_rental_exp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.custom.CustomRatingBar;

public class SecondRateFragment extends Fragment {

    private byte mOverallRate;
    private OnSecondFragmentClickedListener mListener;

    public SecondRateFragment() {
    }

    public static SecondRateFragment newInstance(OnSecondFragmentClickedListener listener, byte overallRate) {
        SecondRateFragment fragment = new SecondRateFragment();
        fragment.setListener(listener);
        fragment.setOverallRate(overallRate);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_renter_rate_review, container, false);

        CustomRatingBar overall = root.findViewById(R.id.rating_bar_overall);
        overall.setScore((float) mOverallRate);

        AppCompatEditText reviewEditText = root.findViewById(R.id.et_review);
        TextView editRatings = root.findViewById(R.id.edit_ratings);
        editRatings.setOnClickListener(view -> mListener.onEditRateClicked());

        AppCompatButton submitButton = root.findViewById(R.id.b_submit);
        submitButton.setOnClickListener(view -> {
            String review = reviewEditText.getText().toString();
            mListener.onSecondSubmitClicked(review);
        });
        return root;
    }

    public void setListener(OnSecondFragmentClickedListener listener) {
        mListener = listener;
    }

    public void setOverallRate(byte overallRate) {
        mOverallRate = overallRate;
    }

    public interface OnSecondFragmentClickedListener {
        void onSecondSubmitClicked(String review);

        void onEditRateClicked();
    }
}
