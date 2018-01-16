package com.cardee.inbox.alert.list.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.account_details.view.AccountDetailsActivity;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.inbox.alert.list.adapter.AlertListAdapter;
import com.cardee.inbox.alert.list.presenter.AlertListContract;
import com.cardee.inbox.alert.list.presenter.AlertListPresenterImp;
import com.cardee.mvp.BaseView;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.car_returned.view.CarReturnedActivity;
import com.cardee.owner_bookings.view.BookingActivity;
import com.cardee.owner_car_details.OwnerCarDetailsContract;
import com.cardee.owner_car_details.view.OwnerCarDetailsActivity;
import com.cardee.renter_bookings.rate_rental_exp.view.RateRentalExpActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertListFragment extends Fragment implements AlertListContract.View {

    @BindView(R.id.alert_recycler)
    RecyclerView mAlertRecycler;

    private AlertListContract.Presenter mPresenter;
    private AlertListAdapter mAlertAdapter;

    public static AlertListFragment newInstance() {
        Bundle args = new Bundle();
        AlertListFragment fragment = new AlertListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AlertListPresenterImp(getActivity());
        mPresenter.onInit(this);
        initAdapter();
    }

    private void initAdapter() {
        mAlertAdapter = new AlertListAdapter(getActivity());
        mAlertAdapter.subscribeToAlertClick(alert -> {
            if (mPresenter != null) {
                mPresenter.onAlertClick(alert);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inbox_alerts, container, false);
        ButterKnife.bind(this, root);
        initRecycler();
        mPresenter.onGetAlerts();
        return root;
    }

    private void initRecycler() {
        mAlertRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAlertRecycler.setHasFixedSize(true);
        mAlertRecycler.setAdapter(mAlertAdapter);
    }

    @Override
    public void showAllAlerts(List<Alert> alertList) {
        if (mAlertAdapter != null) {
            mAlertAdapter.setAlertList(alertList);
        }
    }

    @Override
    public void showAlert(Alert alert) {
        if (alert != null) {
            Alert.Type type = alert.getAlertType();
            if (type != null) {
                String session = AccountManager.getInstance(getContext()).getSessionInfo();
                int objectId = alert.getObjectId();
                switch (type) {
                    case ACCEPTED:
                    case REQUEST_EXPIRED:
                    case HANDOVER_REMINDER:
                    case RETURN_OVERDUE:
                    case NEW_REQUEST:
                    case BOOKING_CANCELLATION:
                    case BOOKING_EXT:
                    case RETURN_REMINDER:
                        if (session.equals(AccountManager.OWNER_SESSION)) {
                            Intent intent = new Intent(getActivity(), BookingActivity.class);
                            intent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                            startActivity(intent);
                        } else if (session.equals(AccountManager.RENTER_SESSION)) {
                            //TODO: implement for Renter
                        }
                        break;
                    case USER_VERIFICATION:
                    case RENTER_STATE_CHANGE:
                    case OWNER_STATE_CHANGE:
                        Intent accountIntent = new Intent(getActivity(), AccountDetailsActivity.class);
                        startActivity(accountIntent);
                        break;
                    case BROADCAST:
                        //TODO: different UI every time.
                        break;
                    case RENTER_REVIEW_REMINDER:
                    case RENTER_REVIEW:
                        Intent renterRateIntent = new Intent(getActivity(), RateRentalExpActivity.class);
                        renterRateIntent.putExtra("booking_id", objectId);
                        startActivity(renterRateIntent);
                        break;
                    case OWNER_CHECKLIST_UPD:
                    case RENTER_CHECKLIST_UPD:
                        //TODO:  editedCheckList();
                        break;
                    case INIT_CHECKLIST:
                        //TODO: checkList();
                        break;
                    case OWNER_REVIEW:
                    case OWNER_REVIEW_REMINDER:
                        Intent ownerRateIntent = new Intent(getContext(), CarReturnedActivity.class);
                        ownerRateIntent.putExtra("booking_id", objectId);
                        startActivity(ownerRateIntent);
                        break;
                    case CAR_VERIFICATION:
                    case CAR_STATE_CHANGE:
                        Intent ownerCarIntent = new Intent(getContext(), OwnerCarDetailsActivity.class);
                        ownerCarIntent.putExtra(OwnerCarDetailsContract.CAR_ID, objectId);
                        startActivity(ownerCarIntent);
                        break;
                    case SYSTEM_MESSAGES:
                        // ignore;
                        break;
                }
                mAlertAdapter.notifyDataSetChanged();
            }
        }
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
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroyView();
    }
}
