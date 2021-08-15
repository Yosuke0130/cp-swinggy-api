package com.example.demo.infrastructure.wish_date;

import com.example.demo.application.wish_date.ParticipateWishDateException;
import com.example.demo.application.wish_date.WishDateRegisterException;
import com.example.demo.domain.wish_date.Participation;
import com.example.demo.domain.wish_date.WishDate;
import com.example.demo.domain.wish_date.WishDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class JdbcWishDateRepository implements WishDateRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public void insert(WishDate wishDate) throws IOException, WishDateRegisterException {
        try {
            jdbc.update("insert into wish_date(wish_date_id, owner, wish_date) values(?, ?, ?)", wishDate.getWishDateId(), wishDate.getOwner(), wishDate.getDate());

        } catch (DataAccessException e) {
            throw new WishDateRegisterException("DB access error occurred when inserting wish date.", e);
        }
    }

    @Override
    @Transactional
    public List<WishDate> selectWishDateByDate(LocalDate date) throws WishDateRegisterException {
        try {
            List<Map<String, Object>> wishDateListData = jdbc.queryForList("select * from wish_date where wish_date = ?", date.toString());

            List<WishDate> wishDateList = wishDateListData.stream()
                    .map(wishDate -> convertToWishDate(wishDate))
                    .collect(Collectors.toList());

            return wishDateList;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new WishDateRegisterException("DB access error occurred while checking if the same wish date exists.", e);
        }
    }

    @Override
    @Transactional
    public List<WishDate> selectAll() {

        List<Map<String, Object>> wishDateData = jdbc.queryForList("select * from wish_date");

        List<WishDate> wishDateList = wishDateData.stream()
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

    @Override
    @Transactional
    public void insertParticipation(Participation participation) throws ParticipateWishDateException {
        try {
            System.out.println(participation.getParticipant());
            jdbc.update("insert into participation(participation_id, wish_date_id, created_at, participant) values(?, ?, ?, ?)",
                    participation.getParticipationId(),
                    participation.getWishDateId(),
                    participation.getDate(),
                    participation.getParticipant());
        } catch (DataAccessException e) {
            throw new ParticipateWishDateException("DB access error occurred when insert into Participation.", e);
        }
    }

    @Override
    @Transactional
    public WishDate selectById(String wishDateId) throws DataAccessException {

        Map<String, Object> wishDateData = jdbc.queryForMap(
                "select * from wish_date where wish_date_id = ?",
                wishDateId);

        WishDate wishDate = convertToWishDate(wishDateData);

        return wishDate;
    }

    @Override
    @Transactional
    public void deleteWishDate(WishDate wishDate, List<Participation> participationList) throws WishDateRegisterException {

        try {
            for (Participation participation : participationList) {
                jdbc.update("delete from participation where participation_id = ?", participation.getParticipationId());
            }

            jdbc.update("delete from wish_date where wish_date_id = ?", wishDate.getWishDateId());

        } catch (DataAccessException e) {
            throw new WishDateRegisterException("DB access error occurred when deleting wishDate.", e);
        }
    }

    @Override
    @Transactional
    public List<Participation> selectParticipation(String wishDateId, String participant) throws DataAccessException {

        List<Map<String, Object>> participationData = jdbc.queryForList(
                "select * from participation where wish_date_id = ? and participant = ?",
                wishDateId, participant);

        List<Participation> participationList = new ArrayList();
        for (Map<String, Object> value : participationData) {
            Participation participation = convertToParticipation(value);
            participationList.add(participation);
        }

        return participationList;
    }

    @Override
    @Transactional
    public List<Participation> selectParticipationsByPage(String wishDateId, int page, int per) {

        int offset = 0;
        if (page > 0) {
            offset = page * per;
        }

        List<Map<String, Object>> participationData = jdbc.queryForList(
                "select * from participation where wish_date_id = ? order by created_at desc limit ? offset ?",
                wishDateId, per, offset);

        List<Participation> participations = participationData.stream()
                .map(participation -> convertToParticipation(participation))
                .collect(Collectors.toList());

        return participations;
    }

    @Override
    @Transactional
    public List<Participation> selectParticipationsByWishDateId(WishDate wishDate) {

        List<Map<String, Object>> participationData = jdbc.queryForList(
                "select * from participation where wish_date_id = ?",
                wishDate.getWishDateId());

        List<Participation> participations = participationData.stream()
                .map(participation -> convertToParticipation(participation))
                .collect(Collectors.toList());

        return participations;
    }

    private Participation convertToParticipation(Map<String, Object> participation) {

        return new Participation((String) participation.get("participation_id"),
                (String) participation.get("wish_date_id"),
                participation.get("created_at").toString(),
                (String) participation.get("participant"));
    }

    @Override
    @Transactional
    public int countParticipations(String wishDateId) {

        Integer count = jdbc.queryForObject("select count(*) from participation where wish_date_id = ?", Integer.class, wishDateId);

        return count;
    }

    @Override
    @Transactional
    public Participation selectParticipationById(String participationId) throws IllegalStateException {
        try {
            Map<String, Object> participationData = jdbc.queryForMap("select * from participation where participation_id = ?",
                    participationId);

            Participation participation = convertToParticipation(participationData);

            return participation;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException("this participationId doesn't matched with any data.");
        }
    }

    @Override
    @Transactional
    public void deleteParticipation(Participation participation) {

        try {
            jdbc.update("delete from participation where participation_id = ?", participation.getParticipationId());

        } catch (DataAccessException e) {
            throw new ParticipateWishDateException("DB access error occurred when deleting participation.", e);
        }
    }

}
