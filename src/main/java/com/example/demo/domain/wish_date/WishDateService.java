package com.example.demo.domain.wish_date;

import com.example.demo.application.wish_date.ParticipateWishDateException;
import com.example.demo.application.wish_date.WishDateRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class WishDateService {

    @Autowired
    WishDateRepository wishDateRepository;

    public boolean wishDateExists(WishDate wishDate) throws WishDateRegisterException {
        List<Map<String, Object>> wishDates = null;
        try {
            wishDates = wishDateRepository.select(wishDate.getOwner(), wishDate.getDate());
        } catch (IOException e) {
            throw new WishDateRegisterException("Local Date format is wrong", e);
        }
        return wishDates.size() > 0;
    }

    //意思表示は一人一回
    public boolean participationExists(Participation participation) throws ParticipateWishDateException {
        List<Map<String, Object>> participations = null;
        try {
            participations = wishDateRepository.participationExists(participation.getWishDateId(), participation.getParticipant());
        } catch (DataAccessException e) {

            throw new ParticipateWishDateException("Failed to access the data source.", e);
        }
        if (participations == null) {
            throw new ParticipateWishDateException("Unexpected null value was returned from WishDateRepository.");
        }
        return participations.size() > 0;
    }

    //自分のWishDateじゃないかチェック
    public boolean isSelfParticipation(Participation participation) throws ParticipateWishDateException {
        Map<String, Object> wishDates = null;
        try {
            wishDates = wishDateRepository.selectWishDateByParticipation(participation.getWishDateId());

        } catch (DataAccessException e) {

            throw new ParticipateWishDateException("Failed to access the data source.", e);
        }
        if (wishDates == null) {
            throw new ParticipateWishDateException("Unexpected null value was returned from WishDateRepository.");
        }
        return wishDates.get("owner").equals(participation.getParticipant());
    }

}
