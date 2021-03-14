package com.example.demo.domain.prefecture;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PrefectureRepository {
    List<Prefecture> find(int regionId);

    List<Prefecture> findAll();
}
