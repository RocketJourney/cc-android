package com.rocketjourney.controlcenterrocketjourney.home.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rocketjourney.controlcenterrocketjourney.home.objects.SpotUsersData;

public class SpotUsersResponse {

    @SerializedName("data")
    @Expose
    private SpotUsersData data;

    /**
     * No args constructor for use in serialization
     */
    public SpotUsersResponse() {
    }

    /**
     * @param data
     */
    public SpotUsersResponse(SpotUsersData data) {
        super();
        this.data = data;
    }

    public SpotUsersData getData() {
        return data;
    }

    public void setData(SpotUsersData data) {
        this.data = data;
    }
}
