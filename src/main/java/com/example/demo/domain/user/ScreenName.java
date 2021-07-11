package com.example.demo.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenName {

    final private static int MAX_LENGTH = 32;
    final private static int MIN_LENGTH = 4;
    private String screenName;

    public ScreenName(String screenName) throws IllegalArgumentException {

        //TODO:正規表現でチェック、記号は＿のみ
        Pattern pattern = Pattern.compile("^[ぁ-んァ-ン一-龥a-zA-Z0-9_]+$");
        Matcher matcher = pattern.matcher(screenName);

        if (isLengthValid(screenName) && matcher.find()) {

            this.screenName = screenName;

        } else {
            throw new IllegalArgumentException("length has to be 4-32 & characters, numbers, and _ are approved to use.");
        }
    }

    public String getValue() {return screenName;}

    public boolean isLengthValid(String screenName) {

        return screenName.length() > MIN_LENGTH && screenName.length() < MAX_LENGTH;
    }

}
