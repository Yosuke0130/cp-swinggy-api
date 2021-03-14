package com.example.demo.infrastructure.prefecture;

import com.example.demo.domain.prefecture.Prefecture;
import com.example.demo.domain.prefecture.PrefectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcPrefectureRepository implements PrefectureRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Prefecture> find(int regionId) {
        //TODO implement
        //地域ごとデータ取得
        List<Map<String, Object>> prefectureData = jdbc.queryForList("select * from prefecture where region_id = ?", regionId);
        System.out.println("took data");

        List<Prefecture> prefectureList = new ArrayList<>();

        //Prefecture型に変換
        for(Map<String, Object> map: prefectureData) {
            Prefecture pref = new Prefecture((int)map.get("id"),
                    (String)map.get("name"),
                    (int) map.get("region_id"));
            prefectureList.add(pref);
        }
        return prefectureList;
    }
    //全件取得
    @Override
    public List<Prefecture> findAll() {
        List<Map<String, Object>> prefectureData = jdbc.queryForList("select * from prefecture");
        List<Prefecture> prefectureList = new ArrayList<>();

        //Prefecture型に変換
        for(Map<String, Object> map: prefectureData) {
            Prefecture pref = new Prefecture((int)map.get("id"),
                    (String)map.get("name"),
                    (int) map.get("region_id"));
            prefectureList.add(pref);
        }
        return prefectureList;
    }
}