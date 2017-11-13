package com.cardee.owner_car_add.presenter;


import android.content.Context;
import android.net.Uri;

import com.cardee.data_source.Error;
import com.cardee.domain.UseCase;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.domain.owner.entity.CarData;
import com.cardee.domain.owner.usecase.SaveCarImage;
import com.cardee.owner_car_add.view.NewCarFormsContract;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.cardee.data_source.cache.LocalNewCarDataSource.CAR_PIC_FILE;

public class CarImagePresenter implements NewCarFormsContract.Presenter {

    private final SaveCarImage saveImgCase;
    private final UseCaseExecutor executor;
    private NewCarFormsContract.View view;
    private Context context;


    public CarImagePresenter(NewCarFormsContract.View view, Context context) {
        this.view = view;
        this.context = context;
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
                if (view == null)
                    return;
                view.showMessage(error.getMessage());
            }
        });
    }

    private File getCarImageFromCache() {
        File f = new File(context.getCacheDir(), CAR_PIC_FILE);
        return f.exists() ? f : null;
    }

    public byte[] getImageFileInByteArray() {
        int size = (int) getCarImageFromCache().length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(getCarImageFromCache()));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public void init() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onCarDataResponse(CarData carData) {

    }
}
