package com.example.demo.domain.prefecture;

public class Prefecture {

    private final int id;
    private final String name;
    private RegionId region_Id;

    public Prefecture(int id, String name, int regionId) {
        this.id = id;
        this.name = name;
        this.region_Id = new RegionId(regionId);
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public int getRegion_Id() {
        return region_Id.getValue();
    }
}
