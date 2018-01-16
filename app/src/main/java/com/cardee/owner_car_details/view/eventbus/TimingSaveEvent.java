package com.cardee.owner_car_details.view.eventbus;


public class TimingSaveEvent {

    private final String timeBegin;
    private final String timeEnd;

    public TimingSaveEvent(String timeBegin, String timeEnd){
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public String getTimeEnd() {
        return timeEnd;
    }
}
