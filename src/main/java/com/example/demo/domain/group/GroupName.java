package com.example.demo.domain.group;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupName {

    private String groupName;
    final private static int MAX_LENGTH = 32;
    final private static int MIN_LENGTH = 1;

    public GroupName(String groupName) throws IllegalArgumentException{
        // validate limit of characters, kinds of characters
        String re = "^[ぁ-んァ-ン一-龥a-zA-Z0-9_]{" + MIN_LENGTH + "," + MAX_LENGTH + "}$";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            this.groupName = groupName;
        } else {
            throw new IllegalArgumentException("length has to be 1-32 & characters, numbers, and _ are approved to use.");
        }
    }

    public String getValue() {return this.groupName;}

}
