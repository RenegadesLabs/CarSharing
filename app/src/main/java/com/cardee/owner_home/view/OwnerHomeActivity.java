package com.cardee.owner_home.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.cardee.R;
import com.cardee.custom.modal.AvailabilityMenuFragment;
import com.cardee.domain.owner.entity.Car;
import com.cardee.inbox.InboxFragment;
import com.cardee.owner_bookings.view.BookingListFragment;
import com.cardee.owner_car_add.NewCarFormsContract;
import com.cardee.owner_car_add.view.CarAddActivity;
import com.cardee.owner_car_details.AvailabilityContract;
import com.cardee.owner_car_details.OwnerCarDetailsContract;
import com.cardee.owner_car_details.view.CarDetailsEditActivity;
import com.cardee.owner_car_details.view.OwnerCarDetailsActivity;
import com.cardee.owner_home.presenter.HomeContract;
import com.cardee.owner_home.presenter.OwnerHomePresenterImp;
import com.cardee.owner_home.view.helper.BottomNavigationHelper;
import com.cardee.owner_home.view.listener.CarListItemEventListener;
import com.cardee.owner_home.view.listener.MoreTabItemEventListener;
import com.cardee.owner_home.view.service.FragmentFactory;

public class OwnerHomeActivity extends AppCompatActivity
        implements AHBottomNavigation.OnTabSelectedListener,
        CarListItemEventListener, MoreTabItemEventListener, View.OnClickListener, HomeContract.View {

    public static final String TAB_TO_SELECT = "tab_to_select";

    private static final String TAG = OwnerHomeActivity.class.getSimpleName();
    private static final int ADD_NEW_CAR_REQUEST_CODE = 101;

    private HomeContract.Presenter mPresenter;
    private boolean mHasFragment;
    private TextView mTitle;
    private View mAddCarAction;
    private ProgressBar mProgress;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);
        if (getSupportActionBar() == null) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            mTitle = toolbar.findViewById(R.id.toolbar_title);
            mAddCarAction = toolbar.findViewById(R.id.toolbar_action);
            mAddCarAction.setOnClickListener(this);
        }
        mProgress = findViewById(R.id.home_progress);
        AHBottomNavigation bottomMenu = findViewById(R.id.bottom_menu);
        BottomNavigationHelper.prepareForOwner(bottomMenu);
        bottomMenu.setOnTabSelectedListener(this);

        int tabToSelect = getIntent().getIntExtra(TAB_TO_SELECT, 1);
        bottomMenu.setCurrentItem(tabToSelect);
        initPresenter(bottomMenu);
    }

    private void initPresenter(AHBottomNavigation bottomMenu) {
        mPresenter = new OwnerHomePresenterImp();
        mPresenter.init(this, bottomMenu);
        mPresenter.onSubscribeToNotifications();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (!wasSelected) {
            switch (position) {
                case 0:
                    getSupportActionBar().hide();
                    break;
                case 1:
                    getSupportActionBar().show();
                    mTitle.setText(R.string.title_my_cars);
                    mAddCarAction.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    getSupportActionBar().show();
                    mTitle.setText(R.string.title_my_bookings);
                    mAddCarAction.setVisibility(View.GONE);
                    break;
                case 3:
                    getSupportActionBar().hide();
                    break;
            }
            showFragmentOnPosition(position);
        }
        return true;
    }

    private void showFragmentOnPosition(int position) {
        Class fragmentClazz = null;
        switch (position) {
            case 0:
                fragmentClazz = InboxFragment.class;
                break;
            case 1:
                fragmentClazz = OwnerCarsFragment.class;
                break;
            case 2:
                fragmentClazz = BookingListFragment.class;
                break;
            case 3:
                fragmentClazz = OwnerProfileFragment.class;
                break;
            default:
                Log.e(TAG, "Position changed to: " + position);
        }

        if (fragmentClazz == null) {
            Log.e(TAG, "No Fragment specified for the position: " + position);
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClazz.getName());
        if (fragment == null) {
            fragment = FragmentFactory.getInstance(fragmentClazz);
        }

        if (fragment == null) {
            Log.e(TAG, "Fragment instance of class " + fragmentClazz.getSimpleName() + " is null");
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (mHasFragment) {
            transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        } else {
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
        }
        transaction.commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.e(TAG, fragment.toString());
        mHasFragment = true;
    }

    @Override
    public void onCarItemClick(Car car) {
        Intent intent = new Intent(this, OwnerCarDetailsActivity.class);
        Bundle args = new Bundle();
        args.putInt(OwnerCarDetailsContract.CAR_ID, car.getCarId() == null ? -1 : car.getCarId());
        args.putString(OwnerCarDetailsContract.CAR_NUMBER, car.getLicenceNumber());
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void onHourlyPickerClick(Car car) {
        AvailabilityMenuFragment menuFragment = AvailabilityMenuFragment
                .getInstance(car.getCarId(), AvailabilityContract.Mode.HOURLY);
        menuFragment.show(getSupportFragmentManager(), menuFragment.getTag());
    }

    @Override
    public void onDailyPickerClick(Car car) {
        AvailabilityMenuFragment menuFragment = AvailabilityMenuFragment
                .getInstance(car.getCarId(), AvailabilityContract.Mode.DAILY);
        menuFragment.show(getSupportFragmentManager(), menuFragment.getTag());
    }

    @Override
    public void onLocationPickerClick(Car car) {
        Intent intent = new Intent(this, CarDetailsEditActivity.class);
        intent.putExtra(NewCarFormsContract.CAR_ID, car.getCarId());
        intent.putExtra(NewCarFormsContract.VIEW_MODE, NewCarFormsContract.Mode.LOCATION);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action:
                startActivityForResult(new Intent(this, CarAddActivity.class), ADD_NEW_CAR_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_CAR_REQUEST_CODE && resultCode == NewCarFormsContract.CAR_CREATED) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.car_added_title)
                    .setMessage(R.string.car_added_message)
                    .setPositiveButton(R.string.car_added_ok, (dialogInterface, i) ->
                            dialogInterface.dismiss())
                    .create();
            alertDialog.setOnShowListener(dialogInterface ->
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(ContextCompat.getColor(OwnerHomeActivity.this, R.color.blue)));
            alertDialog.show();
        }
    }

    @Override
    public void onStartLoading() {
        mProgress.setVisibility(View.VISIBLE);
        mHandler.postDelayed(() ->
                mProgress.setVisibility(View.GONE), 5000); //hide progress bar if there is no response for 5 seconds
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onStopLoading() {
        mProgress.setVisibility(View.GONE);
    }

}
