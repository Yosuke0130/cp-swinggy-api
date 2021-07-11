package com.example.demo.domain.user;

import java.util.Optional;

public class Tel {

    private Optional<String> tel;
    final private static int MAX_DIGIT = 11;
    final private static int MIN_DIGIT = 10;

    public Tel(Optional<String> tel) throws IllegalArgumentException {

        //半角数字のみ、桁数だけチェック
        if (isDigitValid(tel)) {

            this.tel = tel;

        } else {

            throw new IllegalArgumentException("digit of tel num has to be 10-11");

        }
    }

    public Optional<String> getValue() {return tel;}

    public boolean isDigitValid(Optional<String> tel) {

        if(tel.isPresent()) {

            return MAX_DIGIT >= tel.get().length() && MIN_DIGIT <= tel.get().length();
        } else {
            return true;
        }
    }

}
