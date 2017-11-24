package com.cardee.data_source.remote.api.cars;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.cars.response.CarResponse;
import com.cardee.data_source.remote.api.cars.response.CreateCarResponse;
import com.cardee.data_source.remote.api.cars.response.UploadImageResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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
    Call<BaseResponse> updateDescription(@Path("id") Integer carId, @Body NewCarData requestBody);
}
