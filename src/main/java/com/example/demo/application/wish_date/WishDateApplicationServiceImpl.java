package com.example.demo.application.wish_date;

import com.example.demo.Logging;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.wish_date.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishDateApplicationServiceImpl implements WishDateApplicationService {

    @Autowired
    WishDateRepository wishDateRepository;

    @Autowired
    WishDateService wishDateService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Logging logger;

    @Override
    public void register(String owner, String date) throws IllegalArgumentException ,IllegalStateException, WishDateException, IOException {

        if(!userRepository.exists(owner)) {
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
    public List<WishDateModel> getWishDates(Optional<String> from, Optional<String> to, int page, int per) throws IllegalArgumentException {

        Optional<LocalDate> validatedFrom = Optional.empty();
        if(from.isPresent()) {
            LocalDate parsedFrom = parseLocalDate(from);
            validatedFrom = Optional.of(parsedFrom);
        }
        Optional<LocalDate> validatedTo = Optional.empty();
        if(to.isPresent()) {
            LocalDate parsedTo = parseLocalDate(to);
            validatedTo = Optional.of(parsedTo);
        }

        List<WishDate> wishDateList = wishDateRepository.selectWishDatesByPage(validatedFrom, validatedTo, page, per);

        List<WishDateModel> wishDateModelList = wishDateList.stream()
                .map(wishDate -> convertToWishDateModel(wishDate))
                .collect(Collectors.toList());

        return wishDateModelList;

    }

    public int getWishDateCount(Optional<String> from, Optional<String> to) throws IllegalArgumentException {

        Optional<LocalDate> validatedFrom = Optional.empty();
        if(from.isPresent()) {
            LocalDate parsedFrom = parseLocalDate(from);
            validatedFrom = Optional.of(parsedFrom);
        }
        Optional<LocalDate> validatedTo = Optional.empty();
        if(to.isPresent()) {
            LocalDate parsedTo = parseLocalDate(to);
            validatedTo = Optional.of(parsedTo);
        }

        int count = wishDateRepository.selectWishDateCount(validatedFrom, validatedTo);

        return count;
    }

    private LocalDate parseLocalDate(Optional<String> value) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedValue = LocalDate.parse(value.get(), dtf);
            return parsedValue;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format error.");
        }
    }

    private WishDateModel convertToWishDateModel(WishDate wishDate) {
        return new WishDateModel(wishDate.getWishDateId(),
                wishDate.getOwner(),
                wishDate.getDate().toString());
    }

    @Override
    public void deleteWishDate(String wishDateId) throws IllegalStateException, WishDateException {

        wishDateRepository.deleteWishDate(wishDateId);
        logger.info("wish date has been deleted: " + wishDateId);

    }


    @Override
    public void participate(String wishDateId, String participant) throws IllegalStateException, WishDateException {

        if(!userRepository.exists(participant)) {
            throw new IllegalStateException("This participant doesn't exist on user table.");
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
    public int getParticipationCount(String wishDateId) {
        int count = wishDateRepository.countParticipations(wishDateId);
        return count;
    }

    @Override
    public void deleteParticipation(String wishDateId, String participationId) throws IllegalArgumentException {
        wishDateRepository.deleteParticipation(wishDateId, participationId);
        logger.info("participation has been deleted:" + participationId);
    }

    @Override
    public void postWishDateComment(String wishDateId, String author, String text) throws IllegalStateException, IllegalArgumentException, WishDateException {
        if(!userRepository.exists(author)) {
            throw new IllegalStateException("This author doesn't exist on user table.");
        }

        WishDate wishDate = wishDateRepository.selectById(wishDateId);
        if(wishDate == null) {
            throw new IllegalArgumentException("This wishDatId doesn't exist.");
        }

        WishDateComment wishdateComment = new WishDateComment(wishDate.getWishDateId(), author, text);

        wishDateRepository.insertWishDateComment(wishdateComment);
    }

    private static final int COMMENT_DEFAULT_PAGE = 0;
    private static final int COMMENT_DEFAULT_PER = 100;
    @Override
    public List<WishDateCommentModel> getWishDateComments(String wishDateId, Optional<Integer> page, Optional<Integer> per) throws WishDateException {

        WishDate wishDate = wishDateRepository.selectById(wishDateId);
        if(wishDate == null) {
            throw new IllegalArgumentException("This wishDateId doesn't exist.");
        }

        int pageValue = page.orElse(COMMENT_DEFAULT_PAGE);
        int perValue = per.orElse(COMMENT_DEFAULT_PER);

        List<WishDateComment> wishDateCommentList = wishDateRepository.selectWishDateCommentsByPage(wishDate, pageValue, perValue);

        List<WishDateCommentModel> wishDateCommentModelList = wishDateCommentList.stream()
                .map(wishDateComment -> convertToWishDateCommentModel(wishDateComment))
                .collect(Collectors.toList());

        return wishDateCommentModelList;
    }

    private WishDateCommentModel convertToWishDateCommentModel(WishDateComment wishDateComment) {
        return new WishDateCommentModel(wishDateComment.getWishDateCommentId(),
                wishDateComment.getWishDateId(),
                wishDateComment.getAuthor(),
                wishDateComment.getText(),
                wishDateComment.getCreated_at());
    }

    @Override
    public int getWishDateCommentCount(String wishDateId) {
        int count = wishDateRepository.countWishDateComment(wishDateId);
        return count;
    }

}
