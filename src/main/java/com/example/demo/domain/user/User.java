package com.example.demo.domain.user;

public class User {

    private final int userId;
    private final UserProfileId userProfileId;
    private Name firstName;
    private Name lastName;
    private ScreenName screenName;
    private Email email;
    private Tel tel;
    private String profileImagePath;

    public User(int userId, String firstName, String lastName, String screenName, String email, String tel) throws IllegalArgumentException {

        this.userId = userId;
        this.firstName = new Name(firstName);
        this.lastName = new Name(lastName);
        this.screenName = new ScreenName(screenName);
        this.email = new Email(email);
        this.tel = new Tel(tel);
        this.userProfileId = new UserProfileId();
        this.profileImagePath = null;
    }

    public int getUserId() {return userId;}
    public String getUserProfileId() {return userProfileId.getValue();}
    public String getFirstName() {return firstName.getValue();}
    public String getLastName() {return lastName.getValue();}
    public String getScreenName() {return screenName.getValue();}
    public String getEmail() {return email.getValue();}
    public String getTel() {return tel.getValue();}
    public String getProfileImagePath() {return this.profileImagePath;}

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

}
