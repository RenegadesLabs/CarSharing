package com.cardee.owner_car_details.view.eventbus;

public class DailyTimingEventBus {

    private static DailyTimingEventBus INSTANCE;

    private DailyTimingEventBus.Listener listener;

    private DailyTimingEventBus() {

    }

    public static DailyTimingEventBus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DailyTimingEventBus();
        }
        return INSTANCE;
    }

    public void setListener(DailyTimingEventBus.Listener listener) {
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
