package com.cardee.owner_car_details.presenter;

import android.net.Uri;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.Image;
import com.cardee.domain.owner.usecase.AddImage;
import com.cardee.domain.owner.usecase.GetCarImages;
import com.cardee.owner_car_details.CarImagesEditContract;


public class CarImagesPresenter implements CarImagesEditContract.Presenter {

    private CarImagesEditContract.View view;
    private final int id;
    private final UseCaseExecutor executor;
    private final GetCarImages getCarImages;
    private final AddImage addImage;

    public CarImagesPresenter(CarImagesEditContract.View view, int id) {
        this.id = id;
        this.view = view;
        executor = UseCaseExecutor.getInstance();
        getCarImages = new GetCarImages();
        addImage = new AddImage();
    }

    @Override
    public void init() {
        executor.execute(getCarImages, new GetCarImages.RequestValues(id),
                new UseCase.Callback<GetCarImages.ResponseValues>() {
                    @Override
                    public void onSuccess(GetCarImages.ResponseValues response) {
                        if (view != null) {
                            view.showProgress(false);
                            view.onImagesObtained(response.getCarImages());
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        if (view != null) {
                            view.showProgress(false);
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
    public void onAddNewImage(Uri uri) {
        view.showProgress(true);
        executor.execute(addImage, new AddImage.RequestValues(id, uri), new UseCase.Callback<AddImage.ResponseValues>() {
            @Override
            public void onSuccess(AddImage.ResponseValues response) {
                Integer imageId = response.getImageId();
                init();
            }

            @Override
            public void onError(Error error) {
                if (view != null) {
                    view.showProgress(false);
                    view.showMessage(error.getMessage());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
