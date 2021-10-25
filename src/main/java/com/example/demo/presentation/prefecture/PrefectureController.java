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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prefectures")
public class PrefectureController {

    @Autowired
    PrefectureService prefectureService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public PrefectureListResource getPrefectures(@RequestParam(name = "region_id", required = false) Optional<Integer> regionId) {
        try {

            List<PrefectureModel> prefectureModelList = prefectureService.getPrefectureList(regionId);

            List<PrefectureResource> prefectureResources = prefectureModelList.stream()
                    .map(prefecture -> new PrefectureResource(prefecture))
                    .collect(Collectors.toList());

            PrefectureListResource prefectureListResource = new PrefectureListResource(prefectureResources);

            return prefectureListResource;

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
