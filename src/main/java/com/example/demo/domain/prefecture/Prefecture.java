package com.example.demo.domain.prefecture;

public class Prefecture {

    private final int prefectureId;
    private final String name;
    private RegionId regionId;

    public Prefecture(int prefectureId, String name, int regionId) throws IllegalArgumentException {
        this.prefectureId = prefectureId;
        this.name = name;
        this.regionId = new RegionId(regionId);
    }

    public int getPrefectureId() {return prefectureId;}
    public String getName() {return name;}
    public int getRegionId() {
        return regionId.getValue();
    }
}
