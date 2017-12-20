package com.cardee.data_source;


import android.net.Uri;

import com.cardee.data_source.remote.api.cars.request.NewCarData;
import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;
import com.cardee.data_source.remote.api.common.entity.DeliveryRatesEntity;
import com.cardee.data_source.remote.api.common.entity.FuelPolicyEntity;
import com.cardee.data_source.remote.api.common.entity.RentalRatesEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsAdditionalEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsInsuranceEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsRequirementsEntity;
import com.cardee.data_source.remote.api.common.entity.RentalTermsSecurityDepositEntity;
import com.cardee.domain.owner.entity.RentalTermsRequirements;

public interface CarEditDataSource {

    void updateLocation(Integer id, NewCarData carData, Callback callback);

    void updateInfo(Integer id, NewCarData carData, Callback callback);

    void updateDescription(Integer id, String description, Callback callback);

    void updateDeliveryRates(Integer id, DeliveryRatesEntity deliveryRatesEntity, Callback callback);

    void updateInstantBookingDaily(Integer id, boolean isInstantBooking, Callback callback);

    void updateInstantBookingHourly(Integer id, boolean isInstantBooking, Callback callback);

    void updateCurbsideDeliveryDaily(Integer id, boolean isCurbsideDelivery, Callback callback);

    void updateCurbsideDeliveryHourly(Integer id, boolean isCurbsideDelivery, Callback callback);

    void updateAcceptCashDaily(Integer id, boolean isAcceptCash, Callback callback);

    void updateAcceptCashHourly(Integer id, boolean isAcceptCash, Callback callback);

    void updateRentalRatesDaily(Integer id, RentalRatesEntity ratesEntity, Callback callback);

    void updateRentalRatesHourly(Integer id, RentalRatesEntity ratesEntity, Callback callback);

    void updateFuelPolicyDaily(Integer id, FuelPolicyEntity fuelPolicy, Callback callback);

    void updateFuelPolicyHourly(Integer id, FuelPolicyEntity fuelPolicy, Callback callback);

    void updateRentalRequirements(Integer id, RentalTermsRequirementsEntity requirements, Callback callback);

    void updateRentalRules(Integer id, CarRuleEntity rules, Callback callback);

    void updateRentalSecurityDeposit(Integer id, RentalTermsSecurityDepositEntity securityDeposit, Callback callback);

    void updateRentalInsuranceExcess(Integer id, RentalTermsInsuranceEntity insuranceExcess, Callback callback);

    void updateRentalAdditionalTerms(Integer id, RentalTermsAdditionalEntity additionalEntity, Callback callback);

    void uploadImage(Integer id, Uri uri, ImageCallback callback);


    interface Callback {
        void onSuccess();

        void onError(Error error);
    }

    abstract class ImageCallback implements Callback {
        public abstract void onSuccess(int imageId);

        @Override
        public void onSuccess() {

        }
    }
}
