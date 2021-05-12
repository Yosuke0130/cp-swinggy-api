package com.example.demo.domain.user;

public class Tel {

    private String tel;
    final private static int MAX_DIGIT = 11;
    final private static int MIN_DIGIT = 10;

    public Tel(String tel) throws IllegalArgumentException {

        //半角数字のみ、桁数だけチェック
        if (isDigitValid(tel)) {

            this.tel = tel;

        } else {

            throw new IllegalArgumentException("digit of tel num has to be 10-11");

        }
    }

    public String getValue() {return tel;}

    public boolean isDigitValid(String tel) {

        return MAX_DIGIT >= tel.length() && MIN_DIGIT <= tel.length();
    }

}
