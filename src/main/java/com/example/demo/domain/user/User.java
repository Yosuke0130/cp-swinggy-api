package com.example.demo.domain.user;

import java.util.UUID;

public class User {

    private final int userId;
    private final UserProfileId userProfileId;
    private String firstName;
    private String lastName;
    private ScreenName screenName;
    private Email email;
    private Tel tel;

    public User(int userId, String firstName, String lastName, String screenName, String email, String tel) throws IllegalArgumentException {

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.screenName = new ScreenName(screenName);
        this.email = new Email(email);
        this.tel = new Tel(tel);
        //UUID発行
        UUID uuid = UUID.randomUUID();
        this.userProfileId = new UserProfileId();
        userProfileId.setValue(uuid.toString());
    }

    public int getUserId() {return userId;}
    public String getUserProfileId() {return userProfileId.getValue();}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getScreenName() {return screenName.getValue();}
    public String getEmail() {return email.getValue();}
    public String getTel() {return tel.getValue();}

}
