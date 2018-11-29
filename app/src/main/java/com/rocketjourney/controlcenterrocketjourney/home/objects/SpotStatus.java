package com.rocketjourney.controlcenterrocketjourney.home.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpotStatus {

    @SerializedName("total_users_with_team")
    @Expose
    private Integer totalUsersWithTeam;
    @SerializedName("total_users_checked_in")
    @Expose
    private Integer totalUsersCheckedIn;
    @SerializedName("spot_count")
    @Expose
    private Integer spotCount;

    /**
     * No args constructor for use in serialization
     *
     */
    public SpotStatus() {
    }

    /**
     *
     * @param spotCount
     * @param totalUsersWithTeam
     * @param totalUsersCheckedIn
     */
    public SpotStatus(Integer totalUsersWithTeam, Integer totalUsersCheckedIn, Integer spotCount) {
        super();
        this.totalUsersWithTeam = totalUsersWithTeam;
        this.totalUsersCheckedIn = totalUsersCheckedIn;
        this.spotCount = spotCount;
    }

    public Integer getTotalUsersWithTeam() {
        return totalUsersWithTeam;
    }

    public void setTotalUsersWithTeam(Integer totalUsersWithTeam) {
        this.totalUsersWithTeam = totalUsersWithTeam;
    }

    public Integer getTotalUsersCheckedIn() {
        return totalUsersCheckedIn;
    }

    public void setTotalUsersCheckedIn(Integer totalUsersCheckedIn) {
        this.totalUsersCheckedIn = totalUsersCheckedIn;
    }

    public Integer getSpotCount() {
        return spotCount;
    }

    public void setSpotCount(Integer spotCount) {
        this.spotCount = spotCount;
    }
}
