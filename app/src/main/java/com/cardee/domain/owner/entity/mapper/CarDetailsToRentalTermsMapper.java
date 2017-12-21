package com.cardee.domain.owner.entity.mapper;

import android.support.annotation.NonNull;

import com.cardee.data_source.remote.api.cars.response.entity.CarDetailsEntity;
import com.cardee.domain.owner.entity.RentalTerms;

public class CarDetailsToRentalTermsMapper {

    public static RentalTerms transform(@NonNull CarDetailsEntity details) {
        RentalTerms terms = new RentalTerms();
        terms.setRequiredMinAge(details.getRequiredMinAge());
        terms.setRequiredMaxAge(details.getRequiredMaxAge());
        terms.setRequiredDrivingExp(details.getRequiredDrivingExp());
        terms.setOtherCarRules(details.getCarOtherRules());
        terms.setRequiredSecurityDeposit(details.getRequiredSecurityDeposit());
        terms.setSecurityDepositDescription(details.getSecurityDepositDescription());
        terms.setCompensationExcess(details.getCompensationAccess());
        terms.setCompensationOtherGuidelines(details.getCompensationOtherGuidelines());
        terms.setAddOns(details.getAddOns());
        terms.setAdditionalTerms(details.getAdditionalTerms());
        return terms;
    }
}
