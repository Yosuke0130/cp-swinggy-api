package com.example.demo.presentation.wish_date;

import com.example.demo.Logging;
import com.example.demo.application.wish_date.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
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

    @PostMapping("")
    public ResponseEntity<String> registerWishDate(@RequestBody WishDateRequestBody wishDateRequestBody,
                                                   UriComponentsBuilder uriBuilder) {
        try {
            wishDateApplicationService.register(wishDateRequestBody.getOwner(), wishDateRequestBody.getDate());

            HttpHeaders header = new HttpHeaders();
            header.setLocation(uriBuilder.path("/").build().toUri());
            HttpStatus status = HttpStatus.CREATED;

            return new ResponseEntity<>(header, status);

        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } catch (WishDateRegisterException e) {

            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ResponseBody
    @GetMapping("")
    public WishDateListResource getWishDates() {

        List<WishDateModel> wishDateModelList = wishDateApplicationService.get();

        List<WishDateResource> wishDateResourceList = wishDateModelList.stream()
                .map(wishDate -> new WishDateResource(wishDate))
                .collect(Collectors.toList());

        WishDateListResource wishDateListResource = new WishDateListResource(wishDateResourceList, wishDateResourceList.size());

        return wishDateListResource;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{wish-date-id}")
    public void deleteWishDate(@PathVariable("wish-date-id") String wishDateId) {
        try {

            wishDateApplicationService.deleteWishDate(wishDateId);

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (WishDateRegisterException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{wish-date-id}/participations")
    public ResponseEntity<String> participateInWishDate(@PathVariable("wish-date-id") String wishDateId,
                                                        @RequestBody @Valid ParticipantRequestBody participantRequestBody) {
        try {
            System.out.println(participantRequestBody.getParticipant());
            wishDateApplicationService.participate(wishDateId, participantRequestBody.getParticipant());

            HttpHeaders header = new HttpHeaders();
            HttpStatus status = HttpStatus.CREATED;

            return new ResponseEntity<>(header, status);

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        } catch (ParticipateWishDateException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @ResponseBody
    @GetMapping("/{wish-date-id}/participations")
    public ParticipationListResource getParticipations(@PathVariable("wish-date-id") String wishDateId,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("per") int per) {

        List<ParticipationModel> participations = wishDateApplicationService.getParticipations(wishDateId, page, per);

        List<ParticipationResource> participationResources = participations.stream()
                .map(participationResource -> new ParticipationResource(participationResource))
                .collect(Collectors.toList());

        int ttlCount = wishDateApplicationService.getCount(wishDateId);

        ParticipationListResource participationListResource = new ParticipationListResource(participationResources, ttlCount);

        return participationListResource;
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{wish-date-id}/participations/{participation-id}")
    public void deleteParticipation(@PathVariable("wish-date-id") String wishDateId,
                                    @PathVariable("participation-id") String participationId) {
        try {

            wishDateApplicationService.deleteParticipation(wishDateId, participationId);

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        } catch (ParticipateWishDateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
