package com.cardee.domain.inbox.usecase;

import com.cardee.domain.UseCase;

public class SendChatMessage implements UseCase<SendChatMessage.RequestValues, SendChatMessage.ResponseValues> {

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
