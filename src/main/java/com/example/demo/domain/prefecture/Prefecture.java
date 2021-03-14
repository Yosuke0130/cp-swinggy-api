package com.example.demo.domain.prefecture;

//ValueObject
public class Prefecture {

    private int id;
    private String name;
    private int regionId;

    //Constructor
    public Prefecture(int id, String name, int regionId) {
        this.id = id;
        this.name = name;
        this.regionId = regionId;
    }

    //Getter
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getRegion_Id() { return regionId;}



}
