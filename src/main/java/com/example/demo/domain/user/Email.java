package com.example.demo.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private String email;

    public Email(String email) throws IllegalArgumentException{

        //正規表現チェック
        Pattern pattern = Pattern.compile("[\\w.-]+@[\\w-]+\\.[\\w.-]+");
        Matcher matcher = pattern.matcher(email);

        if(matcher.find()) {

            this.email = email;

        } else {

            throw new IllegalArgumentException("Email format is incorrect");

        }
    }

    public String getValue() {return email;}

}
