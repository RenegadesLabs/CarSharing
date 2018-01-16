package com.cardee.data_source.remote.api.booking.response;

import com.cardee.data_source.remote.api.BaseResponse;
import com.cardee.data_source.remote.api.booking.response.entity.ChecklistEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ChecklistResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    private ChecklistEntity mChecklist;

    public ChecklistResponse() {

    }

    public ChecklistEntity getChecklist() {
        return mChecklist;
    }

    public void setChecklist(ChecklistEntity checklistEntity) {
        this.mChecklist = checklistEntity;
    }
}
