package com.example.demo.domain.usergroupthread;

public class Text {

    private String text;
    final private static int MIN_CHAR = 1;
    final private static int MAX_CHAR = 500;

    public Text(String text) throws IllegalArgumentException {
        if(text.length() >= MIN_CHAR && text.length() <= MAX_CHAR) {
            this.text = text;
        } else {
            throw new IllegalStateException("text must be less than 500 characters.");
        }
    }

    public String getValue() {return this.text;}
}
