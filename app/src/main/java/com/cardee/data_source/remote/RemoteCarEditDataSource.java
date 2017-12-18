package com.cardee.data_source.remote;

import android.net.Uri;
import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.cars.Cars;
import com.cardee.data_source.remote.api.cars.request.DescriptionBody;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.cars.response.UploadImageResponse;
import com.cardee.data_source.remote.api.cars.response.entity.UploadImageResponseBody;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.data_source.remote.api.common.entity.RentalRatesEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsAdditionalEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsInsuranceEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsRequirementsEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsSecurityDepositEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import retrofit2.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;


public class RemoteCarEditDataSource implements CarEditDataSource {

    private static final String TAG = RemoteCarEditDataSource.class.getSimpleName();
    private static RemoteCarEditDataSource INSTANCE;

    private final Cars api;

    private RemoteCarEditDataSource() {
        api = CardeeApp.retrofit.create(Cars.class);
    }

    public static RemoteCarEditDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteCarEditDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void updateLocation(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {
        try {
            Response<BaseResponse> response = api.updateLocation(id, carData).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateInfo(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {
        try {
            Response<BaseResponse> response = api.updateInfo(id, carData).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateRentalRequirements(Integer id, RentalTermsRequirementsEntity requirements, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateRentalRequirements(id, requirements).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateRentalRules(Integer id, CarRuleEntity rules, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateRentalRules(id, rules).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateRentalSecurityDeposit(Integer id, RentalTermsSecurityDepositEntity securityDeposit, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateRentalSecurityDeposit(id, securityDeposit).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateRentalInsuranceExcess(Integer id, RentalTermsInsuranceEntity insuranceExcess, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateRentalInsuranceExcess(id, insuranceExcess).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateRentalAdditionalTerms(Integer id, RentalTermsAdditionalEntity additionalEntity, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateRentalAdditional(id, additionalEntity).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateRentalRatesDaily(Integer id, RentalRatesEntity ratesEntity, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateRentalRatesDaily(id, ratesEntity).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateRentalRatesHourly(Integer id, RentalRatesEntity ratesEntity, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateRentalRatesHourly(id, ratesEntity).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateDescription(Integer id, String description, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateDescription(id,
                    new DescriptionBody(description)).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateFuelPolicyDaily(Integer id, FuelPolicyEntity fuelPolicy, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateFuelPolicyDaily(id, fuelPolicy)
                    .execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void updateFuelPolicyHourly(Integer id, FuelPolicyEntity fuelPolicy, Callback callback) {
        try {
            Response<BaseResponse> response = api.updateFuelPolicyHourly(id, fuelPolicy)
                    .execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void uploadImage(Integer id, Uri uri, ImageCallback callback) {
        if (uri == null || id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id + " or URI: " + uri));
            return;
        }
        String[] split = uri.getPath().split("/");
        File imageFile = new File(CardeeApp.context.getCacheDir(), split[split.length - 1]);
        try {
            byte[] buffer = new byte[1024];
            InputStream in = CardeeApp.context.getContentResolver().openInputStream(uri);
            OutputStream out = new FileOutputStream(imageFile);
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(Arrays.copyOfRange(buffer, 0, Math.max(0, bytesRead)));
            }
            in.close();
            out.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.INTERNAL, e.getMessage()));
            return;
        }
        if (imageFile.exists()) {
            MultipartBody.Part part = MultipartBody.Part.createFormData("car_image",
                    imageFile.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), imageFile));
            try {
                Response<UploadImageResponse> response = api.uploadImage(id, part).execute();
                imageFile.deleteOnExit();
                if (response.isSuccessful() && response.body() != null) {
                    UploadImageResponseBody imageResponse = response.body().getBody();
                    if (imageResponse != null && imageResponse.getClass() != null) {
                        callback.onSuccess(imageResponse.getImageId());
                        return;
                    }
                }
                if(response.code() == 400){
                    callback.onError(new Error(Error.Type.INVALID_REQUEST, "File already exist. Please select choose photo."));
                    return;
                }
                handleErrorResponse(response.body(), callback);
            } catch (IOException e) {
                callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
            }
        } else {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid File path: " + imageFile.getAbsolutePath()));
        }
    }

    private void handleErrorResponse(BaseResponse response, CarEditDataSource.Callback callback) {
        if (response == null) {
            callback.onError(new Error(Error.Type.OTHER, "null"));
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
