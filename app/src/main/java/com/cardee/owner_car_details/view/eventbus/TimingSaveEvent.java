package com.cardee.owner_car_details.view.eventbus;


public class TimingSaveEvent {

    private final int hourBegin;
    private final int hourEnd;

    public TimingSaveEvent(int hourBegin, int hourEnd){
        this.hourBegin = hourBegin;
        this.hourEnd = hourEnd;
    }

    public int getHourBegin() {
        return hourBegin;
    }

    public int getHourEnd() {
        return hourEnd;
    }
}
