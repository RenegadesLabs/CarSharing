package com.cardee.domain.inbox.usecase.alert;

import com.cardee.domain.UseCase;

public class GetAlerts implements UseCase<GetAlerts.RequestValues,GetAlerts.ResponseValues> {

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
