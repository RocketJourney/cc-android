package com.rocketjourney.controlcenterrocketjourney.home.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SpotUsersData {

    @SerializedName("users")
    @Expose
    private ArrayList<UserSpotData> users = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_entries")
    @Expose
    private Integer totalEntries;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("page_number")
    @Expose
    private Integer pageNumber;

    /**
     * No args constructor for use in serialization
     *
     */
    public SpotUsersData() {
    }

    /**
     *
     * @param users
     * @param pageSize
     * @param pageNumber
     * @param totalEntries
     * @param totalPages
     */
    public SpotUsersData(ArrayList<UserSpotData> users, Integer totalPages, Integer totalEntries, Integer pageSize, Integer pageNumber) {
        super();
        this.users = users;
        this.totalPages = totalPages;
        this.totalEntries = totalEntries;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public ArrayList<UserSpotData> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserSpotData> users) {
        this.users = users;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(Integer totalEntries) {
        this.totalEntries = totalEntries;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
