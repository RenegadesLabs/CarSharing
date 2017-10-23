package com.cardee.domain.owner.usecase;

import com.cardee.domain.UseCase;

public class GetCars implements UseCase<GetCars.RequestValues, GetCars.ResponseValues> {

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
