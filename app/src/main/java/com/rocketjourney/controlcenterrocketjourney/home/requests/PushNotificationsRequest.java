package com.rocketjourney.controlcenterrocketjourney.home.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushNotificationsRequest {

    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;

    /**
     * No args constructor for use in serialization
     */
    public PushNotificationsRequest() {
    }

    /**
     * @param deviceToken
     * @param device
     */
    public PushNotificationsRequest(String device, String deviceToken) {
        super();
        this.device = device;
        this.deviceToken = deviceToken;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
