package com.rocketjourney.controlcenterrocketjourney.login.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rocketjourney.controlcenterrocketjourney.login.objects.UserData;

public class SignUpRequest {

    @SerializedName("user")
    @Expose
    private UserData user;
    @SerializedName("invitation")
    @Expose
    private String invitation;

    /**
     * No args constructor for use in serialization
     *
     */
    public SignUpRequest() {
    }

    /**
     *
     * @param invitation
     * @param user
     */
    public SignUpRequest(UserData user, String invitation) {
        super();
        this.user = user;
        this.invitation = invitation;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getInvitation() {
        return invitation;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation;
    }
}
