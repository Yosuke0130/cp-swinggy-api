package com.example.demo.infrastructure.prefecture;

import com.example.demo.domain.prefecture.Prefecture;
import com.example.demo.domain.prefecture.PrefectureRepository;

import java.util.List;
import java.util.Optional;

public class JdbcPrefectureRepository implements PrefectureRepository {
    @Override
    public List<Prefecture> find(Optional<String> regionId) {
        //TODO implement
        return null;
    }
}
