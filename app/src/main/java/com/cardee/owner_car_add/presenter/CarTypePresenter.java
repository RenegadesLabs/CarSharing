package com.cardee.owner_car_add.presenter;


import com.cardee.domain.owner.entity.CarData;
import com.cardee.owner_car_add.view.NewCarFormsContract;

public class CarTypePresenter extends NewCarPresenter {

    private NewCarFormsContract.View view;

    private CarData carData;

    public CarTypePresenter(NewCarFormsContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onCarDataResponse(CarData carData) {
        super.onCarDataResponse(carData);
        this.carData = carData;
    }

    public void saveVehicleType(Integer type) {
        CarData.Builder builder;
        if (carData == null) {
            builder = new CarData.Builder();
        } else {
            builder = carData.newBuilder();
        }
        builder.setVehicleType(type);
        super.saveCar(builder.build(), false);
    }

    @Override
    public void onSaved() {
        if (view != null) {
            view.onFinish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }
}
