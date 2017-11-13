package com.cardee.owner_car_add.view.items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cardee.owner_car_add.view.NewCarFormsContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CarPresenterImpl implements NewCarFormsContract.Presenter {

    private final static String CAR_PIC_FILE = "car_picture";

    private Context mCtx;

    @Override
    public void init() {

    }

    public CarPresenterImpl(Context context) {
        mCtx = context;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void saveCarImageToCache(Bitmap bitmap) {
        File cacheDir = mCtx.getCacheDir();
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

    @Override
    public File getCarImageFromCache() {
        File f = new File(mCtx.getCacheDir(), CAR_PIC_FILE);
        return f.exists() ? f : null;
    }
}
