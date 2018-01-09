package com.cardee.inbox.alert.list.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cardee.R;
import com.cardee.data_source.inbox.local.alert.entity.Alert;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class AlertListAdapter extends RecyclerView.Adapter<AlertListAdapter.AlertViewHolder> {

    private List<Alert> mAlertList;
    private PublishSubject<Alert> onClickSubject;

    public AlertListAdapter(Context context) {
        mAlertList = new ArrayList<>();
        onClickSubject = PublishSubject.create();
    }

    @Override
    public AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox_alert, parent, false);
        return new AlertViewHolder(root);
    }

    @Override
    public void onBindViewHolder(AlertViewHolder holder, int position) {
        Alert alert = mAlertList.get(position);
        holder.bind(alert, onClickSubject);
    }

    public void setAlertList(List<Alert> alertList) {
        if (mAlertList.isEmpty()) {
            mAlertList = alertList;
            notifyDataSetChanged();
        } else {
            updateAlertList(alertList);
        }
    }

    private void updateAlertList(List<Alert> newList) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new AlertDiffCallback(mAlertList, newList));
        mAlertList = newList;
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return mAlertList.size();
    }

    public void subscribeToAlertClick(Consumer<Alert> consumer) {
        onClickSubject.subscribe(consumer);
    }

    class AlertViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView mAlertImage;
        private AppCompatImageView mAlertClickButton;
        private AppCompatImageView mAlertNewDot;
        private TextView mAlertTitle;
        private TextView mAlertMessage;

        AlertViewHolder(View itemView) {
            super(itemView);
            mAlertImage = itemView.findViewById(R.id.alert_image);
            mAlertNewDot = itemView.findViewById(R.id.alert_unread_dot);
            mAlertClickButton = itemView.findViewById(R.id.alert_click_image);
            mAlertTitle = itemView.findViewById(R.id.alert_title);
            mAlertMessage = itemView.findViewById(R.id.alert_message);
        }

        public void bind(Alert alert, PublishSubject<Alert> onClickSubject) {
            mAlertImage.setImageResource(getValidAlertImage(alert.getAlertType()));
            mAlertNewDot.setVisibility(alert.getNewBooking() ? View.VISIBLE : View.GONE);
            mAlertClickButton.setOnClickListener(view -> onClickSubject.onNext(alert));
            mAlertMessage.setText(alert.getNotificationText());
        }

        private int getValidAlertImage(int alertType) {
            int drawableResource = R.drawable.ic_alert_system;
            switch (alertType) {
                case Alert.TYPE_BOOKING:
                    drawableResource = R.drawable.ic_alert_booking;
                    break;
                case Alert.TYPE_OVERDUE:
                    drawableResource = R.drawable.ic_alert_overdue;
                    break;
                case Alert.TYPE_REMINDER:
                    drawableResource = R.drawable.ic_alert_reminder;
                    break;
                case Alert.TYPE_REVIEWS:
                    drawableResource = R.drawable.ic_alert_review;
                    break;
                case Alert.TYPE_SYSTEM:
                    drawableResource = R.drawable.ic_alert_system;
                    break;
            }
            return drawableResource;
        }
    }
}
