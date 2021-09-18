package com.example.demo.presentation.usergroup;

import com.example.demo.Logging;
import com.example.demo.application.usergroup.UserGroupApplicationService;
import com.example.demo.application.usergroup.UserGroupException;
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
        } catch (UserGroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //memo: user-idのパラメータ、参加してるグループを取得するのか、自分が作成したグループを取得するのか紛らわしい？
    @GetMapping("")
    public UserGroupListResource getOwnedUserGroups(@RequestParam("user-id")String createdBy,
                             @RequestParam("page")Optional<Integer> page,
                             @RequestParam("per") Optional<Integer> per) {
        try {
        List<UserGroupDTO> userGroups = userGroupApplicationService.getOwnedUserGroups(createdBy, page, per);

        List<UserGroupResource> userGroupResouces = userGroups.stream()
                .map(userGroup -> new UserGroupResource(userGroup))
                .collect(Collectors.toList());;

        int total = userGroupApplicationService.getOwnedUserGroupCount(createdBy);

        UserGroupListResource userGroupListResource = new UserGroupListResource(userGroupResouces, total);

        return userGroupListResource;

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{group-id}")
    public UserGroupResource getUserGroup(@PathVariable("group-id")String userGroupId) {
        try {
            UserGroupDTO userGroupDTO = userGroupApplicationService.getUserGroup(userGroupId);

            UserGroupResource userGroupResource = new UserGroupResource(userGroupDTO);

            return userGroupResource;

        } catch (UserGroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{group-id}")
    public void changeUserGroupName(@PathVariable("group-id")String userGroupId,
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

    //todo: delete group

}
