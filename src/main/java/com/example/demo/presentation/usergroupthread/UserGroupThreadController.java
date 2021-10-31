package com.example.demo.presentation.usergroupthread;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user-groups/{user_group_id}/threads")
public class UserGroupThreadController {
    @GetMapping("")
    public UserGroupThreadListResource getUserGroupThreads(
            @PathVariable("user_group_id") String userGroupId,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "per", required = false) int per
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/{thread_id}")
    public UserGroupThreadResource getUserGroupThread(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping("")
    public void createUserGroupThread(
            @PathVariable("user_group_id") String userGroupId,
            @RequestBody UserGroupThreadCreationRequestBody requestBody
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/{thread_id}")
    public void updateUserGroupThread(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId,
            @RequestBody UserGroupThreadUpdateRequestBody requestBody
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{thread_id}")
    public void deleteUserGroupThread(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/{thread_id}/comments")
    public UserGroupCommentListResource getUserGroupComments(
            @PathVariable("user_group_id") String userGroupId,
            @PathVariable("thread_id") String threadId,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "per", required = false) int per
    ) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
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
