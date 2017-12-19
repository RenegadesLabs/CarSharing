package com.cardee.renter_notifications.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.cardee.R;
import com.cardee.domain.UseCaseExecutor;
import com.cardee.renter_notifications.RenterNotifView;

import java.util.Arrays;
import java.util.List;

public class RenterNotifPresenter {

    private List<String> mRemindersList;
    private RenterNotifView mView;
    private UseCaseExecutor mExecutor;

    public RenterNotifPresenter(RenterNotifView view) {
        mView = view;
        mExecutor = UseCaseExecutor.getInstance();
        mRemindersList = Arrays.asList(((Context) view).getResources().getStringArray(R.array.reminders));
    }

    public void onHandoverReminderClicked(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.car_handover_reminder)
                .setItems(R.array.reminders, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendHandoverReminderToServer(i);
                        mView.setHandoverReminder(mRemindersList.get(i));
                    }
                });
        builder.create().show();
    }

    public void onReturnReminderClicked(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.car_return_reminder)
                .setItems(R.array.reminders, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendReturnReminderToServer(i);
                        mView.setReturnReminder(mRemindersList.get(i));
                    }
                });
        builder.create().show();
    }

    public void onBookingRequestSwitched() {
    }

    public void onInstantBookSwitched() {
    }

    public void onRemindersSwitched() {
    }

    private void sendHandoverReminderToServer(int i) {
    }

    private void sendReturnReminderToServer(int i) {
    }

}
