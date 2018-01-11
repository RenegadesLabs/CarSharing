package com.cardee.owner_bookings.car_checklist.strategy;


import android.view.View;

public abstract class PresentationStrategy {

    protected final View view;
    protected final ActionListener listener;
    private boolean finished = false;

    PresentationStrategy(View view, ActionListener listener) {
        this.view = view;
        this.listener = listener;
    }

    public interface ActionListener {

        void onHandover();

        void onAccurateCancel();

        void onAccurateConfirm();


    }

    public boolean isFinished() {
        return finished;
    }

    public void finish() {
        finished = true;
    }
}
