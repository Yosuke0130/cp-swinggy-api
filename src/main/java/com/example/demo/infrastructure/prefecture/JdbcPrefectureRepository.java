package com.example.demo.infrastructure.prefecture;

import com.example.demo.Logging;
import com.example.demo.domain.prefecture.Prefecture;
import com.example.demo.domain.prefecture.PrefectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcPrefectureRepository implements PrefectureRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    Logging logger;

    //全件取得
    @Override
    public List<Prefecture> findAll() {
        //全件取得
        logger.info("take all data");
        List<Map<String, Object>> prefectureData = jdbc.queryForList("select * from prefecture");
        //Prefecture型に変換
        List<Prefecture> allPrefectureList = prefectureData.stream()
                .map(prefecture -> convertToPrefecture(prefecture))
                .collect(Collectors.toList());
        return allPrefectureList;
    }

    @Override
    public List<Prefecture> find(int regionId) {
        //地域ごとデータ取得
        List<Map<String, Object>> prefectureData = jdbc.queryForList("select * from prefecture where region_id = ?", regionId);
        //Prefecture型に変換
        List<Prefecture> prefectureList = prefectureData.stream()
                .map(prefecture -> convertToPrefecture(prefecture))
                .collect(Collectors.toList());
        return prefectureList;
    }

    //Prefecture型に変換
    private Prefecture convertToPrefecture(Map<String, Object> prefecture) {
        return new Prefecture((int)prefecture.get("id"),
                (String)prefecture.get("name"),
                (int)prefecture.get("region_id"));
    }

}
