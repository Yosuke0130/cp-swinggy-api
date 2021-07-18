package com.example.demo.domain.user;

import java.net.URL;
import java.util.Optional;

public class User {

    private final String userId;
    private UserProfileId userProfileId;
    private Name firstName;
    private Name lastName;
    private ScreenName screenName;
    private Email email;
    private Optional<Tel> tel;
    private ProfileImageURL profileImageURL;

    public User(String userId, String firstName, String lastName, String screenName, String email, Optional<String> tel, URL profileImageUrl) throws IllegalArgumentException {
        this.userId = userId;
        this.userProfileId = new UserProfileId();
        this.firstName = new Name(firstName);
        this.lastName = new Name(lastName);
        this.screenName = new ScreenName(screenName);
        this.email = new Email(email);
        if(tel.isPresent()) {
            this.tel = Optional.of(new Tel(tel.get()));
        } else {
            //todo:telが空の場合
            this.tel = Optional.empty();
        }
        this.profileImageURL = new ProfileImageURL(profileImageUrl);
    }

    public User(String userId, String userProfileId, String firstName, String lastName, String screenName, String email, Optional<String> tel, URL profileImageUrl) throws IllegalArgumentException {
        this.userId = userId;
        this.userProfileId = new UserProfileId(userProfileId);
        this.firstName = new Name(firstName);
        this.lastName = new Name(lastName);
        this.screenName = new ScreenName(screenName);
        this.email = new Email(email);
        if(tel.isPresent()) {
            this.tel = Optional.of(new Tel(tel.get()));
        } else {
            this.tel = Optional.empty();
        }
        this.profileImageURL = new ProfileImageURL(profileImageUrl);
    }

    //todo:this
    public String getUserId() {return this.userId;}
    public UserProfileId getUserProfileId() {return this.userProfileId;}
    public Name getFirstName() {return this.firstName;}
    public Name getLastName() {return this.lastName;}
    public ScreenName getScreenName() {return this.screenName;}
    public Email getEmail() {return this.email;}
    public Optional<Tel> getTel() {return this.tel;}
    public ProfileImageURL getProfileImageURL() {return this.profileImageURL;}
    public void setProfileImageURL(URL profileImageURL) {this.profileImageURL = new ProfileImageURL(profileImageURL);}

}
