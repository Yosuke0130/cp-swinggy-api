package com.example.demo;

import com.example.demo.repository.prefDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//import com.example.demo.repository.Dao;

@RestController
public class Controller {

    @Autowired
    prefDao daoImpl;

//    県名テーブル取得
    @GetMapping("/index")
    public String getData() {
        System.out.println("foo");
        List<Map<String, Object>> result = daoImpl.getPref();
        System.out.println(result);
        return "hoge";
    }

}
