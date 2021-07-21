package com.example.demo.presentation.prefecture;

import com.example.demo.application.prefecture.PrefectureModel;

public class PrefectureResource {

    private int prefectureId;
    private String name;
    private int regionId;

    public PrefectureResource(PrefectureModel prefectureModel) {

        this.prefectureId = prefectureModel.getPrefectureId();
        this.name = prefectureModel.getName();
        this.regionId = prefectureModel.getRegionId();

    }

    public int getPrefectureId() {return prefectureId;}
    public String getName() {return name;}
    public int getRegionId() {return regionId;}
}
