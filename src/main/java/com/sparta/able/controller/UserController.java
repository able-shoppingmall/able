package com.sparta.able.controller;

import com.sparta.able.dto.user.req.UserLoginRequest;
import com.sparta.able.dto.user.req.UserSignupRequest;
import com.sparta.able.dto.user.res.UserResponse;
import com.sparta.able.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> SignupUser(@RequestBody UserSignupRequest userSignupRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.SignupUser(userSignupRequest));
    }

}
