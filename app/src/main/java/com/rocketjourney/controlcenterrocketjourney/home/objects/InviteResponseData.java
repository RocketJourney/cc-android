package com.rocketjourney.controlcenterrocketjourney.home.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteResponseData {

    @SerializedName("invitation_link")
    @Expose
    private String invitationLink;
    @SerializedName("inserted_at")
    @Expose
    private String insertedAt;
    @SerializedName("expiration_date")
    @Expose
    private String expirationDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public InviteResponseData() {
    }

    /**
     *
     * @param expirationDate
     * @param invitationLink
     * @param insertedAt
     */
    public InviteResponseData(String invitationLink, String insertedAt, String expirationDate) {
        super();
        this.invitationLink = invitationLink;
        this.insertedAt = insertedAt;
        this.expirationDate = expirationDate;
    }

    public String getInvitationLink() {
        return invitationLink;
    }

    public void setInvitationLink(String invitationLink) {
        this.invitationLink = invitationLink;
    }

    public String getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(String insertedAt) {
        this.insertedAt = insertedAt;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
