package com.cardee.data_source.remote;

import android.net.Uri;
import android.util.Log;

import com.cardee.CardeeApp;
import com.cardee.data_source.CarEditDataSource;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.NoDataResponse;
import com.cardee.data_source.remote.api.cars.Cars;
import com.cardee.data_source.remote.api.cars.Upload;
import com.cardee.data_source.remote.api.cars.request.CarTitleEntity;
import com.cardee.data_source.remote.api.cars.request.DescriptionBody;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.cars.response.UploadImageResponse;
import com.cardee.data_source.remote.api.cars.response.entity.UploadImageResponseBody;
import com.cardee.data_source.remote.api.common.entity.AcceptCashEntity;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.data_source.remote.api.common.entity.CurbsideDeliveryEntity;
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.data_source.remote.api.common.entity.InstantBookingCount;
import com.cardee.data_source.remote.api.common.entity.InstantBookingEntity;
import com.cardee.data_source.remote.api.common.entity.MinRentalDurationEntity;
import com.cardee.data_source.remote.api.common.entity.RentalRatesEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsAdditionalEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsInsuranceEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsRequirementsEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsSecurityDepositEntity;
import com.cardee.util.ImageProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;


public class RemoteCarEditDataSource implements CarEditDataSource {

    private static final String TAG = RemoteCarEditDataSource.class.getSimpleName();
    private static RemoteCarEditDataSource INSTANCE;

    private final Cars carsApi;
    private final Upload uploadApi;
    private final ImageProcessor imageProcessor;

    private RemoteCarEditDataSource() {
        carsApi = CardeeApp.retrofit.create(Cars.class);
        uploadApi = CardeeApp.retrofitMultipart.create(Upload.class);
        imageProcessor = new ImageProcessor();
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
            Response<BaseResponse> response = carsApi.updateLocation(id, carData).execute();
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
            Response<BaseResponse> response = carsApi.updateInfo(id, carData).execute();
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
    public void updateCarTitle(Integer id, CarTitleEntity title, Callback callback) {
        carsApi.updateCarTitle(id, title).enqueue(new retrofit2.Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                    return;
                }
                handleErrorResponse(response.body(), callback);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                callback.onError(new Error(Error.Type.LOST_CONNECTION, t.getMessage()));
            }
        });
    }

    @Override
    public void updateRentalRequirements(Integer id, RentalTermsRequirementsEntity requirements, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateRentalRequirements(id, requirements).execute();
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
            Response<BaseResponse> response = carsApi.updateRentalRules(id, rules).execute();
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
            Response<BaseResponse> response = carsApi.updateRentalSecurityDeposit(id, securityDeposit).execute();
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
            Response<BaseResponse> response = carsApi.updateRentalInsuranceExcess(id, insuranceExcess).execute();
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
            Response<BaseResponse> response = carsApi.updateRentalAdditional(id, additionalEntity).execute();
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
            Response<BaseResponse> response = carsApi.updateRentalRatesDaily(id, ratesEntity).execute();
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
            Response<BaseResponse> response = carsApi.updateRentalRatesHourly(id, ratesEntity).execute();
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
    public void updateMinRentDaily(Integer id, MinRentalDurationEntity durationEntity, final Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateMinDurationDaily(id, durationEntity).execute();
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
    public void updateMinRentHourly(Integer id, MinRentalDurationEntity durationEntity, final Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateMinDurationHourly(id, durationEntity).execute();
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
            Response<BaseResponse> response = carsApi.updateDescription(id,
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
    public void updateDeliveryRates(Integer id, DeliveryRatesEntity deliveryRatesEntity, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateDeliveryRates(id, deliveryRatesEntity).execute();
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
    public void updateInstantBookingDaily(Integer id, boolean isInstantBooking, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateInstantBookingDaily(id,
                    new InstantBookingEntity(isInstantBooking))
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
    public void updateInstantBookingHourly(Integer id, boolean isInstantBooking, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateInstantBookingHourly(id,
                    new InstantBookingEntity(isInstantBooking))
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
    public void updateInstantBookingDailyCount(Integer id, int count, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.setInstantBookingDailyCount(id,
                    new InstantBookingCount(count))
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
    public void updateInstantBookingHourlyCount(Integer id, int count, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.setInstantBookingHourlyCount(id,
                    new InstantBookingCount(count))
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
    public void updateCurbsideDeliveryDaily(Integer id, boolean isCurbsideDelivery, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateCurbsideDeliveryDaily(id,
                    new CurbsideDeliveryEntity(isCurbsideDelivery))
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
    public void updateCurbsideDeliveryHourly(Integer id, boolean isCurbsideDelivery, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateCurbsideDeliveryHourly(id,
                    new CurbsideDeliveryEntity(isCurbsideDelivery))
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
    public void updateAcceptCashDaily(Integer id, boolean isAcceptCash, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateAcceptCashDaily(id,
                    new AcceptCashEntity(isAcceptCash))
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
    public void updateAcceptCashHourly(Integer id, boolean isAcceptCash, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateAcceptCashHourly(id,
                    new AcceptCashEntity(isAcceptCash))
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
    public void updateFuelPolicyDaily(Integer id, FuelPolicyEntity fuelPolicy, Callback callback) {
        try {
            Response<BaseResponse> response = carsApi.updateFuelPolicyDaily(id, fuelPolicy)
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
            Response<BaseResponse> response = carsApi.updateFuelPolicyHourly(id, fuelPolicy)
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
            InputStream in = CardeeApp.context.getContentResolver().openInputStream(
                    Uri.fromFile(new File(uri.getPath())));
            boolean resized = imageProcessor.resize(in, imageFile);
            if (!resized) {
                throw new Exception("Failed to resize image");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            callback.onError(new Error(Error.Type.INTERNAL, e.getMessage()));
            return;
        }
        if (imageFile.exists()) {
            MultipartBody.Part part = MultipartBody.Part.createFormData("car_image",
                    imageFile.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), imageFile));
            try {
                Response<UploadImageResponse> response = uploadApi.uploadImage(id, part).execute();
                imageFile.deleteOnExit();
                if (response.isSuccessful() && response.body() != null) {
                    UploadImageResponseBody imageResponse = response.body().getBody();
                    if (imageResponse != null && imageResponse.getClass() != null) {
                        callback.onSuccess(imageResponse.getImageId());
                        return;
                    }
                }
                if (response.code() == 400) {
                    callback.onError(new Error(Error.Type.INVALID_REQUEST, "File already exist. Please choose another photo."));
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

    @Override
    public void deleteImage(Integer id, Integer imageId, Callback callback) {
        try {
            Response<NoDataResponse> response = carsApi.deleteImage(id, imageId).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
        }
    }

    @Override
    public void setPrimaryImage(Integer id, Integer imageId, Callback callback) {
        try {
            Response<NoDataResponse> response = carsApi.setPrimaryImage(id, imageId).execute();
            if (response.isSuccessful()) {
                callback.onSuccess();
                return;
            }
            handleErrorResponse(response.body(), callback);
        } catch (IOException e) {
            callback.onError(new Error(Error.Type.LOST_CONNECTION, e.getMessage()));
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
