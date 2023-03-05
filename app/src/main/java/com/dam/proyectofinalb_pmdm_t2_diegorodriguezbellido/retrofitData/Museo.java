package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

// Le he cambiado el nombre a la clase porque con Graph no me estaba
// quedando claro que objeto era el que estaba representando y sin embargo
// con Museo sí que me queda claro.
public class Museo implements Serializable {

    @SerializedName("@id")
    @Expose
    private String id;
    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id2;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("organization")
    @Expose
    private Organization organization;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


    @Override
    public String toString() {
        return "Museo{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", id2='" + id2 + '\'' +
                ", title='" + title + '\'' +
                ", relation='" + relation + '\'' +
                ", address=" + address +
                ", location=" + location +
                ", organization=" + organization +
                '}';
    }
}
