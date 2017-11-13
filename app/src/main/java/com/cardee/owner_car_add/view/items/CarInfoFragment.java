package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.owner_car_add.view.CarAddActivity;
import com.cardee.owner_car_add.view.CarAddView;
import com.cardee.owner_car_add.view.NewCarFormsContract;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;
import com.cardee.owner_home.view.modal.BodyMenuFragment;
import com.cardee.owner_home.view.modal.PickerMenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class CarInfoFragment extends Fragment {

    private Unbinder mUnbinder;

    public final static String CAR_INFO_MAKE = "car_make";

    public final static String CAR_INFO_MODEL = "car_model";

    public final static String CAR_INFO_YEAR = "car_year";

    public final static String CAR_INFO_TITLE = "car_title";

    public final static String CAR_INFO_LICENSE_NUMBER = "car_license_number";

    public final static String CAR_INFO_SEATS = "car_seats";

    public final static String CAR_INFO_ENGINE = "car_engine";

    public final static String CAR_INFO_TRANSMISSION = "car_transmission";

    public final static String CAR_INFO_BODY = "car_body";

    @BindView(R.id.et_addCarInfoMake)
    public AppCompatEditText addCarInfoMakeET;

    @BindView(R.id.et_addCarInfoModel)
    public AppCompatEditText addCarInfoModelET;

    @BindView(R.id.et_addCarInfoYear)
    public AppCompatEditText addCarInfoYearET;

    @BindView(R.id.et_addCarInfoCarTitle)
    public AppCompatEditText addCarInfoTitleET;

    @BindView(R.id.et_addCarInfoLicense)
    public AppCompatEditText addCarInfoLicenseET;

    @BindView(R.id.et_addCarInfoSeating)
    public AppCompatEditText addCarInfoSeatsET;

    @BindView(R.id.et_addCarInfoEngine)
    public AppCompatEditText addCarInfoEngineET;

    @BindView(R.id.et_addCarInfoTransmission)
    public AppCompatEditText addCarInfoTransmissionET;

    @BindView(R.id.et_addCarInfoBody)
    public AppCompatEditText addCarInfoBodyET;

    private AppCompatCheckedTextView mLastSelectedBodyType;

    private CarAddView mView;

    private CarAddActivity.CarInfoPassCallback mPassDataCallback;

    private DetailsChangedListener parentListener;

    public static Fragment newInstance() {
        Fragment fragment = new CarInfoFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_car_info, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @OnTextChanged(R.id.et_addCarInfoModel)
    public void onModelInputChanged(CharSequence text) {
        if (addCarInfoModelET.getError() == null)
            return;
        addCarInfoModelET.setError(null);
    }

    @OnTextChanged(R.id.et_addCarInfoMake)
    public void onMakeInputChanged(CharSequence text) {
        if (addCarInfoMakeET.getError() == null)
            return;
        addCarInfoMakeET.setError(null);
    }

    @OnTextChanged(R.id.et_addCarInfoYear)
    public void onYearInputChanged(CharSequence text) {
        if (addCarInfoYearET.getError() == null)
            return;
        addCarInfoYearET.setError(null);
    }

    @OnTextChanged(R.id.et_addCarInfoCarTitle)
    public void onTitleInputChanged(CharSequence text) {
        if (addCarInfoTitleET.getError() == null)
            return;
        addCarInfoTitleET.setError(null);
    }

    @OnTextChanged(R.id.et_addCarInfoLicense)
    public void onLicenseChanged(CharSequence text) {
        if (addCarInfoLicenseET.getError() == null)
            return;
        addCarInfoLicenseET.setError(null);
    }

    @OnTextChanged(R.id.et_addCarInfoSeating)
    public void onSeatsInputChanged(CharSequence text) {
        if (addCarInfoSeatsET.getError() == null)
            return;
        addCarInfoSeatsET.setError(null);
    }

    @OnTextChanged(R.id.et_addCarInfoEngine)
    public void onEngineInputChanged(CharSequence text) {
        if (addCarInfoEngineET.getError() == null)
            return;
        addCarInfoEngineET.setError(null);
    }

    @OnTextChanged(R.id.et_addCarInfoTransmission)
    public void onTransmissionInputChanged(CharSequence text) {
        if (addCarInfoTransmissionET.getError() == null)
            return;
        addCarInfoTransmissionET.setError(null);
    }

    @OnTextChanged(R.id.et_addCarInfoBody)
    public void onBodyInputChanged(CharSequence text) {
        if (addCarInfoBodyET.getError() == null)
            return;
        addCarInfoBodyET.setError(null);
    }

    @OnClick(R.id.et_addCarInfoYear)
    public void onYearOfManufactureClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(addCarInfoYearET.getText().toString(),
                PickerMenuFragment.Mode.YEAR_OF_MANUFACTURE);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(new PickerMenuFragment.DialogOnClickListener() {
            @Override
            public void onDoneClicked(String value) {
                addCarInfoYearET.setText(value);
            }
        });
    }

    @OnClick(R.id.et_addCarInfoSeating)
    public void onSeatingCapacityClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(addCarInfoSeatsET.getText().toString(),
                PickerMenuFragment.Mode.SEATING_CAPACITY);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(new PickerMenuFragment.DialogOnClickListener() {
            @Override
            public void onDoneClicked(String value) {
                addCarInfoSeatsET.setText(value);
            }
        });
    }

    @OnClick(R.id.et_addCarInfoEngine)
    public void onEngineCapacityClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(addCarInfoEngineET.getText().toString(),
                PickerMenuFragment.Mode.ENGINE_CAPACITY);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(new PickerMenuFragment.DialogOnClickListener() {
            @Override
            public void onDoneClicked(String value) {
                addCarInfoEngineET.setText(value);
            }
        });
    }

    @OnClick(R.id.et_addCarInfoTransmission)
    public void onTransmissionClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(addCarInfoTransmissionET.getText().toString(),
                PickerMenuFragment.Mode.TRANSMISSION);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(new PickerMenuFragment.DialogOnClickListener() {
            @Override
            public void onDoneClicked(String value) {
                addCarInfoTransmissionET.setText(value);
            }
        });
    }

    @OnClick(R.id.et_addCarInfoBody)
    public void onBodyTypeClicked() {
        BodyMenuFragment menu = BodyMenuFragment.getInstance(mLastSelectedBodyType);
        menu.setListeners(new BodyMenuFragment.MenuListeners() {
            @Override
            public void onDoneClicked(AppCompatCheckedTextView selected, CharSequence value) {
                addCarInfoBodyET.setText(value);
                mLastSelectedBodyType = selected;
            }

            @Override
            public void onConvertibleClicked() {

            }
        });
        menu.show(getFragmentManager(), menu.getTag());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private String getFieldContent(AppCompatEditText editText) {
        if (editText.getText().toString().equals("")) {
            editText.setError(getString(R.string.error_empty_field));
            return null;
        }
        return editText.getText().toString();
    }

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.INFO);
    }
}
