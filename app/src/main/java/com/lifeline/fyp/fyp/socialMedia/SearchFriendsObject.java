package com.lifeline.fyp.fyp.socialMedia;

/**
 * Created by Mujtaba_khalid on 4/17/2018.
 */

public class SearchFriendsObject {

    private Integer id;
    private String name;
    private String email;
    private String profileLink;
    private boolean areFriends;


    public SearchFriendsObject(Integer id, String name, String email, String profileLink, boolean areFriends) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileLink = profileLink;
        this.areFriends = areFriends;
    }

    public SearchFriendsObject(Integer id, String name, String email, String profileLink) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileLink = profileLink;
         }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public boolean getAreFriends() {
        return areFriends;
    }
}
