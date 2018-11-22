package com.rocketjourney.controlcenterrocketjourney.login.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rocketjourney.controlcenterrocketjourney.structure.objects.Club;

import java.util.ArrayList;
import java.util.List;

public class SignUpResponseData {

    @SerializedName("jwt")
    @Expose
    private String jwt;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("club")
    @Expose
    private Club club;
    @SerializedName("clubs")
    @Expose
    private List<Club> clubs = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     */
    public SignUpResponseData() {
    }

    /**
     * @param club
     * @param jwt
     */
    public SignUpResponseData(String jwt, int userId, Club club, List<Club> clubs) {
        super();
        this.jwt = jwt;
        this.userId = userId;
        this.club = club;
        this.clubs = clubs;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
