package com.cardee.owner_car_add.presenter;


import android.content.Context;
import android.graphics.Bitmap;

import com.cardee.owner_car_add.view.NewCarFormsContract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CarImagePresenter extends NewCarPresenter implements NewCarFormsContract.Presenter {

    private final static String CAR_PIC_FILE = "car_picture";

    private NewCarFormsContract.View view;

    private Context context;


    public CarImagePresenter(NewCarFormsContract.View view, Context context) {
        super(view);
        this.view = view;
        this.context = context;
    }

    public void saveCarImageToCache(Bitmap bitmap) {
        File cacheDir = context.getCacheDir();
        File f = new File(cacheDir, CAR_PIC_FILE);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCarImageFromCache() {
        File f = new File(context.getCacheDir(), CAR_PIC_FILE);
        return f.exists() ? f : null;
    }
}
