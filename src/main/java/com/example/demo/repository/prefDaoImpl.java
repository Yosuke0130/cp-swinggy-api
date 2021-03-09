package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class prefDaoImpl implements prefDao {

    @Autowired
    JdbcTemplate jdbc;

//    県名取得
    @Override
    public List<Map<String, Object>> getPref() throws DataAccessException {
        List<Map<String, Object>> list = jdbc.queryForList("select * from prefecture");
        return list;
    }

}
