package com.example.demo.application.prefecture;

import com.example.demo.domain.prefecture.Prefecture;
import com.example.demo.domain.prefecture.PrefectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
//import java.util.stream.Stream;

@Service
public class PrefectureServiceImpl implements PrefectureService {

    @Autowired
    PrefectureRepository prefectureRepository;

    //全件取得
    @Override
    public List<PrefectureModel> getFullPrefectureList() {
        System.out.println("input Zero");
        List<Prefecture> allPrefectureList = prefectureRepository.findAll();
        List<PrefectureModel> allPrefectureModelList = allPrefectureList.stream()
                .map(prefecture -> convertToPrefectureModel(prefecture))
                .collect(Collectors.toList());
        return allPrefectureModelList;
    }

    //1-8各地域データ取得
    @Override
    public List<PrefectureModel> getPrefectureList(int regionId) {
        List<Prefecture> prefectureList = prefectureRepository.find(regionId);
        List<PrefectureModel> prefectureModelList = prefectureList.stream()
                .map(prefecture -> convertToPrefectureModel(prefecture))
                .collect(Collectors.toList());
        return prefectureModelList;
    }

        //DTOに入れるメソッド
    private PrefectureModel convertToPrefectureModel(Prefecture prefecture) {
        return new PrefectureModel(prefecture.getId(),
                    prefecture.getName(),
                    prefecture.getRegion_Id());
    }

}

