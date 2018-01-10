package com.cardee.owner_bookings.strategy;


import android.view.View;

import com.cardee.domain.bookings.BookingState;

public abstract class PresentationStrategy {

    protected final View view;
    protected final ActionListener listener;
    private boolean finished = false;

    PresentationStrategy(View view, ActionListener listener) {
        this.view = view;
        this.listener = listener;
    }

    public interface ActionListener {

        void onDecline();

        void onAccept();

        void onCancel();

        void onHandOver();

        void onCancelHandOver();

        void onCompleted();

        void onShowProfile();

        void onCall();

        void onChat();

        void onRatingEdit();
    }

    public boolean isFinished() {
        return finished;
    }

    public void finish() {
        finished = true;
    }

    public abstract BookingState getType();
}
