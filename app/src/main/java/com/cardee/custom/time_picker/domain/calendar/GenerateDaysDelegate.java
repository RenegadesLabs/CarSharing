package com.cardee.custom.time_picker.domain.calendar;

import com.cardee.custom.time_picker.model.Hour;
import com.cardee.custom.time_picker.model.Day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class GenerateDaysDelegate {

    List<Day> onGenerateFromCurrent(int range) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return onGenerateMonths(range, calendar);
    }

    private List<Day> onGenerateMonths(int range, Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        List<Day> days = new ArrayList<>(range);
        for (int i = 0; i < range; i++) {
            Date day = calendar.getTime();
            List<Hour> hours = new ArrayList<>(24);
            for (int j = 1; j <= 24; j++) {
                Hour hour = Hour.from(calendar.getTime());
                hours.add(hour);
                calendar.add(Calendar.HOUR_OF_DAY, 1);
            }
            days.add(new Day(hours, day));
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }
}
