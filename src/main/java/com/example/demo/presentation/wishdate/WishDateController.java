package com.example.demo.presentation.wishdate;

import com.example.demo.Logging;
import com.example.demo.application.usergroup.UserGroupException;
import com.example.demo.application.wishdate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void registerWishDate(@RequestBody WishDateRequestBody wishDateRequestBody) {
        try {
            wishDateApplicationService.register(wishDateRequestBody.getOwner(),
                    wishDateRequestBody.getDate(), wishDateRequestBody.getUserGroupId());

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (WishDateException | UserGroupException | IOException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("")
    public WishDateListResource getWishDates(@RequestParam("from") Optional<String> from,
                                             @RequestParam("to") Optional<String> to,
                                             @RequestParam("page") int page,
                                             @RequestParam("per") int per,
                                             @RequestParam("group_id") Optional<String> userGroupId) {

        List<WishDateModel> wishDateModelList = wishDateApplicationService.getWishDates(from, to, page, per, userGroupId);

        List<WishDateResource> wishDateResourceList = wishDateModelList.stream()
                .map(wishDate -> new WishDateResource(wishDate))
                .collect(Collectors.toList());

        int total = wishDateApplicationService.getWishDateCount(from, to, userGroupId);

        WishDateListResource wishDateListResource = new WishDateListResource(wishDateResourceList, total);

        return wishDateListResource;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{wish_date_id}")
    public void deleteWishDate(@PathVariable("wish_date_id") String wishDateId) {
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
    @PostMapping("/{wish_date_id}/participations")
    public ResponseEntity<String> participateInWishDate(@PathVariable("wish_date_id") String wishDateId,
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
    @GetMapping("/{wish_date_id}/participations")
    public ParticipationListResource getParticipations(@PathVariable("wish_date_id") String wishDateId,
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
    @DeleteMapping("/{wish_date_id}/participations/{participation_id}")
    public void deleteParticipation(@PathVariable("wish_date_id") String wishDateId,
                                    @PathVariable("participation_id") String participationId) {
        try {
            wishDateApplicationService.deleteParticipation(wishDateId, participationId);

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (WishDateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{wish_date_id}/comments")
    public void postComment(@PathVariable("wish_date_id") String wishDateId,
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
    @GetMapping("{wish_date_id}/comments")
    public WishDateCommentListResource getWishDateComment(@PathVariable("wish_date_id") String wishDateId,
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
    @DeleteMapping("/{wish_date_id}/comments/{comment_id}")
    public void deleteWishDateComment(@PathVariable("wish_date_id") String wishDateId,
                                      @PathVariable("comment_id") String wishDateCommentId) {
        try {
            wishDateApplicationService.deleteWishDateComment(wishDateId, wishDateCommentId);

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (WishDateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
