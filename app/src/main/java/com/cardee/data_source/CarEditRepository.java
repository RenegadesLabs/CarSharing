package com.cardee.data_source;


import android.net.Uri;

import com.cardee.data_source.cache.LocalCarEditDataSource;
import com.cardee.data_source.remote.RemoteCarEditDataSource;
import com.cardee.data_source.remote.api.cars.request.CarTitleEntity;
import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.cars.response.CarResponseBody;
import com.cardee.data_source.remote.api.common.entity.BaseCarEntity;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.data_source.remote.api.common.entity.MinRentalDurationEntity;
import com.cardee.data_source.remote.api.common.entity.RentalRatesEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsAdditionalEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsInsuranceEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsRequirementsEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsSecurityDepositEntity;
import com.cardee.data_source.remote.api.profile.response.entity.CarEntity;

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
    public void updateLocation(final Integer id, final NewCarData carData, final CarEditDataSource.Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateLocation(id, carData, new Callback() {
            @Override
            public void onSuccess() {
                CarResponseBody car = OwnerCarRepository.getInstance().getCachedCar(id);
                if (car != null && car.getCarDetails() != null) {
                    car.getCarDetails().setAddress(carData.getAddress());
                    car.getCarDetails().setTown(carData.getTown());
                    car.getCarDetails().setLatitude(carData.getLatitude());
                    car.getCarDetails().setLongitude(carData.getLongitude());
                }
                CarEntity carFromList = OwnerCarsRepository.getInstance().getCachedCar(id);
                if (carFromList != null) {
                    BaseCarEntity carDetails = carFromList.getCarDetails();
                    if (carDetails != null) {
                        carDetails.setAddress(carData.getAddress());
                    }
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
    public void updateInfo(Integer id, NewCarData carData, CarEditDataSource.Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateInfo(id, carData, callback);
    }

    @Override
    public void updateCarTitle(Integer id, CarTitleEntity title, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateCarTitle(id, title, callback);
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
    public void updateMinRentHourly(Integer id, MinRentalDurationEntity durationEntity, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateMinRentHourly(id, durationEntity, new Callback() {
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
    public void updateMinRentDaily(Integer id, MinRentalDurationEntity durationEntity, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateMinRentDaily(id, durationEntity, new Callback() {
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
        remoteDataSource.updateRentalRatesHourly(id, ratesEntity, new Callback() {
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
    public void updateDeliveryRates(Integer id, DeliveryRatesEntity deliveryRatesEntity, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateDeliveryRates(id, deliveryRatesEntity, new Callback() {
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
    public void updateInstantBookingDaily(Integer id, boolean isInstantBooking, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateInstantBookingDaily(id, isInstantBooking, new Callback() {
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
    public void updateInstantBookingHourly(Integer id, boolean isInstantBooking, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateInstantBookingHourly(id, isInstantBooking, new Callback() {
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
    public void updateInstantBookingDailyCount(Integer id, int count, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateInstantBookingDailyCount(id, count, new Callback() {
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
    public void updateInstantBookingHourlyCount(Integer id, int count, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateInstantBookingHourlyCount(id, count, new Callback() {
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
    public void updateCurbsideDeliveryDaily(Integer id, boolean isCurbsideDelivery, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateCurbsideDeliveryDaily(id, isCurbsideDelivery, new Callback() {
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
    public void updateCurbsideDeliveryHourly(Integer id, boolean isCurbsideDelivery, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateCurbsideDeliveryHourly(id, isCurbsideDelivery, new Callback() {
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
    public void updateAcceptCashDaily(Integer id, boolean isAcceptCash, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateAcceptCashDaily(id, isAcceptCash, new Callback() {
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
    public void updateAcceptCashHourly(Integer id, boolean isAcceptCash, Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.updateAcceptCashHourly(id, isAcceptCash, new Callback() {
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
    public void updateFuelPolicyDaily(Integer id, FuelPolicyEntity fuelPolicy, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        remoteDataSource.updateFuelPolicyDaily(id, fuelPolicy, new Callback() {
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
    public void updateFuelPolicyHourly(Integer id, FuelPolicyEntity fuelPolicy, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: " + id));
            return;
        }
        remoteDataSource.updateFuelPolicyHourly(id, fuelPolicy, new Callback() {
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

    @Override
    public void deleteImage(final Integer id, Integer imageId, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.deleteImage(id, imageId, new Callback() {
            @Override
            public void onSuccess() {
                OwnerCarRepository.getInstance().refresh(id);
                OwnerCarsRepository.getInstance().refreshCars();
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void setPrimaryImage(final Integer id, final Integer imageId, final Callback callback) {
        if (id == null) {
            callback.onError(new Error(Error.Type.INVALID_REQUEST, "Invalid ID: null"));
            return;
        }
        remoteDataSource.setPrimaryImage(id, imageId, new Callback() {
            @Override
            public void onSuccess() {
                OwnerCarRepository.getInstance().refresh(id);
                OwnerCarsRepository.getInstance().refreshCars();
                callback.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callback.onError(error);
            }
        });
    }
}
