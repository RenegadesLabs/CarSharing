package com.cardee.data_source.remote;


import android.net.Uri;
import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.Error;
import com.cardee.data_source.NewCarDataSource;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.cars.Cars;
import com.cardee.data_source.remote.api.cars.Upload;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.cars.response.CreateCarResponse;
import com.cardee.data_source.remote.api.cars.response.UploadImageResponse;
import com.cardee.data_source.remote.validator.NewCarValidator;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RemoteNewCarDataSource implements NewCarDataSource {

    private static final String TAG = RemoteNewCarDataSource.class.getSimpleName();

    private static RemoteNewCarDataSource INSTANCE;
    private final Cars carsApi;
    private final Upload uploadApi;

    private RemoteNewCarDataSource() {
        carsApi = CardeeApp.retrofit.create(Cars.class);
        uploadApi = CardeeApp.retrofitMultipart.create(Upload.class);
    }

    public static RemoteNewCarDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteNewCarDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainSavedCarData(CacheCallback callback) {
        //No implementation need in remote data source
    }

    @Override
    public void saveCarData(NewCarData carData, boolean forcePush, Callback callback) {
        if (forcePush) {
            NewCarValidator validator = new NewCarValidator();
            if (validator.isValid(carData)) {
                Call<CreateCarResponse> request = carsApi.createCar(carData);
                try {
                    Response<CreateCarResponse> response = request.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        Integer carId = response.body().getResponseBody().getCarId();
                        uploadCarImage(carData.getImage(), carId, callback);
                        return;
                    }
                    handleErrorResponse(response.body(), callback);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                    callback.onError(new Error(Error.Type.LOST_CONNECTION, "Internet connection lost"));
                }
            } else {
                callback.onError(new Error(Error.Type.INVALID_REQUEST, "Request body is not valid"));
            }
        }
    }

    private void uploadCarImage(String path, Integer carId, Callback callback) {
        if (path == null || carId == null) {
            callback.onSuccess(carId);
            return;
        }
        File imageFile = new File(path);
        if (imageFile.exists()) {
            MultipartBody.Part part = MultipartBody.Part.createFormData("car_image",
                    imageFile.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), imageFile));
            try {
                Response<UploadImageResponse> response = uploadApi.uploadImage(carId, part).execute();
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Image was not uploaded");
                } else {
                    if (response.body() != null) {
                        makeImagePrimary(carId, response.body().getBody().getImageId(), callback);
                        return;
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        callback.onSuccess(carId);
    }

    private void makeImagePrimary(Integer carId, Integer imageId, Callback callback) {
        if (carId != null && imageId != null) {
            try {
                carsApi.makeImagePrimary(carId, imageId).execute();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        callback.onSuccess(carId);
    }

    @Override
    public void saveCarImage(Uri imgUri, boolean forcePush, ImageCacheCallback callback) {

    }

    private void handleErrorResponse(BaseResponse response, Callback callback) {
        if (response == null) {
            callback.onError(new Error(Error.Type.OTHER, "An error occurred. Please try later"));
            return;
        }
        if (response.getResponseCode() == BaseResponse.ERROR_CODE_INTERNAL_SERVER_ERROR) {
            callback.onError(new Error(Error.Type.SERVER, "Server error"));
        } else if (response.getResponseCode() == BaseResponse.ERROR_CODE_UNAUTHORIZED) {
            callback.onError(new Error(Error.Type.AUTHORIZATION, "Unauthorized"));
        } else {
            callback.onError(new Error(Error.Type.OTHER, "Undefined error"));
            throw new IllegalArgumentException("Unsupported response code: " + response.getResponseCode());
        }
    }
}
