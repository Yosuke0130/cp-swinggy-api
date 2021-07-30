package com.example.demo.domain.wish_date;

import com.example.demo.application.wish_date.ParticipateWishDateException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WishDateRepository {

    public void insert(WishDate wishDate) throws IOException;

    public List<WishDate> selectWishDate(String owner, LocalDate date) throws IOException;

    public List<WishDate> selectAll();

    public void insertParticipation(Participation participation) throws ParticipateWishDateException;

    public WishDate selectById(String wishDateId) throws DataAccessException;

    public List<Participation> selectParticipation(String wishDateId, String participant) throws DataAccessException;

    public List<Participation> selectParticipationsByPage(String wishDateId, int page, int per);

    public int countParticipations(String wishDateId);

}
