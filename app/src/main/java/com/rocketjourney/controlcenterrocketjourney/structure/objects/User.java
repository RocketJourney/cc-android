package com.rocketjourney.controlcenterrocketjourney.structure.objects;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    int id = 0;

    String email = "";
    String firstName = "";
    String lastName = "";
    String token = "";
    Club currentClub;
    RealmList<Club> clubs = new RealmList();

    public User() {
    }

    public User(int id, String email, String firstName, String lastName, String token, Club currentClub, RealmList<Club> clubs) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.currentClub = currentClub;
        this.clubs = clubs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Club getCurrentClub() {
        return currentClub;
    }

    public void setCurrentClub(Club currentClub) {
        this.currentClub = currentClub;
    }

    public RealmList<Club> getClubs() {
        return clubs;
    }

    public void setClubs(RealmList<Club> clubs) {
        this.clubs = clubs;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", token='" + token + '\'' +
                ", currentClub=" + currentClub +
                ", clubs=" + clubs +
                '}';
    }
}
