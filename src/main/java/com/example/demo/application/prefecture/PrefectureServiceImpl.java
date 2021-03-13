package com.example.demo.application.prefecture;

import com.example.demo.domain.prefecture.PrefectureRepository;

import java.util.List;
import java.util.Optional;

public class PrefectureServiceImpl implements PrefectureService {

    private final PrefectureRepository prefectureRepository;

    public PrefectureServiceImpl(PrefectureRepository prefectureRepository){
        this.prefectureRepository = prefectureRepository;
    }

    @Override
    public List<PrefectureModel> getPrefectureList(Optional<String> regionId) {
        //TODO implement
        return null;
    }
}
