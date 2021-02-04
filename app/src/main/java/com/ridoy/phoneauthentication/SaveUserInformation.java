package com.ridoy.phoneauthentication;

public class SaveUserInformation {

    private String uId,name,profileImageUrl,sscpoint,sscyear,hscpoint,hscyear;

    public SaveUserInformation() {
    }

    public SaveUserInformation(String uId, String name, String profileImageUrl, String sscpoint, String sscyear, String hscpoint, String hscyear) {
        this.uId = uId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.sscpoint = sscpoint;
        this.sscyear = sscyear;
        this.hscpoint = hscpoint;
        this.hscyear = hscyear;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getSscpoint() {
        return sscpoint;
    }

    public void setSscpoint(String sscpoint) {
        this.sscpoint = sscpoint;
    }

    public String getSscyear() {
        return sscyear;
    }

    public void setSscyear(String sscyear) {
        this.sscyear = sscyear;
    }

    public String getHscpoint() {
        return hscpoint;
    }

    public void setHscpoint(String hscpoint) {
        this.hscpoint = hscpoint;
    }

    public String getHscyear() {
        return hscyear;
    }

    public void setHscyear(String hscyear) {
        this.hscyear = hscyear;
    }



}
