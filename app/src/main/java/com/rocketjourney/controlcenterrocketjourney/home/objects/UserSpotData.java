package com.rocketjourney.controlcenterrocketjourney.home.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserSpotData implements Serializable {

    @SerializedName("week_streak")
    @Expose
    private Integer weekStreak;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("streak")
    @Expose
    private Integer streak;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("max_streak")
    @Expose
    private Integer maxStreak;
    @SerializedName("last_name")
    @Expose
    private Object lastName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("danger_zone")
    @Expose
    private Boolean dangerZone;
    @SerializedName("cheat_days")
    @Expose
    private Integer cheatDays;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserSpotData() {
    }

    /**
     *
     * @param lastName
     * @param streak
     * @param userId
     * @param profilePic
     * @param weekStreak
     * @param firstName
     * @param maxStreak
     * @param dangerZone
     * @param cheatDays
     */
    public UserSpotData(Integer weekStreak, Integer userId, Integer streak, String profilePic, Integer maxStreak, Object lastName, String firstName, Boolean dangerZone, Integer cheatDays) {
        super();
        this.weekStreak = weekStreak;
        this.userId = userId;
        this.streak = streak;
        this.profilePic = profilePic;
        this.maxStreak = maxStreak;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dangerZone = dangerZone;
        this.cheatDays = cheatDays;
    }

    public Integer getWeekStreak() {
        return weekStreak;
    }

    public void setWeekStreak(Integer weekStreak) {
        this.weekStreak = weekStreak;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStreak() {
        return streak;
    }

    public void setStreak(Integer streak) {
        this.streak = streak;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Integer getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(Integer maxStreak) {
        this.maxStreak = maxStreak;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getDangerZone() {
        return dangerZone;
    }

    public void setDangerZone(Boolean dangerZone) {
        this.dangerZone = dangerZone;
    }

    public Integer getCheatDays() {
        return cheatDays;
    }

    public void setCheatDays(Integer cheatDays) {
        this.cheatDays = cheatDays;
    }
}
