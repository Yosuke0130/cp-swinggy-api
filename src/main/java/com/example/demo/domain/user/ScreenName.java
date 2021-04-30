package com.example.demo.domain.user;

public class ScreenName {

    final private static int MAX_LENGTH = 32;
    final private static int MIN_LENGTH = 4;
    private String screenName;

    public ScreenName(String screenName) throws IllegalArgumentException{

        if(isLengthValid(screenName)) {

            this.screenName = screenName;

        } else {
            throw new IllegalArgumentException("length has to be 4-32");
        }
    }

    public String getValue() {return screenName;}

    public boolean isLengthValid(String screenName) {

        return screenName.length() > MIN_LENGTH && screenName.length() < MAX_LENGTH;
    }

}
