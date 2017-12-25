package com.cardee.domain.inbox.usecase.entity;

public class ChatInfo {

    private String recipientName;

    private String recipientPhotoUrl;

    private String carTitle;

    private String recipientCarUrl;

    private String carLicenseNumber;

    private String bookingTimeBegin;

    private String bookingTimeEnd;

    private ChatInfo() {
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientPhotoUrl() {
        return recipientPhotoUrl;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public String getRecipientCarUrl() {
        return recipientCarUrl;
    }

    public String getCarLicenseNumber() {
        return carLicenseNumber;
    }

    public String getBookingTimeBegin() {
        return bookingTimeBegin;
    }

    public String getBookingTimeEnd() {
        return bookingTimeEnd;
    }

    public static class Builder {

        private ChatInfo chatInfo;

        public Builder() {
            chatInfo = new ChatInfo();
        }

        public ChatInfo.Builder withRecipientName(String recipientName) {
            chatInfo.recipientName = recipientName;
            return this;
        }

        public ChatInfo.Builder withRecipientPhoto(String recipientPhoto) {
            chatInfo.recipientPhotoUrl = recipientPhoto;
            return this;
        }

        public ChatInfo.Builder withCarTitle(String carTitle) {
            chatInfo.carTitle = carTitle;
            return this;
        }

        public ChatInfo.Builder withCarUrl(String recipientCarUrl) {
            chatInfo.recipientCarUrl = recipientCarUrl;
            return this;
        }

        public ChatInfo.Builder withLicenseNumber(String carLicenseNumber) {
            chatInfo.carLicenseNumber = carLicenseNumber;
            return this;
        }

        public ChatInfo.Builder withBookingBegin(String bookingTimeBegin) {
            chatInfo.bookingTimeBegin = bookingTimeBegin;
            return this;
        }

        public ChatInfo.Builder withBookingEnd(String bookingTimeEnd) {
            chatInfo.bookingTimeEnd = bookingTimeEnd;
            return this;
        }

        public ChatInfo build() {
            return chatInfo;
        }
    }
}
