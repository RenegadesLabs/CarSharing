package com.cardee.owner_car_add.view.items;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.custom.modal.PickerMenuFragment;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_add.presenter.CarInfoPresenter;
import com.cardee.owner_car_details.view.binder.SimpleBinder;
import com.cardee.owner_car_details.view.listener.DetailsChangedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class CarInfoFragment extends Fragment implements NewCarFormsContract.View {

    private Unbinder mUnbinder;

    @BindView(R.id.et_addCarInfoMake)
    public AppCompatEditText makeInput;
    @BindView(R.id.et_addCarInfoModel)
    public AppCompatEditText modelInput;
    @BindView(R.id.et_addCarInfoYear)
    public AppCompatEditText yearInput;
    @BindView(R.id.et_addCarInfoCarTitle)
    public AppCompatEditText titleInput;
    @BindView(R.id.et_addCarInfoLicense)
    public AppCompatEditText licenceNumberInput;
    @BindView(R.id.et_addCarInfoSeating)
    public AppCompatEditText seatingCapacityInput;
    @BindView(R.id.et_addCarInfoEngine)
    public AppCompatEditText engineCapacityInput;
    @BindView(R.id.et_addCarInfoTransmission)
    public AppCompatEditText transmissionInput;
    @BindView(R.id.et_addCarInfoBody)
    public AppCompatEditText bodyTypeInput;

    private NewCarFormsContract.Action pendingAction;
    private DetailsChangedListener parentListener;
    private CarInfoPresenter presenter;
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
                    onSave(false);
                    break;
                case FINISH:
                    onSave(true);
                    break;
            }
        }
    };

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
        View rootView = inflater.inflate(R.layout.fragment_add_car_info, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        presenter = new CarInfoPresenter(this);
        presenter.init();
        return rootView;
    }

    @OnTextChanged(R.id.et_addCarInfoModel)
    public void onModelInputChanged(CharSequence text) {
        if (modelInput.getError() != null) {
            modelInput.setError(null);
        }
    }

    @OnTextChanged(R.id.et_addCarInfoMake)
    public void onMakeInputChanged(CharSequence text) {
        if (makeInput.getError() != null) {
            makeInput.setError(null);
        }
    }

    @OnTextChanged(R.id.et_addCarInfoYear)
    public void onYearInputChanged(CharSequence text) {
        if (yearInput.getError() != null) {
            yearInput.setError(null);
        }
    }

    @OnTextChanged(R.id.et_addCarInfoCarTitle)
    public void onTitleInputChanged(CharSequence text) {
        if (titleInput.getError() != null) {
            titleInput.setError(null);
        }
    }

    @OnTextChanged(R.id.et_addCarInfoLicense)
    public void onLicenseChanged(CharSequence text) {
        if (licenceNumberInput.getError() != null) {
            licenceNumberInput.setError(null);
        }
    }

    @OnTextChanged(R.id.et_addCarInfoSeating)
    public void onSeatsInputChanged(CharSequence text) {
        if (seatingCapacityInput.getError() != null) {
            seatingCapacityInput.setError(null);
        }
    }

    @OnTextChanged(R.id.et_addCarInfoEngine)
    public void onEngineInputChanged(CharSequence text) {
        if (engineCapacityInput.getError() != null) {
            engineCapacityInput.setError(null);
        }
    }

    @OnTextChanged(R.id.et_addCarInfoTransmission)
    public void onTransmissionInputChanged(CharSequence text) {
        if (transmissionInput.getError() != null) {
            transmissionInput.setError(null);
        }
    }

    @OnTextChanged(R.id.et_addCarInfoBody)
    public void onBodyInputChanged(CharSequence text) {
        if (bodyTypeInput.getError() != null) {
            bodyTypeInput.setError(null);
        }
    }

    @OnClick(R.id.et_addCarInfoYear)
    public void onYearOfManufactureClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(yearInput.getText().toString(),
                PickerMenuFragment.Mode.YEAR_OF_MANUFACTURE);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(value -> yearInput.setText(value));
    }

    @OnClick(R.id.et_addCarInfoSeating)
    public void onSeatingCapacityClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(seatingCapacityInput.getText().toString(),
                PickerMenuFragment.Mode.SEATING_CAPACITY);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(value -> seatingCapacityInput.setText(value));
    }

    @OnClick(R.id.et_addCarInfoEngine)
    public void onEngineCapacityClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(engineCapacityInput.getText().toString(),
                PickerMenuFragment.Mode.ENGINE_CAPACITY);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(value -> engineCapacityInput.setText(value.split(" ")[0]));
    }

    @OnClick(R.id.et_addCarInfoTransmission)
    public void onTransmissionClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(transmissionInput.getText().toString(),
                PickerMenuFragment.Mode.TRANSMISSION);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(value -> transmissionInput.setText(value));
    }

    @OnClick(R.id.et_addCarInfoBody)
    public void onBodyTypeClicked() {
        PickerMenuFragment menu = PickerMenuFragment.getInstance(transmissionInput.getText().toString(),
                PickerMenuFragment.Mode.BODY_TYPE);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(value -> bodyTypeInput.setText(value));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private boolean isFieldValid(AppCompatEditText editText) {
        if (editText.getText().toString().equals("")) {
            editText.setError(getString(R.string.error_empty_field));
            return false;
        }
        return true;
    }

    private String getFieldContent(AppCompatEditText editText) {
        if (editText.getText().toString().equals("")) {
            return null;
        }
        return editText.getText().toString().trim();
    }


    private void onSave(boolean validate) {
        if (validate) {
            if (isValid()) {
                saveInputs();
            }
        } else {
            saveInputs();
        }
    }

    private boolean isValid() {
        return isFieldValid(makeInput) &&
                isFieldValid(modelInput) &&
                isFieldValid(yearInput) &&
                isFieldValid(titleInput) &&
                isFieldValid(licenceNumberInput) &&
                isFieldValid(seatingCapacityInput) &&
                isFieldValid(engineCapacityInput) &&
                isFieldValid(transmissionInput) &&
                isFieldValid(bodyTypeInput);
    }

    private void saveInputs() {
        String transmissionName = getFieldContent(transmissionInput);
        String bodyTypeName = getFieldContent(bodyTypeInput);
        presenter.saveInputs(
                getFieldContent(makeInput),
                getFieldContent(modelInput),
                getFieldContent(yearInput),
                getFieldContent(titleInput),
                getFieldContent(licenceNumberInput),
                getFieldContent(seatingCapacityInput),
                getFieldContent(engineCapacityInput),
                NewCarFormsContract.Transmission.getIdByName(transmissionName),
                NewCarFormsContract.BodyType.getIdByName(bodyTypeName));
    }

    @Override
    public void onStart() {
        super.onStart();
        parentListener.onModeDisplayed(NewCarFormsContract.Mode.INFO);
        parentListener.onBind(binder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void showProgress(boolean show) {
        parentListener.showProgress(show);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(@StringRes int messageId) {

    }

    @Override
    public void setCarData(CarData carData) {
        makeInput.setText(carData.getMake());
        modelInput.setText(carData.getModel());
        String year = carData.getManufactureYear() == null ? null : String.valueOf(carData.getManufactureYear());
        yearInput.setText(year);
        titleInput.setText(carData.getTitle());
        licenceNumberInput.setText(carData.getLicencePlateNumber());
        seatingCapacityInput.setText(carData.getSeatingCapacity());
        engineCapacityInput.setText(carData.getEngineCapacity());
        String transmissionName = null;
        if (carData.getTransmissionId() != null) {
            transmissionName = NewCarFormsContract.Transmission.getNameById(carData.getTransmissionId());
        }
        transmissionInput.setText(transmissionName);
        String bodyTypeName = null;
        if (carData.getBodyType() != null) {
            bodyTypeName = NewCarFormsContract.BodyType.getNameById(carData.getBodyType());
        }
        bodyTypeInput.setText(bodyTypeName);
    }

    @Override
    public void onFinish() {
        parentListener.onFinish(NewCarFormsContract.Mode.INFO, pendingAction);
    }

    @Override
    public void onNoSavedCar() {

    }
}
