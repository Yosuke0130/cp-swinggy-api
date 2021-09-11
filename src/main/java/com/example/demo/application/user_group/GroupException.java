package com.example.demo.application.user_group;

public class GroupException extends RuntimeException{
    public GroupException(String msg) {super(msg);}
    public GroupException(String msg, Throwable cause) {super(msg, cause);}
}
