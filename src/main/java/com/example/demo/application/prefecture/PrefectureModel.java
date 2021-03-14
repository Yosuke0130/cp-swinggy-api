package com.example.demo.application.prefecture;

import com.example.demo.domain.prefecture.Prefecture;

import java.util.List;

//DTOクラス
public class PrefectureModel {

    private int id;
    private String name;
    private int region_id;

    //Constructor
    public PrefectureModel(int id, String name, int region_id) {
        this.id = id;
        this.region_id = region_id;
        this.name = name;
    }

    //Getter
    public int getId() {return id;}
    public String getName() {return name;}
    public int getRegion_Id() {return region_id;}

}
