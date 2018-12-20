package com.rocketjourney.controlcenterrocketjourney.home.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccesibleSpot implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("club_id")
    @Expose
    private Integer clubId;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("badge_url")
    @Expose
    private String badgeUrl;

    /**
     * No args constructor for use in serialization
     */
    public AccesibleSpot() {
    }

    /**
     * @param id
     * @param clubId
     * @param badgeUrl
     * @param name
     * @param branchName
     */
    public AccesibleSpot(String name, Integer id, Integer clubId, String branchName, String badgeUrl) {
        super();
        this.name = name;
        this.id = id;
        this.clubId = clubId;
        this.branchName = branchName;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }
}
