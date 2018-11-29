package com.rocketjourney.controlcenterrocketjourney.home.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClubInfo {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("badge_url")
    @Expose
    private String badgeUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public ClubInfo() {
    }

    /**
     *
     * @param id
     * @param badgeUrl
     * @param name
     */
    public ClubInfo(String name, Integer id, String badgeUrl) {
        super();
        this.name = name;
        this.id = id;
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

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

}
