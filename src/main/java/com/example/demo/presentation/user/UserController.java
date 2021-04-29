package com.example.demo.presentation.user;

import com.example.demo.Logging;
import com.example.demo.application.user.UserApplicationService;
import com.example.demo.application.user.UserCreateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserApplicationService userApplicationService;

    @Autowired
    Logging logger;

    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestParam("id") int userId,
                                             @RequestParam("first_name") String firstName,
                                             @RequestParam("last_name") String lastName,
                                             @RequestParam("screen_name") String screenName,
                                             @RequestParam(name = "profile_image", required = false) MultipartFile multiPartFile,
                                             @RequestParam("email") String email,
                                             @RequestParam(name = "tel", required = false) String tel,
                                             UriComponentsBuilder uriBuilder) {

        try{

            byte[] profileImage = multiPartFile.getBytes();

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

        } catch (IOException e) {

            //画像データ処理
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
    }

}
