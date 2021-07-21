package com.example.demo.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenName {

    final private static int MAX_LENGTH = 32;
    final private static int MIN_LENGTH = 4;
    private String screenName;

    public ScreenName(String screenName) throws IllegalArgumentException {

        String re = "^[ぁ-んァ-ン一-龥a-zA-Z0-9_]{" + MIN_LENGTH + "," + MAX_LENGTH + "}$";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(screenName);

        if (matcher.find()) {

            this.screenName = screenName;

        } else {
            throw new IllegalArgumentException("length has to be 4-32 & characters, numbers, and _ are approved to use.");
        }
    }

    public String getValue() {return this.screenName;}

}
