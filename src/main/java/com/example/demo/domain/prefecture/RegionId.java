package com.example.demo.domain.prefecture;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.CREATED)
public class RegionId {

    private int value;
    final private static int MIN_VALUE = 1;
    final private static int MAX_VALUE = 8;

    @Autowired
    Logging logger;

    public RegionId(int value) {
        if(validateValue(value)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        this.value = value;
        }

    public int getValue() {
        return value;
    }

    public boolean validateValue(int value) {
        logger.info("validation check");
        return value < MIN_VALUE || value > MAX_VALUE;
    }

}
