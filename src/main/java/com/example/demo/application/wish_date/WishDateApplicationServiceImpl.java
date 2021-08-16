package com.example.demo.application.wish_date;

import com.example.demo.Logging;
import com.example.demo.domain.wish_date.Participation;
import com.example.demo.domain.wish_date.WishDate;
import com.example.demo.domain.wish_date.WishDateRepository;
import com.example.demo.domain.wish_date.WishDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishDateApplicationServiceImpl implements WishDateApplicationService {

    @Autowired
    WishDateRepository wishDateRepository;

    @Autowired
    WishDateService wishDateService;

    @Autowired
    Logging logger;

    @Override
    public void register(String owner, String date) throws IllegalArgumentException ,IllegalStateException, WishDateRegisterException, IOException {

        if(!wishDateService.isOwnerOrParticipantValid(owner)) {
            throw new IllegalArgumentException("This owner doesn't exist on user table.");
        }

        WishDate wishDate = new WishDate(owner, date);

        boolean result = wishDateService.wishDateExists(wishDate);

        if (!result) {
            wishDateRepository.insert(wishDate);
            logger.info("Registered wish date:" + wishDate.getDate().toString());
        } else {
            throw new IllegalStateException("wish date has already existed.");
        }
    }

    @Override
    public List<WishDateModel> get() {

        List<WishDate> wishDateList = wishDateRepository.selectAll();

        List<WishDateModel> wishDateModelList = wishDateList.stream()
                .map(wishDate -> convertToWishDateModel(wishDate))
                .collect(Collectors.toList());

        return wishDateModelList;

    }

    private WishDateModel convertToWishDateModel(WishDate wishDate) {
        return new WishDateModel(wishDate.getWishDateId(),
                wishDate.getOwner(),
                wishDate.getDate().toString());
    }

    @Override
    public void deleteWishDate(String wishDateId) throws IllegalStateException, WishDateRegisterException{

            wishDateRepository.deleteWishDate(wishDateId);
            logger.info("wish date has been deleted: " + wishDateId);

    }


    @Override
    public void participate(String wishDateId, String participant) throws IllegalStateException, IllegalArgumentException, ParticipateWishDateException {

        if(!wishDateService.isOwnerOrParticipantValid(participant)) {
            throw new IllegalArgumentException("This participant doesn't exist on user table.");
        }

        Participation participation = new Participation(wishDateId, participant);

        if (wishDateService.isSelfParticipation(participation)) {
            throw new IllegalStateException("This Wish date is the one you registered.");
        }

        if (wishDateService.participationExists(participation)) {
            throw new IllegalStateException("You're already participated in this wish date.");
        }

        wishDateRepository.insertParticipation(participation);
    }

    @Override
    public List<ParticipationModel> getParticipations(String wishDateId, int page, int per) {

        List<Participation> participations = wishDateRepository.selectParticipationsByPage(wishDateId, page, per);

        List<ParticipationModel> participationModels = participations.stream()
                .map(participationModel -> convertToParticipationModel(participationModel))
                .collect(Collectors.toList());

        return participationModels;
    }

    private ParticipationModel convertToParticipationModel(Participation participation) {

        return new ParticipationModel(participation.getParticipationId(),
                participation.getWishDateId(),
                participation.getDate(),
                participation.getParticipant());
    }

    @Override
    public int getCount(String wishDateId) {

        int count = wishDateRepository.countParticipations(wishDateId);

        return count;
    }

    @Override
    public void deleteParticipation(String wishDateId, String participationId) throws IllegalArgumentException {

        wishDateRepository.deleteParticipation(wishDateId, participationId);
        logger.info("participation has been deleted:" + participationId);
    }

}
