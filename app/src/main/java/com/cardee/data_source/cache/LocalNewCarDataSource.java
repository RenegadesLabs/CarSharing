package com.cardee.data_source.cache;

import android.net.Uri;
import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.NewCarDataSource;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.util.ImageProcessor;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;

public class LocalNewCarDataSource implements NewCarDataSource {

    private static final String TAG = LocalNewCarDataSource.class.getSimpleName();
    private static final String CACHE_FILE_NAME = "cached_new_car.json";
    private static final String PATH_DIVIDER = "/";

    public final static String CAR_PIC_FILE = "car_picture";

    private static LocalNewCarDataSource INSTANCE;

    private final File cacheDir;
    private final ImageProcessor imageProcessor;

    private LocalNewCarDataSource() {
        cacheDir = CardeeApp.context.getCacheDir();
        imageProcessor = new ImageProcessor();
    }

    public static LocalNewCarDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalNewCarDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainSavedCarData(CacheCallback callback) {
        File cacheFile = getCacheFile(false);
        if (cacheFile != null) {
            NewCarData carData = deserializeDataFromFile(cacheFile);
            callback.onSuccess(carData);
            return;
        }
        callback.onError(new Error(Error.Type.INTERNAL, null));
    }

    @Override
    public void saveCarData(NewCarData carData, boolean forcePush, Callback callback) {
        if (forcePush) {
            clearCache();
            return;
        }
        File cacheFile = getCacheFile(true);
        if (cacheFile != null) {
            boolean successful = serializeDataToFile(carData, cacheFile);
            if (callback == null) {
                return;
            }
            if (successful) {
                callback.onSuccess(null);
                return;
            }
            callback.onError(new Error(Error.Type.INTERNAL, null));
        }
    }

    @Override
    public void saveCarImage(Uri imgUri, boolean forcePush, ImageCacheCallback callback) {
        File cacheFile = getCacheFile(true);
        if (cacheFile != null) {
            NewCarData carData = deserializeDataFromFile(cacheFile);
            carData.setImage(saveImageToCache(imgUri));
            boolean successful = serializeDataToFile(carData, cacheFile);
            if (callback == null) {
                return;
            }
            if (successful) {
                callback.onSuccess();
                return;
            }
            callback.onError(new Error(Error.Type.INTERNAL, null));
        }
    }

    private File getCacheFile(boolean forceCreate) {
        if (cacheDir != null && cacheDir.canWrite()) {
            String cacheFilePath = cacheDir.getAbsolutePath() + PATH_DIVIDER + CACHE_FILE_NAME;
            File cacheFile = new File(cacheFilePath);
            if (!cacheFile.exists() && forceCreate) {
                try {
                    boolean successful = cacheFile.createNewFile();
                    if (successful) {
                        return cacheFile;
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            return cacheFile;
        }
        return null;
    }

    private String saveImageToCache(Uri imgUri) {
        String path;
        File imageFile = new File(cacheDir, CAR_PIC_FILE);
        try {
            InputStream in = CardeeApp.context.getContentResolver().openInputStream(imgUri);
            imageProcessor.resize(in, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            path = imageFile.getAbsolutePath();
        }
        return path;
    }


    private boolean serializeDataToFile(NewCarData data, File file) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(json);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    private NewCarData deserializeDataFromFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();
            if (!json.isEmpty()) {
                Gson gson = new Gson();
                return gson.fromJson(json, NewCarData.class);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return new NewCarData();
    }

    private void clearCache() {
        File cacheFile = getCacheFile(false);
        if (cacheFile != null) {
            boolean deleted = cacheFile.delete();
            if (!deleted) {
                Log.e(TAG, "Cache was not deleted");
            }
        }
    }
}
