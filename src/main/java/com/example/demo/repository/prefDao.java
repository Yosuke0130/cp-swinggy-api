package com.example.demo.repository;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface prefDao {

//    県名取得
    public List<Map<String, Object>> getPref() throws DataAccessException;

}
