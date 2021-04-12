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

    @Override
    public List<Prefecture> findAll() throws IllegalArgumentException{
        //全件取得
        logger.debug("take all data");
        List<Map<String, Object>> prefectureData = jdbc.queryForList("select * from prefecture");

        List<Prefecture> allPrefectureList = prefectureData.stream()
                .map(prefecture -> convertToPrefecture(prefecture))
                .collect(Collectors.toList());
        return allPrefectureList;
    }

    @Override
    public List<Prefecture> find(int regionId) throws IllegalArgumentException{
        //地域ごとデータ取得
        logger.debug("take regional data");
        List<Map<String, Object>> prefectureData = jdbc.queryForList("select * from prefecture where region_id = ?", regionId);

        List<Prefecture> prefectureList = prefectureData.stream()
                .map(prefecture -> convertToPrefecture(prefecture))
                .collect(Collectors.toList());
        return prefectureList;
    }

    private Prefecture convertToPrefecture(Map<String, Object> prefecture) throws IllegalArgumentException{
        return new Prefecture((int)prefecture.get("id"),
                (String)prefecture.get("name"),
                (int)prefecture.get("region_id"));
    }

}
