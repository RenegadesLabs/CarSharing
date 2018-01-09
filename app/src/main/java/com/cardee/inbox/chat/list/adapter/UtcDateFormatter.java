package com.cardee.inbox.chat.list.adapter;

import java.util.Date;

public interface UtcDateFormatter {

    String formatDate(String utcDate);

    String getDivider();

    interface ChatMessageFormatter extends UtcDateFormatter {

        String formatDividerDate(String utcDate);

        String toISO8601();

        boolean hasSameDate(String firstDate, String nextDate);

        boolean sameWithCurrentDate(String date);
    }
}
