package com.example.demo.presentation.prefecture;

import com.example.demo.application.prefecture.PrefectureModel;
import com.example.demo.application.prefecture.PrefectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/prefectures")
public class PrefectureController {

        @Autowired
        PrefectureService prefectureService;

    @GetMapping("/")
    public PrefectureResource getPrefectures(@RequestParam(name = "region-id", required = false) int regionId) {
        //サービス呼び出し
        List<PrefectureModel> prefectureModelList = prefectureService.getPrefectureList(regionId);

        //PrefectureResourceコンストラクタ生成
        PrefectureResource prefectureResource = new PrefectureResource(prefectureModelList);

        return prefectureResource;
    }
}
