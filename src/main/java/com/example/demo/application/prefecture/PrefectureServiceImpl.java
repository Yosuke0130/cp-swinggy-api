package com.example.demo.application.prefecture;

import com.example.demo.domain.prefecture.Prefecture;
import com.example.demo.domain.prefecture.PrefectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PrefectureServiceImpl implements PrefectureService {

    @Autowired
    PrefectureRepository prefectureRepository;

    @Override
    public List<PrefectureModel> getPrefectureList(int regionId) {
        //TODO implement
        //0=全件取得、1-8各地域データ取得
        if (regionId == 0) {
            System.out.println("here!");
            List<Prefecture> allPrefectureData = prefectureRepository.findAll();
            List<PrefectureModel> allPrefectureDataDto = convertToDto(allPrefectureData);
            return allPrefectureDataDto;
        } else if (regionId >= 1 || regionId <= 8) {
            List<Prefecture> prefectureData = prefectureRepository.find(regionId);
            List<PrefectureModel> prefectureDataDto = convertToDto(prefectureData);
            return prefectureDataDto;
        } else {
            System.out.println("input error");
            return null;
        }
    }

        //DTOに入れるメソッド
    public List<PrefectureModel> convertToDto(List<Prefecture> prefData) {
        List<PrefectureModel> prefectureModelList = new ArrayList<>();
        for(int i = 0; i < prefData.size(); i++) {
            PrefectureModel prefectureModel =
                    new PrefectureModel(prefData.get(i).getId(),
                            prefData.get(i).getName(),
                            prefData.get(i).getRegion_Id());
            prefectureModelList.add(prefectureModel);
        }
        return  prefectureModelList;
    }
}
