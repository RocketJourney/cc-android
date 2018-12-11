package com.rocketjourney.controlcenterrocketjourney.home.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rocketjourney.controlcenterrocketjourney.home.objects.Invitation;

public class InviteRequest {

    @SerializedName("invitation")
    @Expose
    private Invitation invitation;

    /**
     * No args constructor for use in serialization
     */
    public InviteRequest() {
    }

    /**
     * @param invitation
     */
    public InviteRequest(Invitation invitation) {
        super();
        this.invitation = invitation;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

}
