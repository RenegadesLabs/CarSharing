package com.cardee.owner_car_add.presenter;

import android.util.Log;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.GetCarDescription;
import com.cardee.domain.owner.usecase.SaveCarDescription;
import com.cardee.mvp.BasePresenter;
import com.cardee.owner_car_add.view.CarDescriptionView;

public class CarDescriptionPresenter implements BasePresenter {

    private static final String TAG = CarDescriptionPresenter.class.getSimpleName();
    private CarDescriptionView view;
    private final int carId;

    private final UseCaseExecutor executor;
    private final GetCarDescription getDescription;
    private final SaveCarDescription saveDescription;

    public CarDescriptionPresenter(CarDescriptionView view, int carId) {
        this.view = view;
        this.carId = carId;
        executor = UseCaseExecutor.getInstance();
        getDescription = new GetCarDescription();
        saveDescription = new SaveCarDescription();
    }

    @Override
    public void init() {
        executor.execute(getDescription, new GetCarDescription.RequestValues(carId),
                new UseCase.Callback<GetCarDescription.ResponseValues>() {
                    @Override
                    public void onSuccess(GetCarDescription.ResponseValues response) {
                        if (view != null) {
                            view.onDescriptionObtained(response.getCarDescription());
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        Log.e(TAG, error.getMessage());
                    }
                });
    }

    public void onSave(String description) {
        view.showProgress(true);
        executor.execute(saveDescription, new SaveCarDescription.RequestValues(carId, description),
                new UseCase.Callback<SaveCarDescription.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveCarDescription.ResponseValues response) {
                        if (response.isSuccessful() && view != null) {
                            view.onDescriptionSaved();
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.showProgress(false);
                        }
                        Log.e(TAG, error.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
