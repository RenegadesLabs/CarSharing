package com.cardee.domain.inbox.usecase;

import com.cardee.domain.UseCase;

public class GetChats implements UseCase<GetChats.RequestValues, GetChats.ResponseValues> {

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
