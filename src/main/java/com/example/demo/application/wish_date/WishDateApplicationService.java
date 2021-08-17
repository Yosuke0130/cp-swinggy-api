package com.example.demo.application.wish_date;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface WishDateApplicationService {

    public void register(String owner, String date) throws IllegalArgumentException ,IllegalStateException, WishDateRegisterException, IOException;

    public List<WishDateModel> getWishDates(Optional<String> from, Optional<String> to, int page, int per) throws IllegalArgumentException;

    public int getWishDateCount(Optional<String> from, Optional<String> to) throws IllegalArgumentException;

    public void deleteWishDate(String wishDateId) throws IllegalArgumentException, WishDateRegisterException;

    public void participate(String wishDateId, String participant) throws IllegalStateException, ParticipateWishDateException;

    public List<ParticipationModel> getParticipations(String wishDateId, int page, int per);

    public int getParticipationCount(String wishDateId);

    public void deleteParticipation(String wishDateId, String participationId) throws IllegalStateException;

}
