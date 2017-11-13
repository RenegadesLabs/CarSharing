package com.cardee.owner_car_add.presenter;


import android.net.Uri;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.usecase.SaveCarImage;
import com.cardee.owner_car_add.view.NewCarFormsContract;

public class CarImagePresenter extends NewCarPresenter {

    private final SaveCarImage saveImgCase;
    private final UseCaseExecutor executor;
    private NewCarFormsContract.View view;

    public CarImagePresenter(NewCarFormsContract.View view) {
        super(view);
        this.view = view;
        saveImgCase = new SaveCarImage();
        executor = UseCaseExecutor.getInstance();
    }


    public void saveCarImageToCache(Uri imgUri) {
        executor.execute(saveImgCase, new SaveCarImage.RequestValues(imgUri), new UseCase.Callback<SaveCarImage.ResponseValues>() {
            @Override
            public void onSuccess(SaveCarImage.ResponseValues response) {

            }

            @Override
            public void onError(Error error) {
                if (view == null) {
                    return;
                }
                view.showMessage(error.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
