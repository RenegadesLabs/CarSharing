package com.cardee.renter_browse_cars.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.cardee.R;
import com.cardee.domain.owner.entity.Car;
import com.cardee.domain.renter.entity.OfferCar;
import com.cardee.renter_browse_cars.adapter.RenterBrowseCarsListAdapter;
import com.cardee.renter_browse_cars.presenter.RenterBrowseCarListContract;
import com.cardee.renter_browse_cars.presenter.RenterBrowseCarListPresenter;
import com.cardee.renter_browse_cars.view.custom.RenterBrowseCarsFloatingView;
import com.cardee.renter_browse_cars.view.custom.RenterBrowseCarsHeaderView;
import com.cardee.renter_browse_cars.view.custom.listener.CustomRecyclerScrollListener;
import com.cardee.renter_browse_cars_map.BrowseCarsMapActivity;

import java.util.List;


public class RenterBrowseCarsFragment extends Fragment implements RenterBrowseCarListContract.View {

    private RenterBrowseCarsListAdapter mCarsListAdapter;
    private RenterBrowseCarListPresenter mPresenter;

    private RecyclerView mCarsListView;

    private RenterBrowseCarsFloatingView mFloatingView;
    private LinearLayout mHeaderView;

    public static Fragment newInstance() {
        return new RenterBrowseCarsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarsListAdapter = new RenterBrowseCarsListAdapter(getActivity());
        mPresenter = new RenterBrowseCarListPresenter(this);
        mCarsListAdapter.subscribe(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_renter_cars, container, false);
        mCarsListView = rootView.findViewById(R.id.rv_renterBrowseCarsList);
        mFloatingView = rootView.findViewById(R.id.floating_browse_cars);
        mHeaderView = rootView.findViewById(R.id.header);
        addOnScrollListener();
        initCarList(mCarsListView);
        rootView.findViewById(R.id.ll_browseCarsFloatingMapBtn).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BrowseCarsMapActivity.class);
            startActivity(intent);
        });
        return rootView;
    }

    private void addOnScrollListener() {
        mCarsListView.addOnScrollListener(new CustomRecyclerScrollListener() {
            @Override
            public void show() {
                mHeaderView.animate().translationY(-mHeaderView.getHeight())
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();

                mFloatingView.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2))
                        .start();
            }

            @Override
            public void hide() {

                mHeaderView.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2))
                        .start();

                mFloatingView.animate().translationY(mFloatingView.getHeight())
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();
            }
        });
    }

    private void initCarList(RecyclerView listView) {
        listView.setAdapter(mCarsListAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listView.setItemAnimator(new DefaultItemAnimator());
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
    public void onDestroy() {
        super.onDestroy();
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

    @Override
    public void setItems(List<OfferCar> cars) {
        mCarsListAdapter.insert(cars);

    }

    @Override
    public void updateItem(OfferCar car) {
        mCarsListAdapter.update(car);
    }

    @Override
    public void removeItem(OfferCar car) {
        mCarsListAdapter.remove(car);
    }

    @Override
    public void openItem(OfferCar car) {

    }

    @Override
    public void onUnauthorized() {

    }

    @Override
    public void onConnectionLost() {

    }
}
