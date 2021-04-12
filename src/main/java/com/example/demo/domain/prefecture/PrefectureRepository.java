package com.example.demo.domain.prefecture;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PrefectureRepository {

    //全県名データ取得
    List<Prefecture> findAll();

    //地域ごとの県名データ取得
    List<Prefecture> find(int regionId);

}
