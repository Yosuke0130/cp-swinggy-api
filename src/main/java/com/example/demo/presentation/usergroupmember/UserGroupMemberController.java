package com.example.demo.presentation.usergroupmember;

import com.example.demo.Logging;
import com.example.demo.application.usergroupmember.UserGroupMemberApplicationService;
import com.example.demo.application.usergroupmember.UserGroupMemberException;
import com.example.demo.application.usergroupmember.UserGroupMemberListQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-group-members")
public class UserGroupMemberController {

    @Autowired
    UserGroupMemberApplicationService userGroupMemberApplicationService;

    @Autowired
    Logging logger;

    @GetMapping("")
    public UserGroupMemberListResource getUserGroupMembers(
            @RequestParam("user_group_id") String userGroupId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("per") Optional<Integer> per) {
        try {
            UserGroupMemberListQueryModel userGroupMembers = userGroupMemberApplicationService.getUserGroupMembers(userGroupId, page, per);

            List<UserGroupMemberResource> userGroupMemberResources = userGroupMembers.getUserGroupMemberListQueryModel().stream()
                    .map(member -> new UserGroupMemberResource(member))
                    .collect(Collectors.toList());

            int total = userGroupMembers.getTotal();

            UserGroupMemberListResource userGroupMemberListResource = new UserGroupMemberListResource(userGroupMemberResources, total);

            return userGroupMemberListResource;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("")
    public void createUserGroupMember(
            @RequestBody UserGroupMemberCreationRequestBody userGroupMemberCreationRequestBody) {
        try {
            userGroupMemberApplicationService.registerUserGroupMember(
                    userGroupMemberCreationRequestBody.getUserGroupId(),
                    userGroupMemberCreationRequestBody.getUserId());

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } catch (UserGroupMemberException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{user_group_member_id}")
    public void deleteUserGroupMember(@PathVariable("user_group_member_id") String userGroupMemberId) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
