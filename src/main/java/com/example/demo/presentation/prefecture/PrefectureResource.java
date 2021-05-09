package com.example.demo.presentation.prefecture;

import com.example.demo.application.prefecture.PrefectureModel;

public class PrefectureResource {

    private int id;
    private String name;
    private int region_Id;

    public PrefectureResource(PrefectureModel prefectureModel) {

        this.id = prefectureModel.getId();
        this.name = prefectureModel.getName();
        this.region_Id = prefectureModel.getRegion_Id();

    }

    public int getId() {return id;}
    public String getName() {return name;}
    public int getRegion_Id() {return region_Id;}
}
