package com.example.demo.application.prefecture;

import java.util.List;
import java.util.Optional;

public interface PrefectureService {

    List<PrefectureModel> getFullPrefectureList();

    List<PrefectureModel> getPrefectureList(int regionId);

}
