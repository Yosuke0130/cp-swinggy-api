package com.example.demo.presentation.prefecture;

import java.util.List;

public class PrefectureListResource {

    private List<PrefectureResource> prefectures;

    public PrefectureListResource(List<PrefectureResource> prefectures) {
        this.prefectures = prefectures;
    }

    public List<PrefectureResource> getPrefectures() {return  prefectures;}
}
