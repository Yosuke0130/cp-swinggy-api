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

        //0=全件取得、1-8各地域データ取得 サービス層で分けるのか？
        if (regionId >= 1 && regionId <= 8) {
            //1-8各地域データ取得
            List<Prefecture> prefectureList = prefectureRepository.find(regionId);
            List<PrefectureModel> prefectureModelList = convertToPrefectureModel(prefectureList);
            return prefectureModelList;
        } else if (regionId == 0) {
            //0=全件取得
            System.out.println("input Zero");
            List<Prefecture> allPrefectureList = prefectureRepository.findAll();
            List<PrefectureModel> allPrefectureModelList = convertToPrefectureModel(allPrefectureList);
            return allPrefectureModelList;
        } else {
            System.out.println("input error");
            return null;
        }
    }

        //DTOに入れるメソッド どこに書くべき？
    private List<PrefectureModel> convertToPrefectureModel(List<Prefecture> value) {
        List<PrefectureModel> prefectureModelList = new ArrayList<>();
        for(int i = 0; i < value.size(); i++) {
            PrefectureModel prefectureModel =
                    new PrefectureModel(value.get(i).getId(),
                            value.get(i).getName(),
                            value.get(i).getRegion_Id());
            prefectureModelList.add(prefectureModel);
        }
        return  prefectureModelList;
    }
}
