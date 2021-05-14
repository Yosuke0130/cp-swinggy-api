package com.example.demo.application.wish_date;

import com.example.demo.Logging;
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
    public void register(String owner, String date) throws IllegalArgumentException, IOException {

        WishDate wishDate = new WishDate(owner, date);
        logger.info(wishDate.getDate());

        boolean result = wishDateService.wishDateExists(wishDate);

        if (!result) {

            wishDateRepository.insert(wishDate);

        } else {
            throw new IllegalArgumentException("wish date already existed.");
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
                wishDate.getDate());
    }
}
