package com.cardee.owner_home.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.domain.owner.entity.Car;
import com.cardee.owner_home.OwnerCarListContract;
import com.cardee.owner_home.presenter.OwnerCarsPresenter;
import com.cardee.owner_home.view.adapter.CarListAdapter;
import com.cardee.owner_home.view.listener.CarListItemEventListener;

import java.util.List;

public class OwnerCarsFragment extends Fragment implements OwnerCarListContract.View {

    private CarListAdapter mAdapter;
    private RecyclerView mCarsListView;

    private OwnerCarsPresenter mPresenter;
    private CarListItemEventListener mEventListener;
    private Toast mCurrentToast;

    public static Fragment newInstance() {
        return new OwnerCarsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEventListener = (CarListItemEventListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mEventListener = (CarListItemEventListener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CarListAdapter(getActivity());
        mPresenter = new OwnerCarsPresenter(this);
        mAdapter.subscribe(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_cars, container, false);
        mCarsListView = rootView.findViewById(R.id.owner_cars_list);
        initCarList(mCarsListView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadItems();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    private void initCarList(RecyclerView listView) {
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setItems(List<Car> items) {
        mAdapter.insert(items);
    }

    @Override
    public void updateItem(Car car) {
        mAdapter.update(car);
    }

    @Override
    public void removeItem(Car car) {

    }

    @Override
    public void openItem(Car car) {
        if (mEventListener != null) {
            mEventListener.onCarItemClick(car);
        }
    }

    @Override
    public void openDailyPicker(Car car) {
        if (mEventListener != null) {
            mEventListener.onDailyPickerClick(car);
        }
    }

    @Override
    public void openHourlyPicker(Car car) {
        if (mEventListener != null) {
            mEventListener.onHourlyPickerClick(car);
        }
    }

    @Override
    public void openLocationPicker(Car car) {
        if (mEventListener != null) {
            mEventListener.onLocationPickerClick(car);
        }
    }

    @Override
    public void onUnauthorized() {

    }

    @Override
    public void onConnectionLost() {

    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mEventListener.onStartLoading();
        } else {
            mEventListener.onStopLoading();
        }
    }

    @Override
    public void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        showMessage(getString(messageId));
    }

}
