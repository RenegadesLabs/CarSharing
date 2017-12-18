package com.cardee.domain.renter.usecase;


import com.cardee.data_source.Error;
import com.cardee.data_source.RenterProfileDataSource;
import com.cardee.data_source.remote.RemoteRenterProfileDataSource;
import com.cardee.data_source.remote.api.profile.request.ChangeNoteRequest;
import com.cardee.domain.UseCase;

public class ChangeNote implements UseCase<ChangeNote.RequestValues, ChangeNote.ResponseValues> {

    private final RenterProfileDataSource mRepository;

    public ChangeNote() {
        mRepository = RemoteRenterProfileDataSource.getInstance();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        mRepository.changeRenterNote(values.getNewNote(), new RenterProfileDataSource.NoResponseCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess(new ResponseValues());
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        ChangeNoteRequest mNoteRequest;

        public RequestValues(ChangeNoteRequest changeNoteRequest) {
            mNoteRequest = changeNoteRequest;
        }

        public ChangeNoteRequest getNewNote() {
            return mNoteRequest;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
    }
}
