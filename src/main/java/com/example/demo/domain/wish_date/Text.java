package com.example.demo.domain.wish_date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {

    private String text;
    final private static int MIN_CHAR = 1;
    final private static int MAX_CHAR = 500;

    public Text(String text) throws IllegalStateException {

        String re = "^.{" + MIN_CHAR + "," + MAX_CHAR + "}$";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            this.text = text;
        } else {
            throw new IllegalStateException("text must be within 500 characters.");
        }
    }

    public String getValue() {return this.text;}

}
