package com.example.demo.presentation.usergroup;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupListResource {

    private List<UserGroupResource> userGroups;
    private int total;

    public UserGroupListResource(List<UserGroupResource> userGroups, int total) {
        this.userGroups = userGroups;
        this.total = total;
    }

    public List<UserGroupResource> getUserGroups() {return this.userGroups;}
    public int getTotal() {return this.total;}
}
