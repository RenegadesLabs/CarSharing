package com.cardee.inbox.alert.list.presenter;

import android.os.Bundle;

import com.cardee.data_source.inbox.local.alert.entity.Alert;
import com.cardee.mvp.BaseView;

import java.util.List;

public interface AlertListContract {

    interface Presenter {

        void onInit(AlertListContract.View view);

        void onGetAlerts();

        void onAlertClick(Alert alert);

        void onDestroy();

    }

    interface View extends BaseView {

        void showAllAlerts(List<Alert> alertList);

        void showAlert(Bundle bundle);
    }
}
