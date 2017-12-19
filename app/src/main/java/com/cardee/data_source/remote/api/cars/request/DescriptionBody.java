package com.cardee.data_source.remote.api.cars.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DescriptionBody {

    @Expose
    @SerializedName("note")
    private String description;

    public DescriptionBody() {

    }

    public DescriptionBody(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
