package com.cardee.data_source.remote.api.reference;


import com.cardee.data_source.remote.api.reference.response.BodyTypesResponse;
import com.cardee.data_source.remote.api.reference.response.TransmissionTypesResponse;
import com.cardee.data_source.remote.api.reference.response.VehicleTypesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface References {

    @GET("references/types")
    Call<VehicleTypesResponse> getVehicleTypes();

    @GET("references/transmissions")
    Call<TransmissionTypesResponse> getTransmissionTypes();

    @GET("references/bodies")
    Call<BodyTypesResponse> getBodyTypes();
}
