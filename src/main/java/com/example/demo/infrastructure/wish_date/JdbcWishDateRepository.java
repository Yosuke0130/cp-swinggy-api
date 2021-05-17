package com.example.demo.infrastructure.wish_date;

import com.example.demo.domain.wish_date.WishDate;
import com.example.demo.domain.wish_date.WishDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class JdbcWishDateRepository implements WishDateRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public void insert(WishDate wishDate) throws IOException {

        try {

            jdbc.update("insert into wish_date(wish_date_id, owner, wish_date) values(?, ?, ?)", wishDate.getWishDateId(), wishDate.getOwner(), wishDate.getDate());

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new IOException("DB access error occurred when inserting wish date.");
        }
    }

    @Override
    public List<Map<String, Object>> select(String owner, String date) throws IOException {
        try {
            List<Map<String, Object>> wishDateList = jdbc.queryForList("select * from wish_date where owner = ? and wish_date = ?", owner, date.toString());

            return wishDateList;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new IOException("DB access error occurred while checking if the same wish date exists.");
        }
    }

    @Override
    public List<WishDate> selectAll() {

        List<Map<String, Object>> wishDateDate = jdbc.queryForList("select * from wish_date");

        List<WishDate> wishDateList = wishDateDate.stream()
                .map(wishDate -> convertToWishDate(wishDate))
                .collect(Collectors.toList());

        return wishDateList;
    }

    private WishDate convertToWishDate(Map<String, Object> wishDate) {

        return new WishDate(
                (String) wishDate.get("wish_date_id"),
                (String) wishDate.get("owner"),
                wishDate.get("wish_date").toString());
    }
}
