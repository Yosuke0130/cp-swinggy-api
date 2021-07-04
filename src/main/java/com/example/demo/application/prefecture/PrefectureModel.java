package com.example.demo.application.prefecture;

public class PrefectureModel {

    private final int prefectureId;
    private final String name;
    private final int regionId;

    public PrefectureModel(int prefectureId, String name, int regionId) {
        this.prefectureId = prefectureId;
        this.regionId = regionId;
        this.name = name;
    }

    public int getPrefectureId() {return prefectureId;}
    public String getName() {return name;}
    public int getRegionId() {return regionId;}
}
