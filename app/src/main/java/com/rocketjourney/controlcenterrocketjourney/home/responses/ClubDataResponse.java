package com.rocketjourney.controlcenterrocketjourney.home.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rocketjourney.controlcenterrocketjourney.home.objects.ClubData;

public class ClubDataResponse {

    @SerializedName("data")
    @Expose
    private ClubData data;

    /**
     * No args constructor for use in serialization
     *
     */
    public ClubDataResponse() {
    }

    /**
     *
     * @param data
     */
    public ClubDataResponse(ClubData data) {
        super();
        this.data = data;
    }

    public ClubData getData() {
        return data;
    }

    public void setData(ClubData data) {
        this.data = data;
    }
}
