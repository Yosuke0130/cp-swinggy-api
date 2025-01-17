package com.example.demo.presentation.usergroup;

import com.example.demo.Logging;
import com.example.demo.application.usergroup.UserGroupApplicationService;
import com.example.demo.application.usergroup.UserGroupException;
import com.example.demo.application.usergroup.UserGroupQueryModel;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void createUserGroup(@RequestBody UserGroupRequestBody userGroupRequestBody) {
        try {
            userGroupApplicationService.createUserGroup(userGroupRequestBody.getName(), userGroupRequestBody.getOwner());

        } catch (IllegalStateException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (UserGroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public UserGroupListResource getBelongedUserGroups(@RequestParam("user_id")String member,
                             @RequestParam("page")Optional<Integer> page,
                             @RequestParam("per") Optional<Integer> per) {
        try {
        List<UserGroupQueryModel> userGroups = userGroupApplicationService.getBelongedUserGroups(member, page, per);

        List<UserGroupResource> userGroupResources = userGroups.stream()
                .map(userGroup -> new UserGroupResource(userGroup))
                .collect(Collectors.toList());;

        int total = userGroupApplicationService.getBelongedUserGroupCount(member);

        UserGroupListResource userGroupListResource = new UserGroupListResource(userGroupResources, total);

        return userGroupListResource;

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{group_id}")
    public UserGroupResource getUserGroup(@PathVariable("group_id")String userGroupId) {
        try {
            UserGroupQueryModel userGroupQueryModel = userGroupApplicationService.getUserGroup(userGroupId);

            UserGroupResource userGroupResource = new UserGroupResource(userGroupQueryModel);

            return userGroupResource;

        } catch (UserGroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //memo: パラメータが増えてくる可能性。changeUserGroupProfileのように汎用化するかも。
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{group_id}")
    public void changeUserGroupName(@PathVariable("group_id")String userGroupId,
                                    @RequestParam("name")String userGroupName) {
        try {
            userGroupApplicationService.changeUserGroupName(userGroupId, userGroupName);
        } catch (UserGroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{group_id}")
    public void deleteUserGroup(@PathVariable("group_id")String userGroupId) {
        try {
            userGroupApplicationService.deleteUserGroup(userGroupId);
        } catch (UserGroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
