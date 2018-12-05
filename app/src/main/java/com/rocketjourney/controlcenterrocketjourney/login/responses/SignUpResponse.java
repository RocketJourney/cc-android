package com.rocketjourney.controlcenterrocketjourney.login.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rocketjourney.controlcenterrocketjourney.login.objects.SignUpResponseData;

public class SignUpResponse {

    @SerializedName("data")
    @Expose
    private SignUpResponseData data;

    /**
     * No args constructor for use in serialization
     */
    public SignUpResponse() {
    }

    /**
     * @param data
     */
    public SignUpResponse(SignUpResponseData data) {
        super();
        this.data = data;
    }

    public SignUpResponseData getData() {
        return data;
    }

    public void setData(SignUpResponseData data) {
        this.data = data;
    }
}
