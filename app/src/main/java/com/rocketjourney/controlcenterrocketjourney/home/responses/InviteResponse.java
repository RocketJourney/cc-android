package com.rocketjourney.controlcenterrocketjourney.home.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rocketjourney.controlcenterrocketjourney.home.objects.InviteResponseData;

public class InviteResponse {

    @SerializedName("data")
    @Expose
    private InviteResponseData data;

    /**
     * No args constructor for use in serialization
     *
     */
    public InviteResponse() {
    }

    /**
     *
     * @param data
     */
    public InviteResponse(InviteResponseData data) {
        super();
        this.data = data;
    }

    public InviteResponseData getData() {
        return data;
    }

    public void setData(InviteResponseData data) {
        this.data = data;
    }
}
