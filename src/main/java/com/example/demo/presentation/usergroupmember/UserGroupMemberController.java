package com.example.demo.presentation.usergroupmember;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/user-group-members")
public class UserGroupMemberController {
    @GetMapping("")
    public UserGroupMemberListResource getUserGroupMembers(
            @RequestParam("user_group_id") String userGroupId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("per") Optional<Integer> per) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("")
    public void createUserGroupMember(
            @RequestBody UserGroupMemberCreationRequestBody userGroupMemberCreationRequestBody
    ) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{user_group_member_id}")
    public void deleteUserGroupMember(@PathVariable("user_group_member_id") String userGroupMemberId) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
