package com.cardee.domain.owner.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.cardee.data_source.remote.api.common.entity.CarRuleEntity;

public class RentalTerms implements Parcelable {

    private Integer mRequiredMinAge;

    private Integer mRequiredMaxAge;

    private Integer mRequiredDrivingExperience;

    private CarRuleEntity[] mCarRules;

    private String mOtherCarRules;

    private Boolean mRequiredSecurityDeposit;

    private String mSecurityDepositDescription;

    private String mCompensationExcess;

    private String mCompensationOtherGuidelines;

    private String mAddOns;

    private String mAdditionalTerms;

    public RentalTerms() {
    }

    protected RentalTerms(Parcel in) {
        if (in.readByte() == 0) {
            mRequiredMinAge = null;
        } else {
            mRequiredMinAge = in.readInt();
        }
        if (in.readByte() == 0) {
            mRequiredMaxAge = null;
        } else {
            mRequiredMaxAge = in.readInt();
        }
        if (in.readByte() == 0) {
            mRequiredDrivingExperience = null;
        } else {
            mRequiredDrivingExperience = in.readInt();
        }
        mOtherCarRules = in.readString();
        byte tmpMRequiredSecurityDeposit = in.readByte();
        mRequiredSecurityDeposit = tmpMRequiredSecurityDeposit == 0 ? null : tmpMRequiredSecurityDeposit == 1;
        mSecurityDepositDescription = in.readString();
        mCompensationExcess = in.readString();
        mCompensationOtherGuidelines = in.readString();
        mAddOns = in.readString();
        mAdditionalTerms = in.readString();
    }

    public static final Creator<RentalTerms> CREATOR = new Creator<RentalTerms>() {
        @Override
        public RentalTerms createFromParcel(Parcel in) {
            return new RentalTerms(in);
        }

        @Override
        public RentalTerms[] newArray(int size) {
            return new RentalTerms[size];
        }
    };

    public Integer getRequiredMinAge() {
        return mRequiredMinAge;
    }

    public void setRequiredMinAge(Integer requiredMinAge) {
        mRequiredMinAge = requiredMinAge;
    }

    public Integer getRequiredMaxAge() {
        return mRequiredMaxAge;
    }

    public void setRequiredMaxAge(Integer requiredMaxAge) {
        mRequiredMaxAge = requiredMaxAge;
    }

    public Integer getRequiredDrivingExperience() {
        return mRequiredDrivingExperience;
    }

    public void setRequiredDrivingExp(Integer requiredDrivingExperience) {
        mRequiredDrivingExperience = requiredDrivingExperience;
    }

    public CarRuleEntity[] getCarRules() {
        return mCarRules;
    }

    public void setCarRules(CarRuleEntity[] carRules) {
        mCarRules = carRules;
    }

    public String getOtherCarRules() {
        return mOtherCarRules;
    }

    public void setOtherCarRules(String anotherCarRules) {
        mOtherCarRules = anotherCarRules;
    }

    public Boolean getRequiredSecurityDeposit() {
        return mRequiredSecurityDeposit;
    }

    public void setRequiredSecurityDeposit(Boolean requiredSecurityDeposit) {
        mRequiredSecurityDeposit = requiredSecurityDeposit;
    }

    public String getSecurityDepositDescription() {
        return mSecurityDepositDescription;
    }

    public void setSecurityDepositDescription(String securityDepositDescription) {
        mSecurityDepositDescription = securityDepositDescription;
    }

    public String getCompensationExcess() {
        return mCompensationExcess;
    }

    public void setCompensationExcess(String compensationExcess) {
        mCompensationExcess = compensationExcess;
    }

    public String getCompensationOtherGuidelines() {
        return mCompensationOtherGuidelines;
    }

    public void setCompensationOtherGuidelines(String compensationOtherGuidelines) {
        mCompensationOtherGuidelines = compensationOtherGuidelines;
    }

    public String getAddOns() {
        return mAddOns;
    }

    public void setAddOns(String addOns) {
        mAddOns = addOns;
    }

    public String getAdditionalTerms() {
        return mAdditionalTerms;
    }

    public void setAdditionalTerms(String additionalTerms) {
        mAdditionalTerms = additionalTerms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (mRequiredMinAge == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mRequiredMinAge);
        }
        if (mRequiredMaxAge == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mRequiredMaxAge);
        }
        if (mRequiredDrivingExperience == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mRequiredDrivingExperience);
        }
        parcel.writeString(mOtherCarRules);
        parcel.writeByte((byte) (mRequiredSecurityDeposit == null ? 0 : mRequiredSecurityDeposit ? 1 : 2));
        parcel.writeString(mSecurityDepositDescription);
        parcel.writeString(mCompensationExcess);
        parcel.writeString(mCompensationOtherGuidelines);
        parcel.writeString(mAddOns);
        parcel.writeString(mAdditionalTerms);
    }
}
