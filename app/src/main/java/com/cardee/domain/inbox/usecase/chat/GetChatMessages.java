package com.cardee.domain.inbox.usecase.chat;

import com.cardee.data_source.Error;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.data_source.inbox.repository.ChatRepository;
import com.cardee.domain.UseCase;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class GetChatMessages implements UseCase<GetChatMessages.RequestValues, GetChatMessages.ResponseValues> {

    private final ChatRepository mRepository;
    private Disposable mDisposable;

    public GetChatMessages() {
        mRepository = ChatRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mDisposable = mRepository.getLocalMessages()
                .distinct()
                .subscribe(
                        chatMessages -> callback.onSuccess(new ResponseValues(chatMessages)),
                        throwable -> callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage())));
    }

    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public static class RequestValues implements UseCase.RequestValues {

        public RequestValues() {

        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<ChatMessage> mMessageList;

        public ResponseValues(List<ChatMessage> messageList) {
            mMessageList = messageList;
        }

        public List<ChatMessage> getMessageList() {
            return mMessageList;
        }
    }
}
