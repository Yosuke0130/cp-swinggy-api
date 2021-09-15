package com.example.demo.infrastructure.wishdate;

import com.example.demo.Logging;
import com.example.demo.application.wishdate.WishDateException;
import com.example.demo.domain.wishdate.Participation;
import com.example.demo.domain.wishdate.WishDate;
import com.example.demo.domain.wishdate.WishDateComment;
import com.example.demo.domain.wishdate.WishDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
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
    public void insert(WishDate wishDate) throws WishDateException {
        try {
            jdbc.update("INSERT INTO wish_date(wish_date_id, owner, wish_date) VALUES(?, ?, ?)", wishDate.getWishDateId(), wishDate.getOwner(), wishDate.getDate());

        } catch (DataAccessException e) {
            throw new WishDateException("DB access error occurred when inserting wish date.", e);
        }
    }

    @Override
    @Transactional
    public List<WishDate> selectWishDateByDate(LocalDate date) throws WishDateException, IOException {
        try {
            List<Map<String, Object>> wishDateListData = jdbc.queryForList("SELECT * FROM wish_date WHERE wish_date = ?", date.toString());

            List<WishDate> wishDateList = wishDateListData.stream()
                    .map(wishDate -> convertToWishDate(wishDate))
                    .collect(Collectors.toList());

            return wishDateList;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new WishDateException("DB access error occurred while checking if the same wish date exists.", e);
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
                    to = Optional.of(to.get().plusDays(1));
                    wishDateData = jdbc.queryForList("SELECT * FROM wish_date WHERE wish_date >= ? AND wish_date < ? ORDER BY wish_date DESC LIMIT ? OFFSET ?",
                            from.get(), to.get(), per, offset);
                } else {
                    //fromだけ
                    wishDateData = jdbc.queryForList("SELECT * FROM wish_date WHERE wish_date >= ? ORDER BY wish_date DESC LIMIT ? OFFSET ?", from.get(), per, offset);
                }
            } else {
                if(to.isPresent()) {
                    //toだけ
                    to = Optional.of(to.get().plusDays(1));
                    wishDateData = jdbc.queryForList("SELECT * FROM wish_date WHERE wish_date < ? ORDER BY wish_date DESC LIMIT ? OFFSET ?", to.get(), per, offset);
                } else {
                    //どちらも値なし
                    wishDateData = jdbc.queryForList("SELECT * FROM wish_date ORDER BY wish_date DESC LIMIT ? OFFSET ?", per, offset);
                }
            }

            List<WishDate> wishDateList = wishDateData.stream()
                    .map(wishDate -> convertToWishDate(wishDate))
                    .collect(Collectors.toList());

            return wishDateList;
        } catch (DataAccessException e) {
            throw new WishDateException("DB access error occurred while getting wish-dates.", e);
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
                    to = Optional.of(to.get().plusDays(1));
                    count = jdbc.queryForObject("SELECT COUNT(*) FROM wish_date WHERE wish_date >= ? AND wish_date < ?", Integer.class, from.get(), to.get());
                } else {
                    //fromだけ
                    count = jdbc.queryForObject("SELECT COUNT(*) FROM wish_date WHERE wish_date >= ?", Integer.class, from.get());
                }
            } else {
                if(to.isPresent()) {
                    //toだけ
                    to = Optional.of(to.get().plusDays(1));
                    count = jdbc.queryForObject("SELECT COUNT(*) FROM wish_date WHERE wish_date < ?", Integer.class, to.get());
                } else {
                    //どちらも値なし
                    count = jdbc.queryForObject("SELECT COUNT(*) FROM wish_date", Integer.class);
                }
            }
            return count;
        } catch (DataAccessException e) {
            throw new WishDateException("DB access error occurred while getting wish-date counts.", e);
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
    public void insertParticipation(Participation participation) throws WishDateException {
        try {
            jdbc.update("INSERT INTO participation(participation_id, wish_date_id, participant) VALUES(?, ?, ?)",
                    participation.getParticipationId(),
                    participation.getWishDateId(),
                    participation.getParticipant());
        } catch (DataAccessException e) {
            throw new WishDateException("DB access error occurred when insert into Participation.", e);
        }
    }

    @Override
    @Transactional
    public WishDate selectById(String wishDateId) throws WishDateException {
        try {
            Map<String, Object> wishDateData = jdbc.queryForMap(
                    "SELECT * FROM wish_date WHERE wish_date_id = ?",
                    wishDateId);

            WishDate wishDate = convertToWishDate(wishDateData);

            return wishDate;

        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new WishDateException("Query failed.", e);
        }
    }

    @Override
    @Transactional
    public void deleteWishDate(String wishDateId) throws WishDateException, IllegalArgumentException {
        try {

            boolean existWishDate = wishDateExists(wishDateId);
            if(!existWishDate) {
                throw new IllegalArgumentException("This wishDateId doesn't exist.");
            }
            //delete Participation
            List<Map<String, Object>> participationData = jdbc.queryForList(
                    "SELECT * FROM participation WHERE wish_date_id = ?",
                    wishDateId);
            List<Participation> participations = participationData.stream()
                    .map(participation -> convertToParticipation(participation))
                    .collect(Collectors.toList());
            for (Participation participation : participations) {
                jdbc.update("DELETE FROM participation WHERE participation_id = ?", participation.getParticipationId());
            }
            //delete WishDateComment
            List<Map<String, Object>> wishDateCommentData = jdbc.queryForList(
                    "SELECT * FROM wish_date_comment WHERE wish_date_id = ?",
                    wishDateId);
            List<WishDateComment> wishDateComments = wishDateCommentData.stream()
                    .map(wishDateComment -> convertToWishDateComment(wishDateComment))
                    .collect(Collectors.toList());
            for(WishDateComment wishDateComment: wishDateComments) {
                jdbc.update("DELETE FROM wish_date_comment WHERE comment_id = ?", wishDateComment.getWishDateCommentId());
            }
            //delete wishDate
            jdbc.update("DELETE FROM wish_date WHERE wish_date_id = ?", wishDateId);

        } catch (DataAccessException e) {
            throw new WishDateException("DB access error occurred when deleting wishDate.", e);
        }
    }

    @Override
    @Transactional
    public List<Participation> selectParticipation(String wishDateId, String participant) throws DataAccessException {

        List<Map<String, Object>> participationData = jdbc.queryForList(
                "SELECT * FROM participation WHERE wish_date_id = ? AND participant = ?",
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
                "SELECT * FROM participation WHERE wish_date_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?",
                wishDateId, per, offset);

        List<Participation> participations = participationData.stream()
                .map(participation -> convertToParticipation(participation))
                .collect(Collectors.toList());

        return participations;
    }


    private Participation convertToParticipation(Map<String, Object> participation) {

        return new Participation((String) participation.get("participation_id"),
                (String) participation.get("wish_date_id"),
                (Timestamp) participation.get("created_at"),
                (String) participation.get("participant"));
    }

    @Override
    @Transactional
    public int countParticipations(String wishDateId) {

        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM participation WHERE wish_date_id = ?", Integer.class, wishDateId);

        return count;
    }


    @Override
    @Transactional
    public void deleteParticipation(String wishDateId, String participationId) throws IllegalArgumentException {
        try {
            String selectedWishDateId = jdbc.queryForObject("SELECT wish_date_id FROM participation WHERE participation_id = ?",String.class, participationId);

            if(selectedWishDateId.equals(wishDateId)) {
                jdbc.update("DELETE FROM participation WHERE participation_id = ?", participationId);

            } else {
                throw new IllegalArgumentException("The wishDateId is not related to the participationId.");
            }

        } catch (DataAccessException e) {
            throw new IllegalArgumentException("The participationId is not proper value.");
        }
    }


    private boolean wishDateExists(String wishDateId) {
        try {
            Map<String, Object> wishDate = jdbc.queryForMap("SELECT * FROM wish_date WHERE wish_date_id = ?", wishDateId);
            return !wishDate.isEmpty();

        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public void insertWishDateComment(WishDateComment wishDateComment) throws WishDateException{
        try {
            jdbc.update("INSERT INTO wish_date_comment(comment_id, wish_date_id, author, text) VALUES(?, ?, ?, ?)",
                    wishDateComment.getWishDateCommentId(),
                    wishDateComment.getWishDateId(),
                    wishDateComment.getAuthor(),
                    wishDateComment.getText());
        } catch (DataAccessException e) {
            throw new WishDateException("DB access error occurred when inserting wishDateComment.", e);
        }
    }

    @Override
    @Transactional
    public List<WishDateComment> selectWishDateCommentsByPage(WishDate wishDate, int page, int per) {

        int offset = 0;
        if (page > 0) {offset = page * per;}

        List<Map<String, Object>> wishDateCommentsData = null;
        wishDateCommentsData = jdbc.queryForList("SELECT * FROM wish_date_comment WHERE wish_date_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?",
                wishDate.getWishDateId(), per, offset);

        List<WishDateComment> wishDateCommentList = wishDateCommentsData.stream()
                .map(wishDateComment -> convertToWishDateComment(wishDateComment))
                .collect(Collectors.toList());

        return wishDateCommentList;
    }

    private WishDateComment convertToWishDateComment(Map<String, Object> wishDateComment) {
        return new WishDateComment((String)wishDateComment.get("comment_id"),
                (String)wishDateComment.get("wish_date_id"),
                (String)wishDateComment.get("author"),
                (String)wishDateComment.get("text"),
                (Timestamp)wishDateComment.get("created_at"));
    }

    @Override
    public int countWishDateComment(String wishDateId) {

        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM wish_date_comment WHERE wish_date_id = ?", Integer.class, wishDateId);

        return count;
    }

    @Override
    @Transactional
    public void deleteWishDateComment(String wishDateId, String wishDateCommentId) throws IllegalArgumentException {
        try {
            String selectedWishDateId = jdbc.queryForObject("SELECT wish_date_id FROM wish_date_comment WHERE comment_id = ?",String.class, wishDateCommentId);

            if(selectedWishDateId.equals(wishDateId)) {
                jdbc.update("DELETE FROM wish_date_comment WHERE comment_id = ?", wishDateCommentId);

            } else {
                throw new IllegalArgumentException("The wishDateId is not related to the wishDateCommentId.");
            }

        } catch (DataAccessException e) {
            throw new IllegalArgumentException("The wishDateCommentId is not proper value.");
        }
    }

}
