package com.example.demo.presentation.group;

import com.example.demo.Logging;
import com.example.demo.application.group.GroupApplicationService;
import com.example.demo.application.group.GroupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    GroupApplicationService groupApplicationService;

    @Autowired
    Logging logger;

    // return 204 for success
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("")
    public void createGroup(@RequestBody GroupRequestBody groupRequestBody) {
        try {
            // application call
            groupApplicationService.createGroup(groupRequestBody.getName(), groupRequestBody.getCreatedBy());

        } catch (IllegalStateException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (GroupException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
