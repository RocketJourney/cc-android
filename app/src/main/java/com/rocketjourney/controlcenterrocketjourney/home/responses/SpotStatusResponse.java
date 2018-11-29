package com.rocketjourney.controlcenterrocketjourney.home.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rocketjourney.controlcenterrocketjourney.home.objects.SpotStatus;

public class SpotStatusResponse {

    @SerializedName("spotStatus")
    @Expose
    private SpotStatus spotStatus;

    /**
     * No args constructor for use in serialization
     */
    public SpotStatusResponse() {
    }

    /**
     * @param spotStatus
     */
    public SpotStatusResponse(SpotStatus spotStatus) {
        super();
        this.spotStatus = spotStatus;
    }

    public SpotStatus getSpotStatus() {
        return spotStatus;
    }

    public void setSpotStatus(SpotStatus spotStatus) {
        this.spotStatus = spotStatus;
    }

}
