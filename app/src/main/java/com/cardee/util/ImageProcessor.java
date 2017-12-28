package com.cardee.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.annotations.NonNull;

public class ImageProcessor {

    private static final String TAG = ImageProcessor.class.getSimpleName();

    public static final int MAX_IMAGE_WIDTH = 1920;
    public static final int MAX_IMAGE_HEIGHT = 1080;

    public ImageProcessor() {

    }

    public boolean resize(InputStream src, @NonNull File dst) {
        if (dst == null) {
            throw new IllegalArgumentException("Invalid dst: null");
        }
        if (!dst.exists()) {
            try {
                boolean created = dst.createNewFile();
                if (!created) {
                    return false;
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
        }
        Bitmap srcBitmap = BitmapFactory.decodeStream(src);
        Size newSize = calculateSize(srcBitmap.getWidth(), srcBitmap.getHeight());
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(srcBitmap, newSize.width, newSize.height, false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        try {
            Log.i(TAG, "Source size: " + srcBitmap.getWidth() + "x" + srcBitmap.getHeight());
            FileOutputStream fileOutputStream = new FileOutputStream(dst);
            fileOutputStream.write(outputStream.toByteArray());
            fileOutputStream.close();
            Log.i(TAG, "New size: " + newSize.getWidth() + "x" + newSize.getHeight());
            Log.i(TAG, "Bytes: " + dst.length());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } finally {
            srcBitmap.recycle();
            resizedBitmap.recycle();
        }
        return true;
    }

    private Size calculateSize(int srcWidth, int srcHeight) {
        if (srcWidth == 0 || srcHeight == 0) {
            throw new IllegalArgumentException("Illegal srcWidth: " + srcWidth + " or srcHeight: " + srcHeight);
        }
        if (srcWidth <= MAX_IMAGE_WIDTH && srcHeight <= MAX_IMAGE_HEIGHT) {
            return new Size(srcWidth, srcHeight);
        }
        float aspectRatio = (float) srcWidth / srcHeight;
        float baseAspectRatio = (float) MAX_IMAGE_WIDTH / MAX_IMAGE_HEIGHT;
        if (aspectRatio > baseAspectRatio) {
            int resizedHeight = (int) (MAX_IMAGE_WIDTH / aspectRatio);
            return new Size(MAX_IMAGE_WIDTH, resizedHeight);
        } else {
            int resizedWidth = (int) (MAX_IMAGE_HEIGHT * aspectRatio);
            return new Size(resizedWidth, MAX_IMAGE_HEIGHT);
        }
    }

    private class Size {
        private final int width;
        private final int height;

        Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
