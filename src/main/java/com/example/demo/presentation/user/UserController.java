package com.example.demo.presentation.user;

import com.example.demo.Logging;
import com.example.demo.application.user.UserApplicationService;
import com.example.demo.application.user.UserCreateException;
import com.example.demo.application.user.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserApplicationService userApplicationService;

    @Autowired
    Logging logger;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createUser(@RequestPart @Valid UserRequestBody json,
                                             @RequestPart(required = false) MultipartFile profile_image,
                                             UriComponentsBuilder uriBuilder) {
        try {
            Optional<MultipartFile> profileImage = Optional.empty();
            if (Objects.nonNull(profile_image)) {
                profileImage = Optional.of(profile_image);
            }

            userApplicationService.create(json.getId(),
                    json.getFirst_name(),
                    json.getLast_name(),
                    json.getScreen_name(),
                    profileImage,
                    json.getEmail(),
                    json.getTel() == null ? Optional.empty() : Optional.of(json.getTel()));

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

        } catch (IllegalArgumentException | IllegalStateException e) {
            //client error
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserResource getUser(@PathVariable("id") String userId) {

        try {
            UserModel userModel = userApplicationService.get(userId);

            UserResource userResource = new UserResource(userModel);

            return userResource;

        } catch (UserCreateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public UserListResource getUsers(@RequestParam("page") int page,
                                 @RequestParam("per") int per) {
        try {
            List<UserModel> userModels = userApplicationService.getUsers(page, per);

            List<UserResource> userResources = userModels.stream()
                    .map(userResource -> new UserResource(userResource))
                    .collect(Collectors.toList());

            int ttlCount = userApplicationService.getCount();

            UserListResource userListResource = new UserListResource(userResources, ttlCount);

            return userListResource;
        } catch (UserCreateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/_count")
    public UserCountResource getScreenNameCount(@RequestParam("screen_name") String screenName) {

        try {
            int count = userApplicationService.getCountByScreenName(screenName);
            UserCountResource countResource = new UserCountResource(count);

            return countResource;
        } catch (UserCreateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
