package com.cardee.domain.bookings.usecase;

import com.cardee.data_source.BookingDataSource;
import com.cardee.data_source.BookingRepository;
import com.cardee.data_source.Error;
import com.cardee.data_source.remote.api.booking.response.entity.ChecklistEntity;
import com.cardee.domain.UseCase;
import com.cardee.domain.bookings.entity.Checklist;
import com.cardee.domain.bookings.entity.mapper.ChecklistEntityToChecklistMapper;


public class GetChecklist implements UseCase<GetChecklist.RequestValues, GetChecklist.ResponseValues> {

    private BookingRepository mRepository;

    public GetChecklist() {
        mRepository = BookingRepository.getInstance();
    }

    @Override
    public void execute(RequestValues values, Callback<ResponseValues> callback) {

        mRepository.getChecklist(values.getId(), new BookingDataSource.ChecklistCallback() {
            @Override
            public void onSuccess(ChecklistEntity checklist) {
                callback.onSuccess(new ResponseValues(true,
                        ChecklistEntityToChecklistMapper.transform(checklist)));
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });

    }

    public static class RequestValues implements UseCase.RequestValues {

        private final int id;

        public RequestValues(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean success;
        private final Checklist mChecklist;


        public ResponseValues(boolean success, Checklist checklist) {
            this.success = success;
            this.mChecklist = checklist;

        }

        public boolean isSuccess() {
            return success;
        }

        public Checklist getChecklist() {
            return mChecklist;
        }
    }
}
