package com.example.demo.domain.prefecture;

import org.springframework.context.ApplicationContextException;

public class RegionId {
    //staticにするとまずい？？
    private static int value;

    public RegionId(int value) {
        if(value < 0 || value > 8) {
            System.out.println("0-8の整数を入力してちょ");
        }
        this.value = value;
        }
    //PrefectureのGetterからのみ呼ばれる
    public static int getValue() {
        return value;
    }
}

