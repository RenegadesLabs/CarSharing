package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.RentalDataSource;
import com.cardee.data_source.RentalRepository;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.domain.owner.entity.mapper.RentalDetailsToRequestMapper;

public class SaveDailyRental
        implements UseCase<SaveDailyRental.RequestValues, SaveDailyRental.ResponseValues> {

    private final RentalRepository repository;

    public SaveDailyRental() {
        repository = RentalRepository.getInstance();
    }

    @Override
    public void execute(RequestValues request, final Callback<ResponseValues> callback) {
        if (request.getRentalDetails() == null || request.getRentalDetails().getCarId() == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " +
                    request.getRentalDetails().getCarId() + " or RentalDetails: " +
                    request.getRentalDetails()));
            return;
        }
        int id = request.getRentalDetails().getCarId();
        repository.saveDailyRentalDetail(id,
                new RentalDetailsToRequestMapper().transform(request.getRentalDetails()),
                new RentalDataSource.Callback() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess(new ResponseValues(true));
                    }

                    @Override
                    public void onError(Error error) {
                        callback.onError(error);
                    }
                });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final RentalDetails rentalDetails;

        public RequestValues(RentalDetails rentalDetails) {
            this.rentalDetails = rentalDetails;
        }

        public RentalDetails getRentalDetails() {
            return rentalDetails;
        }
    }

    public static class ResponseValues implements UseCase.ResponseValues {
        private final boolean successful;

        public ResponseValues(boolean successful) {
            this.successful = successful;
        }

        public boolean isSuccessful() {
            return successful;
        }
    }
}
