package com.cardee.inbox.alert.list.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.account_details.view.AccountDetailsActivity;
import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.remote.service.AccountManager;
import com.cardee.inbox.alert.list.adapter.AlertListAdapter;
import com.cardee.inbox.alert.list.presenter.AlertListContract;
import com.cardee.inbox.alert.list.presenter.AlertListPresenterImp;
import com.cardee.owner_bookings.OwnerBookingContract;
import com.cardee.owner_bookings.car_checklist.service.PendingChecklistStorage;
import com.cardee.owner_bookings.car_checklist.view.OwnerRenterUpdatedChecklistActivity;
import com.cardee.owner_bookings.car_checklist.view.RenterChecklistActivity;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAlertRecycler.setLayoutManager(layoutManager);
        mAlertRecycler.setHasFixedSize(true);
        mAlertRecycler.setAdapter(mAlertAdapter);
        mAlertAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
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
                            intent.putExtra(BookingActivity.IS_RENTER, false);
                            intent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                            startActivity(intent);
                        } else if (session.equals(AccountManager.RENTER_SESSION)) {
                            Intent intent = new Intent(getActivity(), BookingActivity.class);
                            intent.putExtra(BookingActivity.IS_RENTER, true);
                            intent.putExtra(OwnerBookingContract.BOOKING_ID, objectId);
                            startActivity(intent);
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
                        if (session.equals(AccountManager.OWNER_SESSION)) {
                            AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.RENTER_SESSION);

                            Intent renterRateIntent = new Intent(getActivity(), RateRentalExpActivity.class);
                            renterRateIntent.putExtra("booking_id", objectId);
                            renterRateIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                            stackBuilder.addNextIntentWithParentStack(renterRateIntent);
                            stackBuilder.startActivities();
                        } else {
                            Intent renterRateIntent = new Intent(getActivity(), RateRentalExpActivity.class);
                            renterRateIntent.putExtra("booking_id", objectId);
                            startActivity(renterRateIntent);
                        }
                        break;
                    case OWNER_CHECKLIST_UPD:
                        if (alert.isNewBooking()) {
                            PendingChecklistStorage.remove(getActivity(), objectId);

                            if (session.equals(AccountManager.OWNER_SESSION)) {
                                AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.RENTER_SESSION);

                                Intent ownerEditIntent = new Intent(getActivity(), RenterChecklistActivity.class);
                                ownerEditIntent.putExtra(RenterChecklistActivity.KEY_BOOKING_ID, objectId);
                                ownerEditIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                                stackBuilder.addNextIntentWithParentStack(ownerEditIntent);
                                stackBuilder.startActivities();
                            } else {
                                PendingChecklistStorage.remove(getActivity(), objectId);
                                Intent ownerEditIntent = new Intent(getActivity(), RenterChecklistActivity.class);
                                ownerEditIntent.putExtra(RenterChecklistActivity.KEY_BOOKING_ID, objectId);
                                ownerEditIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(ownerEditIntent);
                            }
                        }
                        break;
                    case RENTER_CHECKLIST_UPD:
                        if (alert.isNewBooking()) {
                            PendingChecklistStorage.remove(getActivity(), objectId);

                            if (session.equals(AccountManager.RENTER_SESSION)) {
                                AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.OWNER_SESSION);

                                Intent renterEditIntent = new Intent(getActivity(), OwnerRenterUpdatedChecklistActivity.class);
                                renterEditIntent.putExtra(OwnerRenterUpdatedChecklistActivity.KEY_BOOKING_ID, objectId);
                                renterEditIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                                stackBuilder.addNextIntentWithParentStack(renterEditIntent);
                                stackBuilder.startActivities();
                            } else {
                                PendingChecklistStorage.remove(getActivity(), objectId);
                                Intent renterEditIntent = new Intent(getActivity(), OwnerRenterUpdatedChecklistActivity.class);
                                renterEditIntent.putExtra(OwnerRenterUpdatedChecklistActivity.KEY_BOOKING_ID, objectId);
                                renterEditIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(renterEditIntent);
                            }
                        }
                        break;
                    case INIT_CHECKLIST:
                        if (alert.isNewBooking()) {
                            PendingChecklistStorage.remove(getActivity(), objectId);

                            Intent checklistIntent = new Intent(getActivity(), RenterChecklistActivity.class);
                            checklistIntent.putExtra(RenterChecklistActivity.KEY_BOOKING_ID, objectId);
                            checklistIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            if (session.equals(AccountManager.OWNER_SESSION)) {
                                AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.RENTER_SESSION);

                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                                stackBuilder.addNextIntentWithParentStack(checklistIntent);
                                stackBuilder.startActivities();
                            } else {
                                startActivity(checklistIntent);
                            }
                        }
                        break;
                    case OWNER_REVIEW:
                    case OWNER_REVIEW_REMINDER:
                        if (session.equals(AccountManager.RENTER_SESSION)) {
                            AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.OWNER_SESSION);

                            Intent ownerRateIntent = new Intent(getContext(), CarReturnedActivity.class);
                            ownerRateIntent.putExtra("booking_id", objectId);
                            ownerRateIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                            stackBuilder.addNextIntentWithParentStack(ownerRateIntent);
                            stackBuilder.startActivities();
                        } else {
                            Intent ownerRateIntent = new Intent(getContext(), CarReturnedActivity.class);
                            ownerRateIntent.putExtra("booking_id", objectId);
                            startActivity(ownerRateIntent);
                        }
                        break;
                    case CAR_VERIFICATION:
                    case CAR_STATE_CHANGE:
                        if (session.equals(AccountManager.RENTER_SESSION)) {
                            AccountManager.getInstance(CardeeApp.context).setSession(AccountManager.OWNER_SESSION);

                            Intent ownerCarIntent = new Intent(getContext(), OwnerCarDetailsActivity.class);
                            ownerCarIntent.putExtra(OwnerCarDetailsContract.CAR_ID, objectId);
                            ownerCarIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                            stackBuilder.addNextIntentWithParentStack(ownerCarIntent);
                            stackBuilder.startActivities();
                        } else {
                            Intent ownerCarIntent = new Intent(getContext(), OwnerCarDetailsActivity.class);
                            ownerCarIntent.putExtra(OwnerCarDetailsContract.CAR_ID, objectId);
                            startActivity(ownerCarIntent);
                        }
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
