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

        //地域ごとデータ取得
        List<Map<String, Object>> prefectureData = jdbc.queryForList("select * from prefecture where region_id = ?", regionId);
        //Prefecture型に変換
        List<Prefecture> prefectureList = convertToPrefecture(prefectureData);
        return prefectureList;
    }

    //全件取得
    @Override
    public List<Prefecture> findAll() {
        //全件取得
        System.out.println("will take all data");
        List<Map<String, Object>> prefectureData = jdbc.queryForList("select * from prefecture");
        //Prefecture型に変換
        List<Prefecture> allPrefectureList = convertToPrefecture(prefectureData);
        return allPrefectureList;
    }

    //Prefecture型に変換メソッド どこに書くべき？
    private List<Prefecture> convertToPrefecture(List<Map<String, Object>> value) {
        List<Prefecture> prefList = new ArrayList<>();
        for(Map<String, Object> map: value) {
            Prefecture pref = new Prefecture((int)map.get("id"),
                    (String)map.get("name"),
                    (int) map.get("region_id"));
            prefList.add(pref);
        }
        return prefList;
    }
}