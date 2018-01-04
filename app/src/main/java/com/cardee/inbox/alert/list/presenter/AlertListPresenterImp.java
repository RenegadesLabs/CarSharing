package com.cardee.inbox.alert.list.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.data_source.inbox.repository.InboxRepository;
import com.cardee.data_source.remote.service.AccountManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlertListPresenterImp implements AlertListContract.Presenter {

    private static final String TAG = AlertListPresenterImp.class.getSimpleName();
    private AlertListContract.View mView;

    private final InboxRepository mInboxRepository;
    private final String mAttachment;
    private CompositeDisposable mCompositeDisposable;

    public AlertListPresenterImp(Context context) {
        mAttachment = AccountManager.getInstance(context).getSessionInfo();
        mInboxRepository = InboxRepository.getInstance();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onInit(AlertListContract.View view) {
        mView = view;
    }

    @Override
    public void onGetAlerts() {
        Disposable localSub = mInboxRepository
                .getLocalAlerts(mAttachment)
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .filter(alerts -> alerts != null && !alerts.isEmpty())
                .subscribe(this::showAllAlerts);

        Disposable remoteSub = mInboxRepository.getRemoteAlerts(mAttachment)
                .filter(alerts -> alerts != null && !alerts.isEmpty())
                .subscribe(mInboxRepository::fetchAlertData, throwable -> Log.d(TAG, "Connection lost"));

        mCompositeDisposable.addAll(localSub, remoteSub);
    }

    private void showAllAlerts(List<Alert> alerts) {
        if (mView != null) {
            mView.showAllAlerts(alerts);
        }
    }

    @Override
    public void onAlertClick(Alert alert) {
        Bundle bundle = new Bundle();
        bundle.putInt(Alert.ALERT_SERVER_ID, alert.getAlertId());
        bundle.putString(Alert.ALERT_ATTACHMENT, alert.getAttachment());
        if (mView != null) {
            mView.showAlert(bundle);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
