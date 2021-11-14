package com.example.demo.domain.usergroupthread;

public class UserGroupThreadName {

    private String name;
    final private static int MAX_LENGTH = 30;

    public UserGroupThreadName(String name) throws IllegalArgumentException{
        //memo: need symbol regulation?
        if(name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("UserThreadName's number of characters must be　less than or equal to 30.");
        }
        this.name = name;
    }

    public String getValue() {return this.name;}

}
