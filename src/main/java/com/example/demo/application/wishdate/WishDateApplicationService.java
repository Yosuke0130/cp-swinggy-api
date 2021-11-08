package com.example.demo.application.wishdate;


import com.example.demo.application.usergroup.UserGroupException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface WishDateApplicationService {

    public void register(String owner, String date, String userGroupId) throws IllegalArgumentException ,IllegalStateException, WishDateException, UserGroupException, IOException;

    public List<WishDateModel> getWishDates(Optional<String> from, Optional<String> to, int page, int per, Optional<String> userGroupId, String userId) throws IllegalArgumentException;

    public int getWishDateCount(Optional<String> from, Optional<String> to, Optional<String> userGroupId, String userId) throws IllegalArgumentException;

    public void deleteWishDate(String wishDateId) throws IllegalArgumentException, WishDateException;

    public void participate(String wishDateId, String participant) throws IllegalStateException, WishDateException;

    public List<ParticipationModel> getParticipations(String wishDateId, int page, int per);

    public int getParticipationCount(String wishDateId);

    public void deleteParticipation(String wishDateId, String participationId) throws IllegalStateException, WishDateException;

    public void postWishDateComment(String wishDateId, String author, String text) throws IllegalStateException, IllegalArgumentException, WishDateException;

    public List<WishDateCommentModel> getWishDateComments(String wishDateId, Optional<Integer> page, Optional<Integer> per) throws WishDateException;

    public int getWishDateCommentCount(String wishDateId);

    public void deleteWishDateComment(String wishDateId, String wishDateCommentId) throws IllegalArgumentException, WishDateException;
}
