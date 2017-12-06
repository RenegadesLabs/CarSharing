package com.cardee.domain.owner.entity;

public class RentalTermsRequirements {

    private int mMinAge;
    private int mMaxAge;
    private int mExperience;

    public RentalTermsRequirements(int minAge, int maxAge, int experience) {
        mMinAge = minAge;
        mMaxAge = maxAge;
        mExperience = experience;
    }
    public int getMinAge() {
        return mMinAge;
    }

    public int getMaxAge() {
        return mMaxAge;
    }

    public int getExperience() {
        return mExperience;
    }
}
