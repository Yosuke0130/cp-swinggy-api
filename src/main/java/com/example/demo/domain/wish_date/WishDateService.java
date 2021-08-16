package com.example.demo.domain.wish_date;

import com.example.demo.application.wish_date.ParticipateWishDateException;
import com.example.demo.application.wish_date.WishDateRegisterException;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WishDateService {

    @Autowired
    WishDateRepository wishDateRepository;

    @Autowired
    UserRepository userRepository;

    //同じ日付の希望日の登録は登録不可
    public boolean wishDateExists(WishDate wishDate) throws WishDateRegisterException {
        List<WishDate> wishDateList = null;
        try {
            wishDateList = wishDateRepository.selectWishDateByDate(wishDate.getDate());

            return wishDateList.size() > 0;
        } catch (IOException e) {
            throw new WishDateRegisterException("Local Date format is wrong", e);
        }
    }

    //意思表示は一人一回
    public boolean participationExists(Participation participation) throws ParticipateWishDateException {

        List<Participation> participations = null;

        try {
            participations = wishDateRepository.selectParticipation(participation.getWishDateId(), participation.getParticipant());
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

        WishDate wishDate = null;
        try {
            wishDate = wishDateRepository.selectById(participation.getWishDateId());

        } catch (DataAccessException e) {

            throw new ParticipateWishDateException("Failed to access the data source.", e);
        }
        if (wishDate == null) {
            throw new ParticipateWishDateException("Unexpected null value was returned from WishDateRepository.");
        }
        return wishDate.getOwner().equals(participation.getParticipant());
    }

    //owner, participantがuser_idに存在するかチェック
    public boolean isOwnerOrParticipantValid(String id) {
        return userRepository.exists(id);
    }

}
