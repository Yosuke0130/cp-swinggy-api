package com.example.demo.domain.wishdate;

import com.example.demo.application.wishdate.WishDateException;
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
    public boolean wishDateExists(WishDate wishDate) throws WishDateException {
        List<WishDate> wishDateList = null;
        try {
            wishDateList = wishDateRepository.selectWishDateByDate(wishDate.getDate());

            return wishDateList.size() > 0;
        } catch (IOException e) {
            throw new WishDateException("Local Date format is wrong", e);
        }
    }

    //意思表示は一人一回
    public boolean participationExists(Participation participation) throws WishDateException {

        List<Participation> participations = null;

        try {
            participations = wishDateRepository.selectParticipation(participation.getWishDateId(), participation.getParticipant());
        } catch (DataAccessException e) {

            throw new WishDateException("Failed to access the data source.", e);
        }
        if (participations == null) {
            throw new WishDateException("Unexpected null value was returned from WishDateRepository.");
        }
        return participations.size() > 0;
    }

    //自分のWishDateじゃないかチェック
    public boolean isSelfParticipation(Participation participation) throws IllegalArgumentException {

        WishDate wishDate = wishDateRepository.selectById(participation.getWishDateId());
        if (wishDate == null) {
            throw new IllegalArgumentException("This wishDateId doesn't exist.");
        }
        return wishDate.getOwner().equals(participation.getParticipant());
    }

}
