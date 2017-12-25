package com.cardee.domain.inbox.usecase.chat;

import android.content.SharedPreferences;

import com.cardee.data_source.Error;
import com.cardee.data_source.inbox.InboxRepository;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.UseCase;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GetChats implements UseCase<GetChats.RequestValues, GetChats.ResponseValues> {

    private final InboxRepository mRepository;
    private Disposable mDisposable;
    private SharedPreferences mSharedPreferences;
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

        Disposable remoteSubscribe = mRepository.getRemoteChats(attachment)
                .doOnError(throwable -> callback.onError(new Error(Error.Type.LOST_CONNECTION, throwable.getMessage())))
                .subscribe();

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
