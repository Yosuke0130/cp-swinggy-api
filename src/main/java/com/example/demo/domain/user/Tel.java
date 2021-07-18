package com.example.demo.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tel {

    private String tel;
    final private static int MAX_DIGIT = 11;
    final private static int MIN_DIGIT = 10;

    public Tel(String tel) throws IllegalArgumentException {

        //半角数字のみ、桁数だけチェック
        String re = "^[0-9]{" + MIN_DIGIT + "," + MAX_DIGIT + "}$";
        System.out.println(re);
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(tel);

        if (matcher.find()) {

            this.tel = tel;

        } else {
            throw new IllegalArgumentException("digit of tel num has to be 10-11");
        }
    }

    public String getValue() {return this.tel;}

}
