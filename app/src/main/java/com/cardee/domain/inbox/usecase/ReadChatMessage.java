package com.cardee.domain.inbox.usecase;

import com.cardee.domain.UseCase;

public class ReadChatMessage implements UseCase<ReadChatMessage.RequestValues, ReadChatMessage.ResponseValues> {

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
