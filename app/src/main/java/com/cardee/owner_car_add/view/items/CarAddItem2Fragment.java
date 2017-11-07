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
import com.cardee.owner_home.view.modal.BodyMenuFragment;
import com.cardee.owner_home.view.modal.PickerMenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CarAddItem2Fragment extends CarAddItemFragment implements CarAddActivity.CarAddActionListener {

    private Unbinder mUnbinder;

    @BindView(R.id.et_addCarInfoMake)
    public AppCompatEditText addCarInfoMakeET;

    @BindView(R.id.et_addCarInfoModel)
    public AppCompatEditText addCarInfoModelET;

    @BindView(R.id.et_addCarInfoYear)
    public AppCompatEditText addCarInfoYearET;

    @BindView(R.id.et_addCarInfoCarTitle)
    public AppCompatEditText addCarInfoCarTitleET;

    @BindView(R.id.et_addCarInfoLicense)
    public AppCompatEditText addCarInfoLicenseET;

    @BindView(R.id.et_addCarInfoSeating)
    public AppCompatEditText addCarInfoSeatingET;

    @BindView(R.id.et_addCarInfoEngine)
    public AppCompatEditText addCarInfoEngineET;

    @BindView(R.id.et_addCarInfoTransmission)
    public AppCompatEditText addCarInfoTransmissionET;

    @BindView(R.id.et_addCarInfoBody)
    public AppCompatEditText addCarInfoBodyET;


    private AppCompatCheckedTextView mLastSelectedBodyType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_car_item2, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        ((CarAddActivity) getActivity()).setActionListener(this);
        return v;
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
        PickerMenuFragment menu = PickerMenuFragment.getInstance(addCarInfoSeatingET.getText().toString(),
                PickerMenuFragment.Mode.SEATING_CAPACITY);
        menu.show(getFragmentManager(), menu.getTag());
        menu.setOnDoneClickListener(new PickerMenuFragment.DialogOnClickListener() {
            @Override
            public void onDoneClicked(String value) {
                addCarInfoSeatingET.setText(value);
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

    }


    @Override
    public void onSaveClicked() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
