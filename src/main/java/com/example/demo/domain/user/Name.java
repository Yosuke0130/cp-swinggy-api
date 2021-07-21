package com.example.demo.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Name {

    private String name;

    public Name(String name) throws IllegalArgumentException {
        //正規表現チェック 全角ひらがな、カタカナ、漢字、半角英小文字、半角英大文字
        Pattern pattern = Pattern.compile("^[ぁ-んァ-ン一-龥a-zA-Z]+$");
        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {

            this.name = name;

        } else {

            throw new IllegalArgumentException("Name format is incorrect");

        }
    }

    public String getValue() {return this.name;}
}
