package com.example.demo.domain.wish_date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WishDateRepository {

    public void insert(WishDate wishDate) throws IOException;

    public List<Map<String, Object>> select(String owner, String date) throws IOException;

    public List<WishDate> selectAll();

}
