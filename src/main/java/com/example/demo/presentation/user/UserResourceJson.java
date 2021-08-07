package com.example.demo.presentation.user;

import java.util.List;

public class UserResourceJson {

    private List<UserResource> userResources;
    private String ttlCount;

    public UserResourceJson(List<UserResource> userResources, int count) {
        this.userResources = userResources;
        this.ttlCount = "total: " + count;
    }

    public List<UserResource> getUserResources() {return this.userResources;}
    public String getCount() {return this.ttlCount;}
}
