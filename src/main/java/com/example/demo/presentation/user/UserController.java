package com.example.demo.presentation.user;

import com.example.demo.Logging;
import com.example.demo.application.user.UserApplicationService;
import com.example.demo.application.user.UserCreateException;
import com.example.demo.application.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserApplicationService userApplicationService;

    @Autowired
    Logging logger;


    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestParam("id") String userId,
                                             @RequestParam("first_name") String firstName,
                                             @RequestParam("last_name") String lastName,
                                             @RequestParam("screen_name") String screenName,
                                             @RequestParam(name = "profile_image", required = false) MultipartFile profileImageData,
                                             @RequestParam("email") String email,
                                             @RequestParam(name = "tel", required = false) String tel,
                                             UriComponentsBuilder uriBuilder) {
        try {
            Optional<MultipartFile> profileImage = Optional.empty();
            if (Objects.nonNull(profileImageData)) {
                profileImage = Optional.of(profileImageData);
            }

            userApplicationService.create(userId, firstName, lastName, screenName, profileImage, email, tel);

            logger.debug("Your account has created!");

            //リダイレクト先設定
            HttpHeaders header = new HttpHeaders();
            header.setLocation(uriBuilder.path("/").build().toUri());
            HttpStatus status = HttpStatus.CREATED;

            return new ResponseEntity<>(header, status);

        } catch (UserCreateException e) {

            //server error
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (IllegalArgumentException e) {

            //client error
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserResource getUsers(@PathVariable("id") String userId) {

        try {
            UserModel userModel = userApplicationService.get(userId);

            UserResource userResource = new UserResource(userModel);

            return userResource;

        } catch (IllegalArgumentException e) {

            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
