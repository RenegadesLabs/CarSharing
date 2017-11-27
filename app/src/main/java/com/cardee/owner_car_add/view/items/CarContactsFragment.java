package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.presenter.CarContactsPresenter;
import com.cardee.owner_car_add.view.NewCarFormsContract;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CarContactsFragment extends Fragment implements NewCarFormsContract.View {

    private Unbinder mUnbinder;
    private Toast currentToast;

    @BindView(R.id.tiet_addCarContactPhone)
    public TextInputEditText carOwnerPhone;
    @BindView(R.id.tiet_addCarContactCode)
    public TextInputEditText carOwnerCode;
    @BindView(R.id.et_addCatContactEmail)
    public AppCompatEditText carOwnerEmail;
    @BindView(R.id.et_addCatContactName)
    public AppCompatEditText carOwnerName;

    private DetailsChangedListener parentListener;
    private NewCarFormsContract.Action pendingAction;
    private CarContactsPresenter presenter;
    private SimpleBinder binder = new SimpleBinder() {
        @Override
        public void push(Bundle args) {
            NewCarFormsContract.Action action = (NewCarFormsContract.Action)
                    args.getSerializable(NewCarFormsContract.ACTION);
            if (action == null) {
                return;
            }
            pendingAction = action;
            switch (action) {
                case SAVE:
                case FINISH:
                    saveContactInfo();
                    break;
            }
        }
    };

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
        View rootView = inflater.inflate(R.layout.fragment_add_car_contact, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        carOwnerPhone.addTextChangedListener(textWatcherPhone);
        carOwnerCode.addTextChangedListener(textWatcherCode);
        presenter = new CarContactsPresenter(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.init();
        parentListener.onBind(binder);
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.CONTACT);
    }

    @Override
    public void showProgress(boolean show) {
        parentListener.showProgress(show);
    }

    @Override
    public void showMessage(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void showMessage(@StringRes int messageId) {

    }

    @Override
    public void setCarData(CarData carData) {
        carOwnerName.setText(carData.getContactName());
        carOwnerEmail.setText(carData.getContactEmail());
        String phone = carData.getContactPhone();
        if (phone != null) {
            String countryCode = carOwnerCode.getText().toString();
            if (phone.startsWith(countryCode)) {
                carOwnerPhone.setText(phone.substring(countryCode.length()));
            } else {
                carOwnerPhone.setText(phone.replace("+", ""));
            }
        }
    }

    private boolean isValid() {
        return !carOwnerPhone.getText().toString().isEmpty() &&
                carOwnerCode.getText().toString().matches("\\+[0-9]{2,}") &&
                !carOwnerName.getText().toString().isEmpty() &&
                !carOwnerEmail.getText().toString().isEmpty();
    }

    private void saveContactInfo() {
        if (isValid()) {
            String code = carOwnerCode.getText().toString();
            String phone = carOwnerPhone.getText().toString();
            presenter.saveContactInfo(
                    carOwnerName.getText().toString(),
                    code + phone,
                    carOwnerEmail.getText().toString());
        } else {
            showMessage("Please fill in all fields");
        }
    }


    @Override
    public void onFinish() {
        parentListener.onFinish(NewCarFormsContract.Mode.CONTACT, pendingAction);
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
