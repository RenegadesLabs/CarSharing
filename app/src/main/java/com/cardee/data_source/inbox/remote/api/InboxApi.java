package com.cardee.data_source.inbox.remote.api;

import com.cardee.data_source.inbox.remote.api.request.NewChatMessage;
import com.cardee.data_source.inbox.remote.api.response.ChatListResponse;
import com.cardee.data_source.inbox.remote.api.response.ChatMessagesResponse;
import com.cardee.data_source.inbox.remote.api.response.MessageResponse;
import com.cardee.data_source.remote.api.auth.request.PushRequest;
import com.cardee.data_source.remote.api.auth.response.BaseAuthResponse;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InboxApi {

    @GET("/chats/")
    Single<ChatListResponse> getChats();

    @GET("/chats/{chat_id}")
    Observable<ChatMessagesResponse> getMessages(@Path("chat_id") int chatId);

    @POST
    @Headers("Content-Type: application/json")
    Single<MessageResponse> sendMessage(@Body NewChatMessage message);

    @PUT("/chats/{message_id}")
    Single<MessageResponse> markAsRead(@Path("message_id") int messageId);
}
