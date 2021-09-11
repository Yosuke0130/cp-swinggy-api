package com.example.demo.presentation.user_group;

import com.example.demo.Logging;
import com.example.demo.application.user_group.UserGroupApplicationService;
import com.example.demo.application.user_group.GroupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user-groups")
public class UserGroupController {

    @Autowired
    UserGroupApplicationService groupApplicationService;

    @Autowired
    Logging logger;

    // return 204 for success
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("")
    public void createUserGroup(@RequestBody UserGroupRequestBody userGroupRequestBody) {
        try {
            // application call
            groupApplicationService.createUserGroup(userGroupRequestBody.getName(), userGroupRequestBody.getCreatedBy());

        } catch (IllegalStateException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (GroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
