package com.rocketjourney.controlcenterrocketjourney.login.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpResponseData {

    @SerializedName("jwt")
    @Expose
    private String jwt;
    @SerializedName("club")
    @Expose
    private Club club;
    @SerializedName("clubs")
    @Expose
    private List<Club> clubs = null;

    /**
     * No args constructor for use in serialization
     */
    public SignUpResponseData() {
    }

    /**
     * @param club
     * @param jwt
     */
    public SignUpResponseData(String jwt, Club club, List<Club> clubs) {
        super();
        this.jwt = jwt;
        this.club = club;
        this.clubs = clubs;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
}
