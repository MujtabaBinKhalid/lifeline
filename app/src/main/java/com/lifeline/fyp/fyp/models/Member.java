package com.lifeline.fyp.fyp.models;

/**
 * Created by Mujtaba_khalid on 1/29/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("memberId")
    @Expose
    private Integer memberId;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName( "isVerfied" )
    @Expose
    private boolean isVerfied;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("height")
    @Expose
    private String height;

    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("instagramId")
    @Expose
    private String instagramId;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("joiningDate")
    @Expose
    private String joiningDate;

    @SerializedName("faceShape")
    @Expose
    private String faceShape;

    @SerializedName("hairType")
    @Expose
    private String hairType;

    @SerializedName("skinColor")
    @Expose
    private String skinColor;

    @SerializedName("hasRecentNotifications")
    @Expose
    private String hasRecentNotifications;


    @SerializedName( "facebookId" )
    @Expose
    private String facebookId;
    // default.

    public Member(){

    }
    // constructor for sign up
    public Member(String firstName, String lastName, String username, String gender, Integer age, String email, String password,String facebookId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.password = password;
        this.joiningDate = joiningDate;
        this.facebookId = facebookId;
    }

    public Member(Integer id,String email,String email2,String height,String weight){
     this.memberId = id;
     this.email = email;
     this.email = email2;
     this.height = height;
     this.weight = weight;
    }


  // constructor for hairtype
    public Member(String faceShape, String hairType) {
        this.faceShape = faceShape;
        this.hairType = hairType;

    }

    // constructor for cloths.
    public Member(Integer memberId,String weight, String height, String skinColor) {
        this.weight = weight;
        this.height = height;
       this.memberId = memberId;
       this.skinColor = skinColor;

    }


    // constructor for sunglasses.
    public Member(String faceShape) {

        this.faceShape = faceShape;

    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getInstagramId() {
        return instagramId;
    }

    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public void setFaceShape(String faceShape) {
        this.faceShape = faceShape;
    }

    public String getHairType() {
        return hairType;
    }

    public void setHairType(String hairType) {
        this.hairType = hairType;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getHasRecentNotifications() {
        return hasRecentNotifications;
    }

    public boolean isVerfied() {
        return isVerfied;
    }
}
