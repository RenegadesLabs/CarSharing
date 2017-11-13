package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.owner_car_add.view.NewCarFormsContract;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CarContactsFragment extends Fragment {

    private Unbinder mUnbinder;

    @BindView(R.id.tiet_addCarContactPhone)
    public TextInputEditText addCarContactPhoneET;

    @BindView(R.id.tiet_addCarContactCode)
    public TextInputEditText addCarContactCodeET;

    private DetailsChangedListener parentListener;

    public static Fragment newInstance() {
        Fragment fragment = new CarContactsFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_car_contact, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        addCarContactPhoneET.addTextChangedListener(textWatcherPhone);
        addCarContactCodeET.addTextChangedListener(textWatcherCode);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.CONTACT);
    }

    private TextWatcher textWatcherPhone = new TextWatcher() {
        int length_before = 0;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            length_before = s.length();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (length_before < s.length()) {
                if (length_before < s.length()) {
                    if (s.length() == 3 || s.length() == 7)
                        s.append("-");
                    if (s.length() > 3) {
                        if (Character.isDigit(s.charAt(3)))
                            s.insert(3, "-");
                    }
                    if (s.length() > 7) {
                        if (Character.isDigit(s.charAt(7)))
                            s.insert(7, "-");
                    }
                }
            }
        }
    };

    private TextWatcher textWatcherCode = new TextWatcher() {
        int length_before = 0;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            length_before = s.length();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (length_before < s.length()) {
                if (length_before < s.length()) {
                    if (s.length() == 1)
                        s.insert(0, "+");
                }
            }
        }
    };
}
