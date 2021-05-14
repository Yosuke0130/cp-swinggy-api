package com.example.demo.domain.wish_date;

import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.joda.time.IllegalInstantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class WishDateService {

    @Autowired
    WishDateRepository wishDateRepository;

    public boolean wishDateExists(WishDate wishDate) throws IOException {

        List<Map<String, Object>> wishDateList = wishDateRepository.selectDuplicateWishDate(wishDate);

        if (wishDateList.size() > 0) {

            return true;
        }

        return false;
    }
}
