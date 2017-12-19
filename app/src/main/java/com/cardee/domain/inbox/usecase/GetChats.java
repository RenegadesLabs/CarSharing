package com.cardee.domain.inbox.usecase;

import com.cardee.data_source.inbox.InboxRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class GetChats implements UseCase<GetChats.RequestValues, GetChats.ResponseValues> {

    private final InboxRepository mRepository;
    private Disposable mDisposable;

    public GetChats() {
        mRepository = InboxRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {
        mDisposable = mRepository.getChats(values.getAttachment())
                .subscribe(
                        inboxChats -> callback.onSuccess(new ResponseValues(inboxChats)),
                        throwable -> {
                        });
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

        private final List<InboxChat> mInboxChats;

        public ResponseValues(List<InboxChat> inboxChats) {
            mInboxChats = inboxChats;
        }

        public List<InboxChat> getInboxChats() {
            return mInboxChats;
        }
    }
}
