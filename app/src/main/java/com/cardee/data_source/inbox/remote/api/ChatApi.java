package com.cardee.data_source.inbox.remote.api;

import com.cardee.data_source.inbox.remote.api.model.entity.NewChatMessage;
import com.cardee.data_source.inbox.remote.api.response.ChatListResponse;
import com.cardee.data_source.inbox.remote.api.response.ChatMessagesResponse;
import com.cardee.data_source.inbox.remote.api.response.ChatSingleResponse;
import com.cardee.data_source.inbox.remote.api.response.MessageResponse;
import com.cardee.data_source.remote.api.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ChatApi {

    @GET("/api/dev/chats/{attachment}")
    Single<ChatListResponse> getChats(@Path(value = "attachment") String attachment);

    @GET("/api/dev/chats/{chat_id}/details")
    Single<ChatSingleResponse> getSingleChat(@Path("chat_id") int chatId);

    @GET("/api/dev/chats/{chat_id}")
    Single<ChatMessagesResponse> getMessages(@Path("chat_id") int chatId);

    @POST("/api/dev/chats/{chat_id}")
    Single<MessageResponse> sendMessage(@Path("chat_id") int chatId, @Body NewChatMessage message);

    @PUT("/api/dev/chats/messages/{message_id}")
    Single<BaseResponse> markAsRead(@Path("message_id") int messageId);
}
