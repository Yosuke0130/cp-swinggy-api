package com.example.demo.presentation.prefecture;

import com.example.demo.application.prefecture.PrefectureModel;
import com.example.demo.application.prefecture.PrefectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.lang.Integer;
import java.util.Optional;

@RestController
@RequestMapping("/prefectures")
public class PrefectureController {

    @Autowired
    PrefectureService prefectureService;

    @ResponseBody
    @GetMapping("/")
    public PrefectureResource getPrefectures(@RequestParam(name = "region-id", required = false) Optional<Integer> regionId) {

        //サービス呼び出し
        List<PrefectureModel> prefectureModelList = prefectureService.getPrefectureList(regionId);

        //PrefectureResource型に変換
        PrefectureResource prefectureResource = new PrefectureResource(prefectureModelList);

        return prefectureResource;

    }
}
