package com.rocketjourney.controlcenterrocketjourney.structure.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PushNotificationMessage {

    @SerializedName("loc-args")
    @Expose
    private List<String> locArgs = null;
    @SerializedName("loc-key")
    @Expose
    private String locKey;
    @SerializedName("body")
    @Expose
    private String body;

    /**
     * No args constructor for use in serialization
     */
    public PushNotificationMessage() {
    }

    /**
     * @param body
     * @param locArgs
     * @param locKey
     */
    public PushNotificationMessage(List<String> locArgs, String locKey, String body) {
        super();
        this.locArgs = locArgs;
        this.locKey = locKey;
        this.body = body;
    }

    public List<String> getLocArgs() {
        return locArgs;
    }

    public void setLocArgs(List<String> locArgs) {
        this.locArgs = locArgs;
    }

    public String getLocKey() {
        return locKey;
    }

    public void setLocKey(String locKey) {
        this.locKey = locKey;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
