package com.cardee.data_source.remote.api.profile.request;


import com.cardee.data_source.remote.api.profile.response.entity.NotificationSettings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileSettingsRequest {

    @Expose
    @SerializedName("settings")
    private NotificationSettings[] settings;

    public ProfileSettingsRequest() {

    }

    public NotificationSettings[] getSettings() {
        return settings;
    }

    public void setSettings(NotificationSettings[] settings) {
        this.settings = settings;
    }
}
