package com.cardee.owner_bookings.car_returned.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cardee.R;
import com.cardee.owner_bookings.car_returned.presenter.CarReturnedPresenter;

public class CarReturnedActivity extends AppCompatActivity implements CarReturnedView{

    private CarReturnedPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_returned);

        mPresenter = new CarReturnedPresenter(this);
    }



    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }
}
