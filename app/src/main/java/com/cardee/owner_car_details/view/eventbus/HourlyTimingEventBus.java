package com.cardee.owner_car_details.view.eventbus;


public class HourlyTimingEventBus {

    private static HourlyTimingEventBus INSTANCE;

    private Listener listener;

    private HourlyTimingEventBus() {

    }

    public static HourlyTimingEventBus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HourlyTimingEventBus();
        }
        return INSTANCE;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onSave(TimingSaveEvent event);
    }

    public void post(TimingSaveEvent event) {
        if (listener != null) {
            listener.onSave(event);
        }
    }
}
