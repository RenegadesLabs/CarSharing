package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.mvp.BaseView;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CarPaymentFragment extends Fragment implements NewCarFormsContract.View {

    private Unbinder mUnbinder;

    private DetailsChangedListener parentListener;
    private NewCarFormsContract.Action pendingAction;

    public static Fragment newInstance() {
        Fragment fragment = new CarPaymentFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailsChangedListener) {
            parentListener = (DetailsChangedListener) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailsChangedListener) {
            parentListener = (DetailsChangedListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_payment, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        TextView learnMore = v.findViewById(R.id.learnMore);
        if (learnMore != null) {
            SpannableString spannableString = new SpannableString(learnMore.getText().toString());
            spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            learnMore.setText(spannableString);
        }

        AppCompatButton btnStripe = v.findViewById(R.id.btnStripe);
        btnStripe.setOnClickListener(view -> showMessage("Coming soon"));
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.PAYMENT);
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {
        ((BaseView) getActivity()).showMessage(message);
    }

    @Override
    public void showMessage(@StringRes int messageId) {

    }

    @Override
    public void setCarData(CarData carData) {

    }

    @Override
    public void onFinish() {
        parentListener.onFinish(NewCarFormsContract.Mode.PAYMENT, pendingAction);
    }

    @Override
    public void onNoSavedCar() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
