package com.cardee.inbox.alert.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.data_source.inbox.local.alert.entity.Alert;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class AlertListAdapter extends RecyclerView.Adapter<AlertListAdapter.AlertViewHolder> {

    private List<Alert> mAlertList;
    private PublishSubject<Alert> mSubject;

    public AlertListAdapter() {
        mAlertList = new ArrayList<>();
        mSubject = PublishSubject.create();
    }

    @Override
    public AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AlertViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mAlertList.size();
    }

    public void subscribeToAlertClick(Consumer<Alert> consumer) {
        mSubject.subscribe(consumer);
    }

    class AlertViewHolder extends RecyclerView.ViewHolder {

        public AlertViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(Alert alert) {

        }
    }
}
