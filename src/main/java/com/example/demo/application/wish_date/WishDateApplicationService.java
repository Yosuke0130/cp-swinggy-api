package com.example.demo.application.wish_date;


import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WishDateApplicationService {

    public void register(String owner, String date) throws IllegalArgumentException ,IllegalStateException, WishDateRegisterException, IOException;

    public List<WishDateModel> get();

    public void participate(String wishDateId, String participant) throws IllegalStateException, ParticipateWishDateException;

    public List<ParticipationModel> getParticipations(String wishDateId, int page, int per);

    public int getCount(String wishDateId);
}
