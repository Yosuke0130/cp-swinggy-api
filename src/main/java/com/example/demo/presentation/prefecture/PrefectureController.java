package com.example.demo.presentation.prefecture;

import com.example.demo.application.prefecture.PrefectureModel;
import com.example.demo.application.prefecture.PrefectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @ResponseStatus(HttpStatus.CREATED)
    public PrefectureResource getPrefectures(@RequestParam(name = "region-id", required = false) Optional<Integer> regionId) {
        //サービス呼び出し
        if (regionId.isEmpty()) {
            System.out.println("param is null");
            List<PrefectureModel> prefectureModelList = prefectureService.getFullPrefectureList();
            //PrefectureResourceコンストラクタ生成
            PrefectureResource prefectureResource = new PrefectureResource(prefectureModelList);
            return prefectureResource;

        } else if (regionId.get() >= 1 && regionId.get() <= 8) {
            System.out.println("pram is between 1-8");
            List<PrefectureModel> prefectureModelList = prefectureService.getPrefectureList(regionId.get().intValue());
            //PrefectureResourceコンストラクタ生成
            PrefectureResource prefectureResource = new PrefectureResource(prefectureModelList);
            return prefectureResource;

        } else {
            System.out.println("param error");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
