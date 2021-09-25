package com.example.demo.presentation.wishdate;

import com.example.demo.Logging;
import com.example.demo.application.wishdate.*;
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
import java.util.Optional;
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

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (WishDateException | IOException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //todo: Optioanlでgroup_id単位で取得可能に
    @ResponseBody
    @GetMapping("")
    public WishDateListResource getWishDates(@RequestParam("from") Optional<String> from,
                                             @RequestParam("to") Optional<String> to,
                                             @RequestParam("page") int page,
                                             @RequestParam("per") int per) {

        List<WishDateModel> wishDateModelList = wishDateApplicationService.getWishDates(from, to, page, per);

        List<WishDateResource> wishDateResourceList = wishDateModelList.stream()
                .map(wishDate -> new WishDateResource(wishDate))
                .collect(Collectors.toList());

        int total = wishDateApplicationService.getWishDateCount(from, to);

        WishDateListResource wishDateListResource = new WishDateListResource(wishDateResourceList, total);

        return wishDateListResource;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{wish-date-id}")
    public void deleteWishDate(@PathVariable("wish-date-id") String wishDateId) {
        try {

            wishDateApplicationService.deleteWishDate(wishDateId);

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        } catch (WishDateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{wish-date-id}/participations")
    public ResponseEntity<String> participateInWishDate(@PathVariable("wish-date-id") String wishDateId,
                                                        @RequestBody @Valid ParticipantRequestBody participantRequestBody) {
        try {
            wishDateApplicationService.participate(wishDateId, participantRequestBody.getParticipant());

            HttpHeaders header = new HttpHeaders();
            HttpStatus status = HttpStatus.CREATED;

            return new ResponseEntity<>(header, status);

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (WishDateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ResponseBody
    @GetMapping("/{wish-date-id}/participations")
    public ParticipationListResource getParticipations(@PathVariable("wish-date-id") String wishDateId,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("per") int per) {
        try {
            List<ParticipationModel> participations = wishDateApplicationService.getParticipations(wishDateId, page, per);

            List<ParticipationResource> participationResources = participations.stream()
                    .map(participationResource -> new ParticipationResource(participationResource))
                    .collect(Collectors.toList());

            int ttlCount = wishDateApplicationService.getParticipationCount(wishDateId);

            ParticipationListResource participationListResource = new ParticipationListResource(participationResources, ttlCount);

            return participationListResource;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{wish-date-id}/participations/{participation-id}")
    public void deleteParticipation(@PathVariable("wish-date-id") String wishDateId,
                                    @PathVariable("participation-id") String participationId) {
        try {
            wishDateApplicationService.deleteParticipation(wishDateId, participationId);

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{wish-date-id}/comments")
    public void postComment(@PathVariable("wish-date-id") String wishDateId,
                              @RequestBody @Valid WishDateCommentRequestBody wishDateCommentRequestBody) {
        try {
            wishDateApplicationService.postWishDateComment(wishDateId,
                    wishDateCommentRequestBody.getAuthor(),
                    wishDateCommentRequestBody.getText());

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (WishDateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ResponseBody
    @GetMapping("{wish-date-id}/comments")
    public WishDateCommentListResource getWishDateComment(@PathVariable("wish-date-id") String wishDateId,
                           @RequestParam(name = "page") Optional<Integer> page,
                           @RequestParam(name = "per") Optional<Integer> per) {
        try {

            List<WishDateCommentModel> wishDateCommentModelList = wishDateApplicationService.getWishDateComments(wishDateId, page, per);

            List<WishDateCommentResource> wishDateResources = wishDateCommentModelList.stream()
                    .map(wishDateCommentModel -> new WishDateCommentResource(wishDateCommentModel))
                    .collect(Collectors.toList());

            int total = wishDateApplicationService.getWishDateCommentCount(wishDateId);

            WishDateCommentListResource wishDateCommentListResource = new WishDateCommentListResource(wishDateResources, total);

            return wishDateCommentListResource;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (WishDateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{wish-date-id}/comments/{comment-id}")
    public void deleteWishDateComment(@PathVariable("wish-date-id")String wishDateId,
                                      @PathVariable("comment-id")String wishDateCommentId) {
        try {
            wishDateApplicationService.deleteWishDateComment(wishDateId, wishDateCommentId);

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}