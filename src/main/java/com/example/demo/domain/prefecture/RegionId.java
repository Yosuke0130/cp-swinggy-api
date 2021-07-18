package com.example.demo.domain.prefecture;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

public class RegionId {

    private int value;
    final private static int MIN_VALUE = 1;
    final private static int MAX_VALUE = 8;

    @Autowired
    Logging logger;

    public RegionId(int value) throws IllegalArgumentException {
        if (validateValue(value)) {
            throw new IllegalArgumentException("Input value must be between 1-8");
        }
        this.value = value;
    }

    public int getValue() {return this.value;}

    public boolean validateValue(int value) {
        logger.debug("validation check");
        return value < MIN_VALUE || value > MAX_VALUE;
    }

}
