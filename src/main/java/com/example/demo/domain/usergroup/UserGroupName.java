package com.example.demo.domain.usergroup;

public class UserGroupName {

    private String userGroupName;
    final private static int MAX_LENGTH = 32;
    final private static int MIN_LENGTH = 0;

    public UserGroupName(String userGroupName) throws IllegalArgumentException{
        if(userGroupName.length() > MIN_LENGTH && userGroupName.length() < MAX_LENGTH) {
            this.userGroupName = userGroupName;
        } else {
            throw new IllegalArgumentException("length has to be 1-32 & characters, numbers, and _ are approved to use.");
        }
    }

    public String getValue() {return this.userGroupName;}

}
