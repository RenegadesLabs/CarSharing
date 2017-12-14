package com.cardee.owner_car_add.presenter;

import android.location.Address;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.domain.owner.usecase.GetCarData;
import com.cardee.domain.owner.usecase.UpdateCarLocation;
import com.cardee.owner_car_add.factory.CarDataFactory;
import com.cardee.owner_car_add.NewCarFormsContract;

public class CarLocationPresenter extends NewCarPresenter {

    private static final Double DEFAULT_LATITUDE = 1.323174;
    private static final Double DEFAULT_LONGITUDE = 103.890894;

    private final UpdateCarLocation updateUsecase;
    private final GetCarData getCarDataUsecase;
    private final UseCaseExecutor executor;

    private NewCarFormsContract.View view;
    private CarData carData;
    private final Integer carId;

    public CarLocationPresenter(NewCarFormsContract.View view, Integer carId) {
        super(view);
        this.view = view;
        this.carId = carId;

        updateUsecase = new UpdateCarLocation();
        getCarDataUsecase = new GetCarData();
        executor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init() {
        if (carId == null) {
            super.init();
            return;
        }
        fetchCarDataById(carId);
    }

    private void fetchCarDataById(Integer carId) {
        GetCarData.RequestValues values = new GetCarData.RequestValues(carId);
        executor.execute(getCarDataUsecase, values, new UseCase.Callback<GetCarData.ResponseValues>() {
            @Override
            public void onSuccess(GetCarData.ResponseValues response) {
                onCarDataResponse(response.getCarData());
            }

            @Override
            public void onError(Error error) {
                //TODO: implement
            }
        });
    }

    private void setDefaultLocation() {
        if (view != null) {
            view.setCarData(new CarData.Builder()
                    .setLatitude(DEFAULT_LATITUDE)
                    .setLongitude(DEFAULT_LONGITUDE)
                    .build());
        }
    }

    public void saveLocation(Address address) {
        if (address == null) {
            return;
        }
        CarDataFactory factory = new CarDataFactory();
        carData = factory.from(carData, address);
        if (carId == null) {
            super.saveCar(carData, false);
            return;
        }
        updateCarLocation(carId, carData);
    }

    private void updateCarLocation(Integer carId, CarData carData) {
        UpdateCarLocation.RequestValues values = new UpdateCarLocation.RequestValues(carId, carData);
        executor.execute(updateUsecase, values, new UseCase.Callback<UpdateCarLocation.ResponseValues>() {
            @Override
            public void onSuccess(UpdateCarLocation.ResponseValues response) {
                if (view != null) {
                    view.onFinish();
                }
            }

            @Override
            public void onError(Error error) {
                //TODO: implement
            }
        });
    }

    @Override
    public void onCarDataResponse(CarData carData) {
        if (view == null) {
            return;
        }
        this.carData = carData;
        if (carData.getLatitude() == null || carData.getLongitude() == null) {
            setDefaultLocation();
            return;
        }
        view.setCarData(carData);
    }

    @Override
    public void onSaved() {
        if (view != null) {
            view.onFinish();
        }
    }
}
