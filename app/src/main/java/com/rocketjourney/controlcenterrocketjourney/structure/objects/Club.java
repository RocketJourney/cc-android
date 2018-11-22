package com.rocketjourney.controlcenterrocketjourney.structure.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Club extends RealmObject {

    @SerializedName("namespace")
    @Expose
    private String namespace;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logo")
    @Expose
    private String logo;

    /**
     * No args constructor for use in serialization
     *
     */
    public Club() {
    }

    /**
     *
     * @param id
     * @param name
     * @param namespace
     * @param logo
     */
    public Club(String namespace, String name, Integer id, String logo) {
        super();
        this.namespace = namespace;
        this.name = name;
        this.id = id;
        this.logo = logo;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
