package com.example.demo.presentation.prefecture;

import com.example.demo.application.prefecture.PrefectureModel;

public class PrefectureResource {

    private int id;
    private String name;
    private int regionId;

    public PrefectureResource(PrefectureModel prefectureModel) {

        this.id = prefectureModel.getPrefectureId();
        this.name = prefectureModel.getName();
        this.regionId = prefectureModel.getRegionId();

    }

    public int getId() {return id;}
    public String getName() {return name;}
    public int getRegionId() {return regionId;}
}
