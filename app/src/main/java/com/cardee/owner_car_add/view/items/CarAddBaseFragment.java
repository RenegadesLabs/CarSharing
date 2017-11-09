package com.cardee.owner_car_add.view.items;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cardee.domain.owner.entity.Car;
import com.cardee.owner_car_add.view.CarAddActivity;
import com.cardee.owner_car_add.view.CarAddView;

public abstract class CarAddBaseFragment extends Fragment {

    public final static String FRAGMENT_NUMBER = "fragment_number";
    public final static String FRAGMENT_VALUE = "fragment_value";

    abstract void saveArguments(Bundle b, boolean onNext);

    public abstract void setPassDataCallback(CarAddActivity.CarInfoPassCallback callback);

    public abstract void setViewListener(CarAddView listener);
}
