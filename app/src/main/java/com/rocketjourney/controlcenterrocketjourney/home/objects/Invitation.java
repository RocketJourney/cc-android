package com.rocketjourney.controlcenterrocketjourney.home.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Invitation {

    @SerializedName("permission")
    @Expose
    private String permission;
    @SerializedName("spots")
    @Expose
    private ArrayList<Integer> spots = null;
    @SerializedName("club_id")
    @Expose
    private Integer clubId;

    /**
     * No args constructor for use in serialization
     *
     */
    public Invitation() {
    }

    /**
     *
     * @param spots
     * @param clubId
     * @param permission
     */
    public Invitation(String permission, ArrayList<Integer> spots, Integer clubId) {
        super();
        this.permission = permission;
        this.spots = spots;
        this.clubId = clubId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public ArrayList<Integer> getSpots() {
        return spots;
    }

    public void setSpots(ArrayList<Integer> spots) {
        this.spots = spots;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }
}
