package com.cardee.owner_car_add.presenter;

import android.util.Log;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.domain.owner.usecase.GetSavedCar;
import com.cardee.domain.owner.usecase.SaveCar;
import com.cardee.owner_car_add.view.NewCarFormsContract;


public class NewCarPresenter implements NewCarFormsContract.Presenter {

    private static final String TAG = NewCarPresenter.class.getSimpleName();

    private final SaveCar saveCarTask;
    private final GetSavedCar getSavedCarTask;
    private final UseCaseExecutor executor;

    private NewCarFormsContract.View view;

    public NewCarPresenter(NewCarFormsContract.View view) {
        this.view = view;
        saveCarTask = new SaveCar();
        getSavedCarTask = new GetSavedCar();
        executor = UseCaseExecutor.getInstance();
    }

    @Override
    public void init() {
        executor.execute(getSavedCarTask, null, new UseCase.Callback<GetSavedCar.ResponseValues>() {
            @Override
            public void onSuccess(GetSavedCar.ResponseValues response) {
                onCarDataResponse(response.getCarData());
            }

            @Override
            public void onError(Error error) {
                Log.e(TAG, "Error: " + error.getErrorType() + " with message: " + error.getMessage());
                //TODO implement
            }
        });
    }

    @Override
    public void onCarDataResponse(CarData carData) {
        if (carData != null && view != null) {
            view.setCarData(carData);
        }
    }

    public void saveCar(CarData carData, boolean completed) {
        SaveCar.RequestValues values = new SaveCar.RequestValues(carData, completed);
        executor.execute(saveCarTask, values, new UseCase.Callback<SaveCar.ResponseValues>() {
            @Override
            public void onSuccess(SaveCar.ResponseValues response) {
                Log.d(TAG, "Car saved with ID=" + response.getNewCarId() + "(null ID means that car saved locally)");
                onSaved();
            }

            @Override
            public void onError(Error error) {
                Log.e(TAG, "Error: " + error.getErrorType() + " with message: " + error.getMessage());
                //TODO implement
            }
        });
    }

    public void onSaved() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
