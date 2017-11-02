package com.cardee.data_source.remote;

import com.cardee.data_source.OwnerCarDataSource;


public class RemoteOwnerCarDataSource implements OwnerCarDataSource {

    private static RemoteOwnerCarDataSource INSTANCE;

    private RemoteOwnerCarDataSource(){

    }

    public static RemoteOwnerCarDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RemoteOwnerCarDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void obtainCar(int id, Callback callback) {

    }
}
