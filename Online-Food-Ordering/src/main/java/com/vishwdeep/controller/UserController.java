package com.vishwdeep.controller;

import com.vishwdeep.model.User;
import com.vishwdeep.service.UserService;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")

public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt ) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    System.err.println("-------"+user);
    return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
