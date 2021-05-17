package com.example.demo.application.wish_date;


import java.io.IOException;
import java.util.List;

public interface WishDateApplicationService {

    public void register(String owner, String date) throws IOException;

    public List<WishDateModel> get();

}
