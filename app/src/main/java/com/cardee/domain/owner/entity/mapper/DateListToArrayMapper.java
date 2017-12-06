package com.cardee.domain.owner.entity.mapper;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateListToArrayMapper {

    private final SimpleDateFormat formatter;

    public DateListToArrayMapper(String datePattern) {
        formatter = new SimpleDateFormat(datePattern);
    }

    public String[] transform(List<Date> dates) {
        if (dates == null) {
            return null;
        }
        String[] datesArray = new String[dates.size()];
        for (int i = 0; i < datesArray.length; i++) {
            datesArray[i] = formatter.format(dates.get(i));
        }
        return datesArray;
    }

}
