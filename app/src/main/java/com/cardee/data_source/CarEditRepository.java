package com.cardee.data_source;


import android.net.Uri;

import com.cardee.data_source.cache.LocalCarEditDataSource;
import com.cardee.data_source.remote.RemoteCarEditDataSource;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.data_source.remote.api.common.entity.RentalRatesEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsAdditionalEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsInsuranceEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsRequirementsEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsSecurityDepositEntity;

public class CarEditRepository implements CarEditDataSource {

    private static CarEditRepository INSTANCE;

    private final CarEditDataSource localDataSource;
    private final CarEditDataSource remoteDataSource;

    private CarEditRepository() {
        localDataSource = LocalCarEditDataSource.getInstance();
        remoteDataSource = RemoteCarEditDataSource.getInstance();
    }

    public static CarEditRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarEditRepository();
        }
        return INSTANCE;
    }

    @Override
    public void updateLocation(final Integer id, NewCarData carData, final CarEditDataSource.Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateLocation(id, carData, new Callback() {
            @Override
            public void onSuccess() {
                OwnerCarRepository.getInstance().refresh(id);
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateInfo(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateInfo(id, carData, callback);
    }

    @Override
    public void updateRentalRequirements(Integer id, RentalTermsRequirementsEntity requirements, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateRentalRequirements(id, requirements, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateRentalRules(Integer id, CarRuleEntity rules, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateRentalRules(id, rules, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateRentalSecurityDeposit(Integer id, RentalTermsSecurityDepositEntity securityDeposit, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateRentalSecurityDeposit(id, securityDeposit, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateRentalInsuranceExcess(Integer id, RentalTermsInsuranceEntity insuranceExcess, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateRentalInsuranceExcess(id, insuranceExcess, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateRentalAdditionalTerms(Integer id, RentalTermsAdditionalEntity additional, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateRentalAdditionalTerms(id, additional, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateRentalRatesDaily(Integer id, RentalRatesEntity ratesEntity, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateRentalRatesDaily(id, ratesEntity, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateRentalRatesHourly(Integer id, RentalRatesEntity ratesEntity, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateRentalRatesDaily(id, ratesEntity, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateDescription(final Integer id, final String description, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateDescription(id, description, new Callback() {
            @Override
            public void onSuccess() {
                CarResponseBody car = OwnerCarRepository.getInstance().getCachedCar(id);
                if (car != null) {
                    car.getCarDetails().setDescription(description);
                }
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void uploadImage(final Integer id, Uri uri, final ImageCallback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.uploadImage(id, uri, new ImageCallback() {
            @Override
            public void onSuccess(int imageId) {
                OwnerCarRepository.getInstance().refresh(id);
                OwnerCarsRepository.getInstance().refreshCars();
                callback.onSuccess(imageId);
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }
}
