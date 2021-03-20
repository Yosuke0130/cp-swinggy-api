package com.example.demo.domain.prefecture;

//値オブジェクト
public class Prefecture {

    private final int id;
    private final String name;
//    private final int regionId;
    private RegionId regionId;

    //Constructor
    public Prefecture(int id, String name, int regionId) {
        this.id = id;
        this.name = name;
//        this.regionId = regionId;
        RegionId rId = new RegionId(regionId);
    }

    //Getter
    public int getId() {return id;}
    public String getName() {return name;}
    //RegionIdの値を取得
    public int getRegion_Id() {
        int regionId = RegionId.getValue();
        return regionId;
    }
}
