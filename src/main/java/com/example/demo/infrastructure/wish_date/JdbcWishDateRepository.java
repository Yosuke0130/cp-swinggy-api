package com.example.demo.infrastructure.wish_date;

import com.example.demo.Logging;
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
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class JdbcWishDateRepository implements WishDateRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    Logging logger;

    @Override
    @Transactional
    public void insert(WishDate wishDate) throws WishDateRegisterException {
        try {
            jdbc.update("insert into wish_date(wish_date_id, owner, wish_date) values(?, ?, ?)", wishDate.getWishDateId(), wishDate.getOwner(), wishDate.getDate());

        } catch (DataAccessException e) {
            throw new WishDateRegisterException("DB access error occurred when inserting wish date.", e);
        }
    }

    @Override
    @Transactional
    public List<WishDate> selectWishDateByDate(LocalDate date) throws WishDateRegisterException, IOException {
        try {
            List<Map<String, Object>> wishDateListData = jdbc.queryForList("select * from wish_date where wish_date = ?", date.toString());

            List<WishDate> wishDateList = wishDateListData.stream()
                    .map(wishDate -> convertToWishDate(wishDate))
                    .collect(Collectors.toList());

            return wishDateList;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new WishDateRegisterException("DB access error occurred while checking if the same wish date exists.", e);
        }
    }

    @Override
    @Transactional
    public List<WishDate> selectWishDatesByPage(Optional<LocalDate> from, Optional<LocalDate> to, int page, int per) {
        try {
            int offset = 0;
            if (page > 0) {
                offset = page * per;
            }

            List<Map<String, Object>> wishDateData = null;
            if(from.isPresent()) {
                if(to.isPresent()) {
                    //どちらも値あり
                    wishDateData = jdbc.queryForList("select * from wish_date where wish_date between ? and ? order by wish_date desc limit ? offset ?",
                            from.get(), to.get(), per, offset);
                } else {
                    //fromだけ
                    wishDateData = jdbc.queryForList("select * from wish_date where wish_date >= ? order by wish_date desc limit ? offset ?", from.get(), per, offset);
                }
            } else {
                if(to.isPresent()) {
                    //toだけ
                    to = Optional.of(to.get().plusDays(1));
                    wishDateData = jdbc.queryForList("select * from wish_date where wish_date < ? order by wish_date desc limit ? offset ?", to.get(), per, offset);
                } else {
                    //どちらも値なし
                    wishDateData = jdbc.queryForList("select * from wish_date order by wish_date desc limit ? offset ?", per, offset);
                }
            }

            List<WishDate> wishDateList = wishDateData.stream()
                    .map(wishDate -> convertToWishDate(wishDate))
                    .collect(Collectors.toList());

            return wishDateList;
        } catch (DataAccessException e) {
            throw new WishDateRegisterException("DB access error occurred while getting wish-dates.", e);
        }
    }

    @Override
    @Transactional
    public int selectWishDateCount(Optional<LocalDate> from,Optional<LocalDate> to) {
        try {
            Integer count = 0;
            if(from.isPresent()) {
                if(to.isPresent()) {
                    //どちらも値あり
                    count = jdbc.queryForObject("select count(*) from wish_date where wish_date between ? and ?", Integer.class, from.get(), to.get());
                } else {
                    //fromだけ
                    count = jdbc.queryForObject("select count(*) from wish_date where wish_date >= ?", Integer.class, from.get());
                }
            } else {
                if(to.isPresent()) {
                    //toだけ
                    to = Optional.of(to.get().plusDays(1));
                    count = jdbc.queryForObject("select count(*) from wish_date where wish_date < ?", Integer.class, to.get());
                } else {
                    //どちらも値なし
                    count = jdbc.queryForObject("select count(*) from wish_date", Integer.class);
                }
            }
            return count;
        } catch (DataAccessException e) {
            throw new WishDateRegisterException("DB access error occurred while getting wish-date counts.", e);
        }
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
    public void deleteWishDate(String wishDateId) throws WishDateRegisterException, IllegalArgumentException {
        try {

            boolean existWishDate = wishDateExists(wishDateId);
            if(!existWishDate) {
                throw new IllegalArgumentException("This wishDateId doesn't exist.");
            }

            List<Map<String, Object>> participationData = jdbc.queryForList(
                    "select * from participation where wish_date_id = ?",
                    wishDateId);

            List<Participation> participations = participationData.stream()
                    .map(participation -> convertToParticipation(participation))
                    .collect(Collectors.toList());

            for (Participation participation : participations) {
                jdbc.update("delete from participation where participation_id = ?", participation.getParticipationId());
            }
            jdbc.update("delete from wish_date where wish_date_id = ?", wishDateId);

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
    public List<Participation> selectParticipationsByPage(String wishDateId, int page, int per) throws IllegalArgumentException{

        int offset = 0;
        if (page > 0) {offset = page * per;}

        boolean existWishDate = wishDateExists(wishDateId);
        if(!existWishDate) {
            throw new IllegalArgumentException("This wishDateId doesn't exist.");
        }

        List<Map<String, Object>> participationData = jdbc.queryForList(
                "select * from participation where wish_date_id = ? order by created_at desc limit ? offset ?",
                wishDateId, per, offset);

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
    public void deleteParticipation(String wishDateId, String participationId) throws IllegalArgumentException {
        try {
            boolean existWishDate = wishDateExists(wishDateId);
            if(!existWishDate) {
                throw new IllegalArgumentException("The wishDateId doesn't exist.");
            }

            String selectedWishDateId = jdbc.queryForObject("select wish_date_id from participation where participation_id = ?",String.class, participationId);

            if(selectedWishDateId.equals(wishDateId)) {
                jdbc.update("delete from participation where participation_id = ?", participationId);

            } else {
                throw new IllegalArgumentException("The wishDateId is not related to the participationId.");
            }

        } catch (DataAccessException e) {
            throw new IllegalArgumentException("The participationId is not proper value.");
        }
    }


    private boolean wishDateExists(String wishDateId) {
        try {
            Map<String, Object> wishDate = jdbc.queryForMap("select * from wish_date where wish_date_id = ?", wishDateId);
            return !wishDate.isEmpty();

        } catch (DataAccessException e) {
            return false;
        }
    }

}
