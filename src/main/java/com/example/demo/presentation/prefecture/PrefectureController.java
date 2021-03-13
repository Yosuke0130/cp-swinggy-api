package com.example.demo.presentation.prefecture;

import com.example.demo.application.prefecture.PrefectureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prefectures")
public class PrefectureController {

    private final PrefectureService prefectureService;
    public PrefectureController(PrefectureService prefectureService) {
        this.prefectureService = prefectureService;
    }

    @GetMapping("/")
    public PrefectureResource getPrefectures(@RequestParam(name = "region-id", required = false) String regionId) {
        //TODO implement the method
        return new PrefectureResource();
    }
}
