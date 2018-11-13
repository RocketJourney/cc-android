package com.rocketjourney.controlcenterrocketjourney.login.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Club {

    @SerializedName("namespace")
    @Expose
    private String namespace;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;

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
     */
    public Club(String namespace, String name, Integer id) {
        super();
        this.namespace = namespace;
        this.name = name;
        this.id = id;
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
}
