package com.cardee.data_source.inbox.service.controller;

final class Key {

    public static final String TAG = "tag";

    static final String PROFILE_TYPE = "profile_type";
    public static final String CHAT_COUNT = "chat_cnt";
    public static final String ALERT_COUNT = "alert_cnt";
    static final String DATE_CREATED = "date_created";

    public static class Chat {

        static final String SENDER = "sender";
        static final String MESSAGE = "message";
        static final String MESSAGE_ID = "message_id";

        static final String CHAT_ID = "chat_id";

        static final String NEW_MESSAGES = "new_messages";
    }

    public static class Alert {

        static final String TYPE_NOTIFICATION = "type_notification";
        static final String MESSAGE_BODY = "message_body";
        static final String MESSAGE_TITLE = "message_title";
        static final String OBJECT_ID = "object_id";
        static final String USER_ALERT_ID = "user_alert_id";
    }
}
