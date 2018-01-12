package com.cardee.data_source.remote.api.booking;

import com.cardee.data_source.remote.api.booking.response.UploadImageResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Upload {

    @Multipart
    @PUT("bookings/{id}/checklist")
    Call<UploadImageResponse> uploadImage(@Path("id") int id, @Part MultipartBody.Part picture);
}
