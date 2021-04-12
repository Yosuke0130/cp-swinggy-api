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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public PrefectureResource getPrefectures(@RequestParam(name = "region-id", required = false) Optional<Integer> regionId) {
        try{

            List<PrefectureModel> prefectureModelList = prefectureService.getPrefectureList(regionId);

            //PrefectureResource型に変換
            PrefectureResource prefectureResource = new PrefectureResource(prefectureModelList);

            return prefectureResource;

        } catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
