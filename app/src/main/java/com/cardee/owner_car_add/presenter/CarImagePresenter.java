package com.cardee.owner_car_add.presenter;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.cardee.owner_car_add.view.NewCarFormsContract;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class CarImagePresenter extends NewCarPresenter implements NewCarFormsContract.Presenter {

    private final static String CAR_PIC_FILE = "car_picture";

    private NewCarFormsContract.View view;

    private Context context;


    public CarImagePresenter(NewCarFormsContract.View view, Context context) {
        super(view);
        this.view = view;
        this.context = context;
    }


    public CarImagePresenter saveCarImageToCache(Uri imgUri) {
        byte[] imageData = new byte[1024];
        File f = new File(context.getCacheDir(), CAR_PIC_FILE);
        try {
            InputStream in = context.getContentResolver().openInputStream(imgUri);
            OutputStream out = new FileOutputStream(f);
            int bytesRead;
            while ((bytesRead = in.read(imageData)) > 0) {
                out.write(Arrays.copyOfRange(imageData, 0, Math.max(0, bytesRead)));
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public File getCarImageFromCache() {
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

}
