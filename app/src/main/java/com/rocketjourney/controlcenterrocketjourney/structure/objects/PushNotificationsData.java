package com.rocketjourney.controlcenterrocketjourney.structure.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushNotificationsData {

    @SerializedName("payload")
    @Expose
    private PushNotificationPayload payload;
    @SerializedName("message")
    @Expose
    private PushNotificationMessage message;

    /**
     * No args constructor for use in serialization
     */
    public PushNotificationsData() {
    }

    /**
     * @param message
     * @param payload
     */
    public PushNotificationsData(PushNotificationPayload payload, PushNotificationMessage message) {
        super();
        this.payload = payload;
        this.message = message;
    }

    public PushNotificationPayload getPayload() {
        return payload;
    }

    public void setPayload(PushNotificationPayload payload) {
        this.payload = payload;
    }

    public PushNotificationMessage getMessage() {
        return message;
    }

    public void setMessage(PushNotificationMessage message) {
        this.message = message;
    }

}
