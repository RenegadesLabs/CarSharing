package com.cardee.domain.owner.usecase;

import com.cardee.data_source.Error;
import com.cardee.data_source.OwnerCarDataSource;
import com.cardee.data_source.OwnerCarRepository;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.domain.UseCase;
import com.cardee.domain.owner.entity.RentalDetails;
import com.cardee.domain.owner.entity.mapper.CarResponseToRentalDetailsMapper;

public class GetRentalDetails
        implements UseCase<GetRentalDetails.RequestValues, GetRentalDetails.ResponseValues> {

    private final OwnerCarRepository repository;
    private final CarResponseToRentalDetailsMapper mapper;

    public GetRentalDetails() {
        repository = OwnerCarRepository.getInstance();
        mapper = new CarResponseToRentalDetailsMapper();
    }

    @Override
    public void execute(RequestValues values, final Callback<ResponseValues> callback) {
        int id = values.getId();
        if (id == -1) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid id: " + id));
            return;
        }
        repository.obtainCar(id, new OwnerCarDataSource.Callback() {
            @Override
            public void onSuccess(CarResponseBody carResponse) {
                callback.onSuccess(new ResponseValues(mapper.transfor(carResponse)));
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

        private final RentalDetails rentalDetails;

        public ResponseValues(RentalDetails rentalDetails) {
            this.rentalDetails = rentalDetails;
        }

        public RentalDetails getRentalDetails() {
            return rentalDetails;
        }
    }
}
