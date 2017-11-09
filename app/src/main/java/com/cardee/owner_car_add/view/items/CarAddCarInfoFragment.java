package com.cardee.owner_car_add.view.items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.owner_car_add.view.CarAddActivity;
import com.cardee.owner_car_add.view.CarAddView;
import com.cardee.owner_home.view.modal.BodyMenuFragment;
import com.cardee.owner_home.view.modal.PickerMenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class CarAddCarInfoFragment extends CarAddBaseFragment implements CarAddActivity.CarAddActionListener {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_info, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        ((CarAddActivity) getActivity()).setActionListener(this);
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

    @OnClick(R.id.b_carInfoNext)
    public void onNextClicked() {
        saveArguments(new Bundle(), true);
    }


    @Override
    public void onSaveClicked() {
        saveArguments(new Bundle(), false);
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
    void saveArguments(Bundle b, boolean onNext) {

        String make = getFieldContent(addCarInfoMakeET);
        String model = getFieldContent(addCarInfoModelET);
        String year = getFieldContent(addCarInfoYearET);
        String title = getFieldContent(addCarInfoTitleET);
        String license = getFieldContent(addCarInfoLicenseET);
        String seats = getFieldContent(addCarInfoSeatsET);
        String engine = getFieldContent(addCarInfoEngineET);
        String transmission = getFieldContent(addCarInfoTransmissionET);
        String body = getFieldContent(addCarInfoBodyET);

        if (make == null || model == null
                || year == null || title == null
                || license == null || seats == null
                || engine == null || transmission == null
                || body == null) {
            return;
        }

        b.putInt(CarAddBaseFragment.FRAGMENT_NUMBER, 1);
        b.putString(CAR_INFO_MAKE, make);
        b.putString(CAR_INFO_MODEL, model);
        b.putString(CAR_INFO_YEAR, year);
        b.putString(CAR_INFO_TITLE, title);
        b.putString(CAR_INFO_LICENSE_NUMBER, license);
        b.putString(CAR_INFO_SEATS, seats);
        b.putString(CAR_INFO_ENGINE, engine);
        b.putString(CAR_INFO_TRANSMISSION, transmission);
        b.putString(CAR_INFO_BODY, body);
//        getArguments().putAll(b);
        if (mPassDataCallback == null)
            return;
        mPassDataCallback.onPassData(b);

        if (onNext) {
            if (mView == null)
                return;
            mView.onCarImage();
            return;
        }
        getActivity().onBackPressed();
    }

    @Override
    public void setPassDataCallback(CarAddActivity.CarInfoPassCallback callback) {
        mPassDataCallback = callback;
    }

    @Override
    public void setViewListener(CarAddView listener) {
        mView = listener;
    }
}
