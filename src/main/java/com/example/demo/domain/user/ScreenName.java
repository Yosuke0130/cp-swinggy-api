package com.example.demo.domain.user;

public class ScreenName {

    final private static int MAX_LENGTH = 32;
    final private static int MIN_LENGTH = 4;
    private String screenName;

    public ScreenName(String screenName) throws IllegalArgumentException{

        if(validateLength(screenName)) {

            throw new IllegalArgumentException("length has to be 4-32");
        }

        this.screenName = screenName;
    }

    public String getValue() {return screenName;}

    public boolean validateLength(String screenName) {

        return screenName.length() < MIN_LENGTH || screenName.length() > MAX_LENGTH;
    }

}
