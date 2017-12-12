package com.cardee.domain.inbox.usecase;

import com.cardee.data_source.inbox.InboxRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class GetChats implements UseCase<GetChats.RequestValues, GetChats.ResponseValues> {

    private final InboxRepository mRepository;
    private Disposable mDisposable;

    public GetChats() {
        mRepository = InboxRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mDisposable = mRepository.getChats()
                .subscribe(
                        inboxChats -> callback.onSuccess(new ResponseValues(inboxChats)),
                        throwable -> {
                        });
    }

    public static class RequestValues implements UseCase.RequestValues {

    }

    public static class ResponseValues implements UseCase.ResponseValues {

        private final List<InboxChat> mInboxChats;

        public ResponseValues(List<InboxChat> inboxChats) {
            mInboxChats = inboxChats;
        }

        public List<InboxChat> getInboxChats() {
            return mInboxChats;
        }
    }
}
