package com.cardee.owner_car_details.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FetchAddressService extends IntentService {

    public static final String RECEIVER = "service_result_receiver";
    public static final String LOCATION = "location";
    public static final String ADDRESS = "location_address";

    public static final int CODE_SUCCESS = 101;
    public static final int CODE_NOT_FOUND = 202;
    public static final int CODE_ERROR = 303;

    protected ResultReceiver addressReceiver;

    public FetchAddressService() {
        super(FetchAddressService.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LatLng location = intent.getParcelableExtra(LOCATION);
        addressReceiver = intent.getParcelableExtra(RECEIVER);
        if (!Geocoder.isPresent()) {
            sendResponseToReceiver(CODE_ERROR, null);
            return;
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault()); //TODO: specify location if needed

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
        } catch (IOException | IllegalArgumentException e) {
            sendResponseToReceiver(CODE_ERROR, null);
        }

        if (addresses == null || addresses.isEmpty()) {
            sendResponseToReceiver(CODE_NOT_FOUND, null);
        } else {
            Address address = addresses.get(0);
            StringBuilder addressBuilder = new StringBuilder();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressBuilder.append(address.getAddressLine(i));
                if (i != address.getMaxAddressLineIndex()) {
                    addressBuilder.append(" ");
                }
            }
            sendResponseToReceiver(CODE_SUCCESS, addressBuilder.toString());
        }
    }

    private void sendResponseToReceiver(int code, String address) {
        if (addressReceiver != null) {
            Bundle args = new Bundle();
            args.putString(ADDRESS, address);
            addressReceiver.send(code, args);
        }
    }
}
