package com.example.demo.presentation.usergroupthread;

import com.example.demo.Logging;
import com.example.demo.application.usergroupthread.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-groups/{user_group_id}/threads")
public class UserGroupThreadController {

    @Autowired
    private UserGroupThreadApplicationService userGroupThreadApplicationService;

    @Autowired
    private Logging logger;

    @GetMapping("")
    public UserGroupThreadListResource getUserGroupThreads(
            @PathVariable("user_group_id") String userGroupId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("per") Optional<Integer> per
    ) {
        try {
            UserGroupThreadListQueryModel userGroupThreadList = userGroupThreadApplicationService.getUserGroupThreads(userGroupId, page, per);

            int count = userGroupThreadList.getCount();

            List<UserGroupThreadResource> userGroupThreads = userGroupThreadList.getUserGroupThreadQueryModels().stream()
                    .map(thread -> new UserGroupThreadResource(thread.getUserGroupThreadId(), thread.getName(), thread.getUserGroupId()))
                    .collect(Collectors.toList());

            UserGroupThreadListResource userGroupThreadListResource = new UserGroupThreadListResource(userGroupThreads, count);

            return userGroupThreadListResource;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{thread_id}")
    public UserGroupThreadResource getUserGroupThread(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId
    ) {
        try {
            UserGroupThreadQueryModel thread = userGroupThreadApplicationService.getUserGroupThreadById(userGroupId, threadId);

            return new UserGroupThreadResource(thread.getUserGroupThreadId(), thread.getName(), thread.getUserGroupId());
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (UserGroupThreadException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUserGroupThread(
            @PathVariable("user_group_id") String userGroupId,
            @RequestBody UserGroupThreadCreationRequestBody requestBody
    ) {
        try {
            userGroupThreadApplicationService.createThread(userGroupId, requestBody.getName());

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (UserGroupThreadException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{thread_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserGroupThread(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId,
            @RequestBody UserGroupThreadUpdateRequestBody requestBody
    ) {
        try {
            userGroupThreadApplicationService.updateThread(userGroupId, threadId, requestBody.getName());

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (UserGroupThreadException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{thread_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserGroupThread(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId
    ) {
        try {
            userGroupThreadApplicationService.deleteThread(userGroupId, threadId);

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (UserGroupThreadException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{thread_id}/comments")
    public UserGroupCommentListResource getUserGroupComments(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("per") Optional<Integer> per
    ) {
        try {
            UserGroupCommentListQueryModel commentList = userGroupThreadApplicationService.getUserGroupComments(threadId, userGroupId, page, per);

            int count = commentList.getCount();

            List<UserGroupCommentResource> comments = commentList.getUserGroupCommentQueryModels().stream()
                    .map(comment -> new UserGroupCommentResource(
                            comment.getId(),
                            comment.getUserGroupThreadId(),
                            comment.getMemberId(),
                            comment.getText(),
                            comment.getCreatedAt().toString()))
                    .collect(Collectors.toList());

            return new UserGroupCommentListResource(comments, count);
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{thread_id}/comments")
    public void createUserGroupComment(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId,
            @RequestBody UserGroupCommentCreationRequestBody requestBody
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/{thread_id}/comments/{comment_id}")
    public void updateUserGroupComment(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId,
            @PathVariable("comment_id") String commentId,
            @RequestBody UserGroupCommentUpdateRequestBody requestBody
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{thread_id}/comments/{comment_id}")
    public void deleteUserGroupComment(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId,
            @PathVariable("comment_id") String commentId
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
