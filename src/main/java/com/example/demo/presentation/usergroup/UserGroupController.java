package com.example.demo.presentation.usergroup;

import com.example.demo.Logging;
import com.example.demo.application.usergroup.UserGroupApplicationService;
import com.example.demo.application.usergroup.GroupException;
import com.example.demo.application.usergroup.UserGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-groups")
public class UserGroupController {

    @Autowired
    UserGroupApplicationService userGroupApplicationService;

    @Autowired
    Logging logger;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("")
    public void createUserGroup(@RequestBody UserGroupRequestBody userGroupRequestBody) {
        try {
            userGroupApplicationService.createUserGroup(userGroupRequestBody.getName(), userGroupRequestBody.getCreatedBy());

        } catch (IllegalStateException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (GroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public UserGroupListResource getUserGroups(@RequestParam("user-id")String userId,
                             @RequestParam("page")Optional<Integer> page,
                             @RequestParam("per") Optional<Integer> per) {
        try {
        List<UserGroupDTO> userGroups = userGroupApplicationService.getUserGroups(userId, page, per);

        List<UserGroupResource> userGroupResouces = userGroups.stream()
                .map(userGroup -> new UserGroupResource(userGroup))
                .collect(Collectors.toList());;

        int total = userGroupApplicationService.getUserGroupCount(userId);

        UserGroupListResource userGroupListResource = new UserGroupListResource(userGroupResouces, total);

        return userGroupListResource;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
