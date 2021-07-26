package com.example.demo.presentation.user;

import javax.validation.constraints.NotNull;

public class UserRequestBody {

    @NotNull
    private String id;
    @NotNull
    private String first_name;
    @NotNull
    private String last_name;
    @NotNull
    private String screen_name;
    @NotNull
    private String email;
    private String tel;

    public String getId() {return this.id;}
    public String getFirst_name() {return this.first_name;}
    public String getLast_name() {return this.last_name;}
    public String getScreen_name() {return this.screen_name;}
    public String getEmail() {return this.email;}
    public String getTel() {return this.tel;}

    public void setId(String id) {this.id = id;}
    public void setFirst_name(String first_name) {this.first_name = first_name;}
    public void setLast_name(String last_name) {this.last_name = last_name;}
    public void setScreen_name(String screen_name) {this.screen_name = screen_name;}
    public void setEmail(String email) {this.email = email;}
    public void setTel(String tel) {this.tel = tel;}

}
