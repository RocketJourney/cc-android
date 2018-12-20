package com.rocketjourney.controlcenterrocketjourney.structure.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushNotificationPayload {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("spot_id")
    @Expose
    private Integer spotId;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     */
    public PushNotificationPayload() {
    }

    /**
     * @param userId
     * @param spotId
     * @param type
     */
    public PushNotificationPayload(Integer userId, Integer spotId, String type) {
        super();
        this.userId = userId;
        this.spotId = spotId;
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSpotId() {
        return spotId;
    }

    public void setSpotId(Integer spotId) {
        this.spotId = spotId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
