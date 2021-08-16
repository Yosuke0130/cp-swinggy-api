package com.example.demo.domain.wish_date;

import com.example.demo.application.wish_date.ParticipateWishDateException;
import com.example.demo.application.wish_date.WishDateRegisterException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface WishDateRepository {

    public void insert(WishDate wishDate) throws WishDateRegisterException;

    public List<WishDate> selectWishDateByDate(LocalDate date) throws WishDateRegisterException, IOException;

    public List<WishDate> selectAll();

    public void insertParticipation(Participation participation) throws ParticipateWishDateException;

    public WishDate selectById(String wishDateId) throws DataAccessException;

    public void deleteWishDate(String wishDateid) throws ParticipateWishDateException, IllegalArgumentException;

    public List<Participation> selectParticipation(String wishDateId, String participant) throws DataAccessException;

    public List<Participation> selectParticipationsByPage(String wishDateId, int page, int per) throws IllegalArgumentException;

    public int countParticipations(String wishDateId);

    public void deleteParticipation(String wishDateId, String participationId) throws IllegalArgumentException;

    public boolean userIdExists(String owner);

}
