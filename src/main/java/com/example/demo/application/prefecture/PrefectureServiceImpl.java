package com.example.demo.application.prefecture;

import com.example.demo.Logging;
import com.example.demo.domain.prefecture.Prefecture;
import com.example.demo.domain.prefecture.PrefectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@ResponseStatus(HttpStatus.CREATED)
public class PrefectureServiceImpl implements PrefectureService {

    @Autowired
    PrefectureRepository prefectureRepository;

    @Autowired
    Logging logger;

    @Override
    public List<PrefectureModel> getPrefectureList(Optional<Integer> regionId) throws IllegalArgumentException {

        //全件取得
        if (regionId.isEmpty()) {
            logger.info("param is null");
            List<Prefecture> allPrefectureList = prefectureRepository.findAll();
        List<PrefectureModel> allPrefectureModelList = allPrefectureList.stream()
                .map(prefecture -> convertToPrefectureModel(prefecture))
                .collect(Collectors.toList());
        return allPrefectureModelList;

        }

        //1-8各地域データ取得
        if(regionId.get() >= 1 && regionId.get() <= 8) {
            List<Prefecture> prefectureList = prefectureRepository.find(regionId.get().intValue());
            List<PrefectureModel> prefectureModelList = prefectureList.stream()
                    .map(prefecture -> convertToPrefectureModel(prefecture))
                    .collect(Collectors.toList());
            return prefectureModelList;
        }

        logger.info("Input error from PrefectureServiceImpl");
        throw new IllegalArgumentException("Input error from PrefectureServiceImpl");
    }

        //PrefectureModel型に変換
    private PrefectureModel convertToPrefectureModel(Prefecture prefecture) {
        return new PrefectureModel(prefecture.getId(),
                    prefecture.getName(),
                    prefecture.getRegion_Id());
    }

}
