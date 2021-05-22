package com.example.demo.domain.wish_date;

import com.example.demo.application.wish_date.ParticipateWishDateException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WishDateRepository {

    public void insert(WishDate wishDate) throws IOException;

    public List<Map<String, Object>> select(String owner, LocalDate date) throws IOException;

    public List<WishDate> selectAll();

    public void insertIntoParticipation(Participation participation) throws ParticipateWishDateException;

    public List<Map<String, Object>> selectWishDateByParticipation(String wishDateId, String participant) throws DataAccessException;

    public List<Map<String , Object>> participationExists(String wishDateId, String participant) throws DataAccessException;

    public List<Participation> selectParticipation(String wishDateId, int page, int per);

    public int countParticipations(String wishDateId);

}
