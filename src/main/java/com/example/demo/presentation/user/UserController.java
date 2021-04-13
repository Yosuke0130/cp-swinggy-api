package com.example.demo.presentation.user;

import com.example.demo.Logging;
import com.example.demo.application.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Logging logger;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public void createUser(@RequestParam(name = "id", required = false) int userId,
                           @RequestParam(name = "name", required = false) String name,
                           @RequestParam(name = "screenname", required = false) String screenName,
                           @RequestParam(name = "email", required = false) String email,
                           @RequestParam(name = "tel", required = false) String tel) {

        boolean createResult = userService.createAccount(userId, name, screenName, email, tel);

        if (createResult) {
            logger.info("result : " + createResult);
        } else {

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
    }
}
