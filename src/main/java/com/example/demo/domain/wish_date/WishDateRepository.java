package com.example.demo.domain.wish_date;

import com.example.demo.application.wish_date.ParticipateWishDateException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface WishDateRepository {

    public void insert(WishDate wishDate) throws IOException;

    public List<WishDate> selectWishDateByDate(LocalDate date) throws IOException;

    public List<WishDate> selectAll();

    public void insertParticipation(Participation participation) throws ParticipateWishDateException;

    public WishDate selectById(String wishDateId) throws DataAccessException;

    public void deleteWishDate(WishDate wishDate, List<Participation> participationList) throws ParticipateWishDateException;

    public List<Participation> selectParticipation(String wishDateId, String participant) throws DataAccessException;

    public List<Participation> selectParticipationsByPage(String wishDateId, int page, int per);

    public List<Participation> selectParticipationsByWishDateId(WishDate wishDate);

    public int countParticipations(String wishDateId);

    public Participation selectParticipationById(String participationId) throws IllegalStateException;

    public void deleteParticipation(Participation participation) throws ParticipateWishDateException;

}
