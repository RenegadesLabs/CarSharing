package com.cardee.owner_car_add.presenter;


import com.cardee.domain.owner.entity.NewCar;
import com.cardee.owner_car_add.view.NewCarFormsContract;

public class CarTypePresenter extends NewCarPresenter {

    private NewCarFormsContract.View view;

    private NewCar carData;

    public CarTypePresenter(NewCarFormsContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onCarDataResponse(NewCar carData) {
        super.onCarDataResponse(carData);
        this.carData = carData;
    }

    public void saveVehicleType(Integer type) {
        NewCar.Builder builder;
        if (carData == null) {
            builder = new NewCar.Builder();
        } else {
            builder = carData.newBuilder();
        }
        builder.setVehicleType(type);
        super.saveCar(carData, false);
    }

    @Override
    public void onSaved() {
        if(view!=null){
            view.onFinish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }
}
