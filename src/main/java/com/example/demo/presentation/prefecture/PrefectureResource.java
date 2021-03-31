package com.example.demo.presentation.prefecture;

import com.example.demo.application.prefecture.PrefectureModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PrefectureResource {

    @JsonProperty("prefectures")
    private List<PrefectureModel> prefectureModel;

    public PrefectureResource(List<PrefectureModel> prefectureModel) {
        this.prefectureModel = prefectureModel;
    }

}
