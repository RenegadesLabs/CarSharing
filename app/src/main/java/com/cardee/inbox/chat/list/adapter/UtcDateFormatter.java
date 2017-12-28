package com.cardee.inbox.chat.list.adapter;

public interface UtcDateFormatter {

    String formatDate(String utcDate);

    String getDivider();

    interface ChatMessageFormatter extends UtcDateFormatter {

        String formatDividerDate(String utcDate);

        boolean hasSameDate(String firstDate, String nextDate);

        boolean sameWithCurrentDate(String date);
    }
}
