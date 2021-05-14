package com.example.demo.presentation.wish_date;

import com.example.demo.Logging;
import com.example.demo.application.wish_date.WishDateApplicationService;
import com.example.demo.application.wish_date.WishDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wish-dates")
public class WishDateController {

    @Autowired
    WishDateApplicationService wishDateApplicationService;

    @Autowired
    Logging logger;

    @PostMapping("/")
    public ResponseEntity<String> registerWishDate(@RequestParam("owner") String owner,
                                               @RequestParam("date") String date,
                                               UriComponentsBuilder uriBuilder) {
        try {

            wishDateApplicationService.register(owner, date);

            HttpHeaders header = new HttpHeaders();
            header.setLocation(uriBuilder.path("/").build().toUri());
            HttpStatus status = HttpStatus.CREATED;

            return new ResponseEntity<>(header, status);

        } catch (IllegalArgumentException e) {

            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } catch (IOException e) {

            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/")
    public List<Object> getWishDates() {

        List<WishDateModel> wishDateModelList = wishDateApplicationService.get();

        List<Object> wishDateResourceList = wishDateModelList.stream()
                .map(wishDate -> new WishDateResource(wishDate))
                .collect(Collectors.toList());

        wishDateResourceList.add("total: " + wishDateResourceList.size());

        return wishDateResourceList;

    }

}
