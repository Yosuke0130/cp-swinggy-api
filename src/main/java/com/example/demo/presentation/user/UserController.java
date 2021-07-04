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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserApplicationService userApplicationService;

    @Autowired
    Logging logger;


    @PostMapping("")
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<Object> getUsers(@RequestParam("page") int page,
                                 @RequestParam("per") int per) {
        try {
            List<UserModel> userModels = userApplicationService.getUsers(page, per);

            List<Object> userResources = userModels.stream()
                    .map(userResource -> new UserResource(userResource))
                    .collect(Collectors.toList());

            int ttlCount = userApplicationService.getCount();
            userResources.add("ttl: " + ttlCount);

            return userResources;
        } catch (UserCreateException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/_count")
    public int getScreenNameCount(@RequestParam("screen_name")String screenName) {

        //TODO:例外処理実装dataaccessexeption?
        int count = userApplicationService.getScreenNameCount(screenName);

        return count;
    }

}
