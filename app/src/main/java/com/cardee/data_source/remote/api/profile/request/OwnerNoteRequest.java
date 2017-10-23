package com.cardee.data_source.remote.api.profile.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerNoteRequest {

    @Expose
    @SerializedName("note")
    private String note;

    public OwnerNoteRequest() {

    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
