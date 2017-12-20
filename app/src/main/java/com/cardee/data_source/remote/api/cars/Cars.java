package com.cardee.data_source.remote.api.cars;

import android.content.Intent;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.cars.request.DescriptionBody;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.cars.response.CarResponse;
import com.cardee.data_source.remote.api.cars.response.CreateCarResponse;
import com.cardee.data_source.remote.api.cars.response.UploadImageResponse;
import com.cardee.data_source.remote.api.common.entity.AcceptCashEntity;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.data_source.remote.api.common.entity.CurbsideDeliveryEntity;
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.data_source.remote.api.common.entity.InstantBookingEntity;
import com.cardee.data_source.remote.api.common.entity.RentalRatesEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsAdditionalEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsInsuranceEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsRequirementsEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsSecurityDepositEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Cars {

    @GET("cars/{id}")
    Call<CarResponse> getCar(@Path("id") int id);

    @POST("cars")
    Call<CreateCarResponse> createCar(@Body NewCarData requestBody);

    @Multipart
    @PUT("cars/{id}/images")
    Call<UploadImageResponse> uploadImage(@Path("id") Integer carId, @Part MultipartBody.Part picture);

    @PUT("cars/{id}/images/{img_id}/primary")
    Call<BaseResponse> makeImagePrimary(@Path("id") Integer carId, @Path("img_id") Integer imgId);

    @PUT("cars/{id}/location")
    Call<BaseResponse> updateLocation(@Path("id") Integer carId, @Body NewCarData requestBody);

    @PUT("cars/{id}/information")
    Call<BaseResponse> updateInfo(@Path("id") Integer carId, @Body NewCarData requestBody);

    @PUT("cars/{id}/description")
    Call<BaseResponse> updateDescription(@Path("id") Integer carId, @Body DescriptionBody description);

    @PUT("cars/{id}/delivery/rates")
    Call<BaseResponse> updateDeliveryRates(@Path("id") Integer carId, @Body DeliveryRatesEntity requestBody);

    @PUT("cars/{id}/rental/terms")
    Call<BaseResponse> updateRentalRequirements(@Path("id") Integer carId, @Body RentalTermsRequirementsEntity requestBody);

    @PUT("cars/{id}/rental/rules")
    Call<BaseResponse> updateRentalRules(@Path("id") Integer carId, @Body CarRuleEntity requestBody);

    @PUT("cars/{id}/rental/terms")
    Call<BaseResponse> updateRentalSecurityDeposit(@Path("id") Integer carId, @Body RentalTermsSecurityDepositEntity requestBody);

    @PUT("cars/{id}/rental/terms")
    Call<BaseResponse> updateRentalInsuranceExcess(@Path("id") Integer carId, @Body RentalTermsInsuranceEntity requestBody);

    @PUT("cars/{id}/rental/terms")
    Call<BaseResponse> updateRentalAdditional(@Path("id") Integer carId, @Body RentalTermsAdditionalEntity requestBody);

    @PUT("cars/{id}/rental/daily")
    Call<BaseResponse> updateRentalRatesDaily(@Path("id") Integer carId, @Body RentalRatesEntity requestBody);

    @PUT("cars/{id}/rental/hourly")
    Call<BaseResponse> updateRentalRatesHourly(@Path("id") Integer carId, @Body RentalRatesEntity requestBody);

    @PUT("cars/{id}/rental/daily")
    Call<BaseResponse> updateFuelPolicyDaily(@Path("id") Integer carId, @Body FuelPolicyEntity requestBody);

    @PUT("cars/{id}/rental/hourly")
    Call<BaseResponse> updateFuelPolicyHourly(@Path("id") Integer carId, @Body FuelPolicyEntity requestBody);

    @PUT("cars/{id}/rental/daily")
    Call<BaseResponse> updateInstantBookingDaily(@Path("id") Integer carId, @Body InstantBookingEntity requestBody);

    @PUT("cars/{id}/rental/hourly")
    Call<BaseResponse> updateInstantBookingHourly(@Path("id") Integer carId, @Body InstantBookingEntity requestBody);

    @PUT("cars/{id}/rental/daily")
    Call<BaseResponse> updateCurbsideDeliveryDaily(@Path("id") Integer carId, @Body CurbsideDeliveryEntity requestBody);

    @PUT("cars/{id}/rental/hourly")
    Call<BaseResponse> updateCurbsideDeliveryHourly(@Path("id") Integer carId, @Body CurbsideDeliveryEntity requestBody);

    @PUT("cars/{id}/rental/daily")
    Call<BaseResponse> updateAcceptCashDaily(@Path("id") Integer carId, @Body AcceptCashEntity requestBody);

    @PUT("cars/{id}/rental/hourly")
    Call<BaseResponse> updateAcceptCashHourly(@Path("id") Integer carId, @Body AcceptCashEntity requestBody);

}

