package com.cardee.owner_car_details.presenter;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.Image;
import com.cardee.domain.owner.usecase.GetCarImages;
import com.cardee.owner_car_details.CarImagesEditContract;


public class CarImagesPresenter implements CarImagesEditContract.Presenter {

    private CarImagesEditContract.View view;
    private final int id;
    private final UseCaseExecutor executor;
    private final GetCarImages getCarImages;

    public CarImagesPresenter(CarImagesEditContract.View view, int id) {
        this.id = id;
        this.view = view;
        executor = UseCaseExecutor.getInstance();
        getCarImages = new GetCarImages();
    }

    @Override
    public void init() {
        executor.execute(getCarImages, new GetCarImages.RequestValues(id),
                new UseCase.Callback<GetCarImages.ResponseValues>() {
                    @Override
                    public void onSuccess(GetCarImages.ResponseValues response) {
                        if (view != null) {
                            view.onImagesObtained(response.getCarImages());
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.showMessage(error.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onImageSetPrimary(Image image) {

    }

    @Override
    public void onImageRemove(Image image) {

    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
