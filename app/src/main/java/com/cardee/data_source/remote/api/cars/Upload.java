package com.cardee.data_source.remote.api.cars;


import com.cardee.data_source.remote.api.cars.response.UploadImageResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Upload {

    @Multipart
    @PUT("cars/{id}/images")
    Call<UploadImageResponse> uploadImage(@Path("id") Integer carId, @Part MultipartBody.Part picture);

}
