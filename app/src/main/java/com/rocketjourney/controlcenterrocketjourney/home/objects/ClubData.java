package com.rocketjourney.controlcenterrocketjourney.home.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClubData {

    @SerializedName("user")
    @Expose
    private UserData user;
    @SerializedName("club")
    @Expose
    private ClubInfo clubInfo;
    @SerializedName("accesible_spots")
    @Expose
    private List<AccesibleSpot> accesibleSpots = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ClubData() {
    }

    /**
     *
     * @param accesibleSpots
     * @param clubInfo
     * @param user
     */
    public ClubData(UserData user, ClubInfo clubInfo, List<AccesibleSpot> accesibleSpots) {
        super();
        this.user = user;
        this.clubInfo = clubInfo;
        this.accesibleSpots = accesibleSpots;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public ClubInfo getClubInfo() {
        return clubInfo;
    }

    public void setClub(ClubInfo clubInfo) {
        this.clubInfo = clubInfo;
    }

    public List<AccesibleSpot> getAccesibleSpots() {
        return accesibleSpots;
    }

    public void setAccesibleSpots(List<AccesibleSpot> accesibleSpots) {
        this.accesibleSpots = accesibleSpots;
    }
}
