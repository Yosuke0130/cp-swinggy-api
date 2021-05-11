package com.example.demo.domain.user;

import java.net.URL;

public class User {

    private final int userId;
    private UserProfileId userProfileId;
    private Name firstName;
    private Name lastName;
    private ScreenName screenName;
    private Email email;
    private Tel tel;
    private ProfileImageURL profileImageURL;

    public User(int userId, String firstName, String lastName, String screenName, String email, String tel, URL profileImageUrl) throws IllegalArgumentException {
        this.userId = userId;
        this.firstName = new Name(firstName);
        this.lastName = new Name(lastName);
        this.screenName = new ScreenName(screenName);
        this.email = new Email(email);
        this.tel = new Tel(tel);
        this.userProfileId = new UserProfileId();
        this.profileImageURL = new ProfileImageURL(profileImageUrl);
    }

    public User(int userId, String userProfileId, String firstName, String lastName, String screenName, String email, String tel, URL profileImageUrl) throws IllegalArgumentException {
        this.userId = userId;
        this.userProfileId = new UserProfileId(userProfileId);
        this.firstName = new Name(firstName);
        this.lastName = new Name(lastName);
        this.screenName = new ScreenName(screenName);
        this.email = new Email(email);
        this.tel = new Tel(tel);
        this.profileImageURL = new ProfileImageURL(profileImageUrl);
    }

    public int getUserId() {return userId;}
    public UserProfileId getUserProfileId() {return userProfileId;}
    public Name getFirstName() {return firstName;}
    public Name getLastName() {return lastName;}
    public ScreenName getScreenName() {return screenName;}
    public Email getEmail() {return email;}
    public Tel getTel() {return tel;}
    public ProfileImageURL getProfileImageURL() {return profileImageURL;}

    public void setProfileImageURL(URL profileImageURL) {this.profileImageURL = new ProfileImageURL(profileImageURL);}

}
