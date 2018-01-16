package com.cardee.inbox.alert.list.adapter;

import android.support.v7.util.DiffUtil;

import com.cardee.data_source.inbox.local.alert.entity.Alert;

import java.util.List;

public class AlertDiffCallback extends DiffUtil.Callback {

    private List<Alert> oldAlertList;
    private List<Alert> newAlertList;

    public AlertDiffCallback(List<Alert> oldAlertList, List<Alert> newAlertList) {
        this.oldAlertList = oldAlertList;
        this.newAlertList = newAlertList;
    }

    @Override
    public int getOldListSize() {
        return oldAlertList.size();
    }

    @Override
    public int getNewListSize() {
        return newAlertList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldAlertList.get(oldItemPosition).getAlertId().intValue() == newAlertList.get(newItemPosition).getAlertId().intValue();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldAlertList.get(oldItemPosition).equals(newAlertList.get(newItemPosition));
    }
}
