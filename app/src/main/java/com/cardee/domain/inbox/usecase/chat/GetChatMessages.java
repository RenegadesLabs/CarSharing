package com.cardee.domain.inbox.usecase.chat;

import com.cardee.domain.UseCase;

public class GetChatMessages implements UseCase<GetChatMessages.RequestValues, GetChatMessages.ResponseValues> {

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValues implements UseCase.ResponseValues {

    }
}
