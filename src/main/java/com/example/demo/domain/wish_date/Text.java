package com.example.demo.domain.wish_date;

public class Text {

    private String text;
    final private static int MIN_CHAR = 1;
    final private static int MAX_CHAR = 500;

    public Text(String text) throws IllegalStateException {
        if(text.length() >= MIN_CHAR && text.length() <= MAX_CHAR) {
            this.text = text;
        } else {
            throw new IllegalStateException("text must be within 500 characters.");
        }
    }

    public String getValue() {return this.text;}

}
