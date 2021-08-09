package com.example.demo.presentation.user;

public class UserCountResource {
    private int count;

    UserCountResource(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }
}
