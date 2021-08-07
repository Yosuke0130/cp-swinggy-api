package com.example.demo.presentation.user;

import java.util.List;

public class UserListResource {

    private List<UserResource> users;
    private int total;

    public UserListResource(List<UserResource> users, int total) {
        this.users = users;
        this.total = total;
    }

    public List<UserResource> getUsers() {return this.users;}
    public int getTotal() {return this.total;}
}
