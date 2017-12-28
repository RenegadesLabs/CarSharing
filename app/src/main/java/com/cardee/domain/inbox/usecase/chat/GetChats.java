package com.cardee.domain.inbox.usecase.chat;

import com.cardee.data_source.Error;
import com.cardee.data_source.inbox.local.chat.entity.Chat;
import com.cardee.data_source.inbox.repository.InboxRepository;
import com.cardee.domain.UseCase;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GetChats implements UseCase<GetChats.RequestValues, GetChats.ResponseValues> {

    private final InboxRepository mRepository;
    private CompositeDisposable mCompositeDisposable;

    public GetChats() {
        mCompositeDisposable = new CompositeDisposable();
        mRepository = InboxRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        String attachment = values.getAttachment();
        Disposable localSubscribe = mRepository.getLocalChats(attachment)
                .distinct()
                .subscribe(inboxChats -> callback.onSuccess(new ResponseValues(inboxChats)));

        Disposable remoteSubscribe = mRepository
                .getRemoteChats(attachment)
                .filter(chats -> chats != null && !chats.isEmpty())
                .subscribe(mRepository::fetchOrSaveData,
                        throwable -> callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage())));

        mCompositeDisposable.addAll(localSubscribe, remoteSubscribe);
    }

    public void dispose() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    public static class RequestValues implements UseCase.RequestValues {

        private final String mAttachment;

        public RequestValues(String attachment) {
            mAttachment = attachment;
        }

        public String getAttachment() {
            return mAttachment;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<Chat> mChats;

        public ResponseValues(List<Chat> chats) {
            mChats = chats;
        }

        public List<Chat> getChats() {
            return mChats;
        }
    }
}
