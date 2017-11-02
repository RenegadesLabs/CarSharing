package com.cardee.domain.filter;


import com.cardee.domain.Filter;

public class Filters {

    private Filters() {

    }

    public static Filter createOwnerCarFilter() {
        return new OwnerCarFilter();
    }

    public static Filter createRenterCarFilter() {
        return new RenterCarFilter();
    }

}
